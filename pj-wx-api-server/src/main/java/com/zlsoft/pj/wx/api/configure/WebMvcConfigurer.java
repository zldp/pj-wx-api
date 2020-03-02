package com.zlsoft.pj.wx.api.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zlsoft.pj.wx.api.commons.Result;
import com.zlsoft.pj.wx.api.commons.ResultCode;
import com.zlsoft.pj.wx.api.exception.ServiceException;
import com.zlsoft.pj.wx.api.util.Sha1Util;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;
    /**
     * app_secret  平台密钥
     */
    @Value("${pj.app.secret}")
    private String secret;

    /**
     * 使用阿里 FastJson 作为JSON MessageConverter
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //保留空的字段
        config.setSerializerFeatures(
                // 保留 Map 空的字段
                SerializerFeature.WriteMapNullValue,
                // 将 String 类型的 null 转成""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将 Number 类型的 null 转成 0
                SerializerFeature.WriteNullNumberAsZero,
                // 将 List 类型的 null 转成 []
                SerializerFeature.WriteNullListAsEmpty,
                // 将 Boolean 类型的 null 转成 false
                SerializerFeature.WriteNullBooleanAsFalse,
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect
                );

        // 按需配置，更多参考FastJson文档哈
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");

        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);
    }


    /**
     * 统一异常处理
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result();
                //业务失败的异常，如“账号或密码错误”
                if (e instanceof ServiceException) {
                    result.setCode(ResultCode.FAIL).setMsg(e.getMessage());
                    logger.info(e.getMessage());
                } else if (e instanceof NoHandlerFoundException) {
                    result.setCode(ResultCode.NOT_FIND).setMsg("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof ServletException) {
                    result.setCode(ResultCode.FAIL).setMsg(e.getMessage());
                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                    logger.error(message, e);
                }
                responseResult(response, result);
                return new ModelAndView();
            }

        });
    }

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
        //开发环境忽略签名认证
        if ("dev".equals(env) || "prod".equals(env)) {
            registry.addInterceptor(new HandlerInterceptorAdapter() {
                @Override
                public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                    // 前提判断 获取token的值，如果token的值是空的就进行签名验证
                    //验证签名
                    boolean pass = validateSign(request);
                    if (pass) {
                        return true;
                    } else {
                        logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                                request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));

                        Result result = new Result();
                        result.setCode(ResultCode.ERRORSIGN).setMsg("签名认证失败");
                        responseResult(response, result);
                        return false;
                    }
                }
            });
        }
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    private boolean validateSign(HttpServletRequest request) {
        // 获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
        String requestSign = request.getHeader("sign");
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
        // 排除sign参数
        keys.remove("sign");
        // 排序
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            // 拼接字符串
            sb.append(key).append("=").append(request.getParameter(key)).append("&");
        }
        // 最后加上平台密钥
        sb.append(secret);
        String linkString = sb.toString();

        // 进行sha1加密
        String sign = Sha1Util.sha1Encrypt(linkString);
        //比较
        return StringUtils.equals(sign, requestSign);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }
}
