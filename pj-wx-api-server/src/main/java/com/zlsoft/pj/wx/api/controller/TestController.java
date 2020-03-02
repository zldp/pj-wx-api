package com.zlsoft.pj.wx.api.controller;

import com.zlsoft.pj.wx.api.domain.BRXX;
import com.zlsoft.pj.wx.api.service.BRXXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搭建环境时测试的conreoller
 * @author dp
 * @version 1.0.0
 * @date 2020-02-28 10:22
 */
@RestController
public class TestController {


    @Autowired
    private BRXXService brxxService;

    @GetMapping("test")
    public List<BRXX> test(){
        return brxxService.getAll();
    }

    @GetMapping("testSign")
    public String testSign(){
        return "签名测试通过";
    }
}
