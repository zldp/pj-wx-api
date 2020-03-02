package com.zlsoft.pj.wx.api.service;

import com.zlsoft.pj.wx.api.domain.BRXX;

import java.util.List;

/**
 * 病人信息service
* @author dp
* @Version 1.0.0
* @date 2020-02-28 10:22
*/
public interface BRXXService{

    /**
     * 获取所有病人信息
     * @return {@link BRXX}
     */
    List<BRXX> getAll();
}
