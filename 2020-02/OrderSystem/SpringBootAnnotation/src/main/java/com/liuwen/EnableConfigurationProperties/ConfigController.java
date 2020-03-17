package com.liuwen.EnableConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 15:14
 * @Description:
 */
@RestController
public class ConfigController {

    @Autowired
    private EnableConfigurationPropertiesDomain propertiesDomain;

    @RequestMapping("index")
    public String index() {
        System.out.println(propertiesDomain);
        return "success";
    }

}
