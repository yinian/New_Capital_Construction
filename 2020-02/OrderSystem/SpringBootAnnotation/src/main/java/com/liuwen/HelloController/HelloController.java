package com.liuwen.HelloController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 16:20
 * @Description:
 */
@RestController
public class HelloController {

    @Autowired
    private Engine engine;

    @RequestMapping("/hello")
    public String hello() {
        engine.work();
        return "HelloController";
    }
}
