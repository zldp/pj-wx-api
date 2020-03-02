package com.zlsoft.pj.wx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 主启动类
 * @author dp
 * @version 1.0.0
 * @date 2020-02-28 10:21
 */
@SpringBootApplication
@MapperScan("com.zlsoft.pj.wx.api.mapper")
public class PjWxApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PjWxApiApplication.class, args);
    }
}
