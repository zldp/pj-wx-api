package com.zlsoft.pj.wx.api.service.impl;

import com.zlsoft.pj.wx.api.domain.BRXX;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.zlsoft.pj.wx.api.mapper.BRXXMapper;
import com.zlsoft.pj.wx.api.service.BRXXService;

import java.util.List;

/**
 * 病人信息service实现类
* @author dp
* @Version 1.0.0
* @date 2020-02-28 10:22
*/
@Service
public class BRXXServiceImpl implements BRXXService{

    @Resource
    private BRXXMapper bRXXMapper;


    /**
     * 得到所有的病人
     * @return {@link BRXX}
     */
    @Override
    public List<BRXX> getAll() {
        return bRXXMapper.selectAll();
    }
}
