package com.liuwen.HelloController;

import lombok.Data;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 16:21
 * @Description:
 */
@Data
public class Engine {

    private String model;

    Engine(String model) {
        this.model = model;
    }

    public void work() {
        System.out.println(this.model + " engine work");
    }
}
