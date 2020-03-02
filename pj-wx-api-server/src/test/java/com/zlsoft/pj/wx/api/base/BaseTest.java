package com.zlsoft.pj.wx.api.base;

import com.zlsoft.pj.wx.api.PjWxApiApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 公共的测试类，所有测试类都要继承这个类
 * @author dp
 * @version 1.0.0
 * @date 2020-02-28 10:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PjWxApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
}
