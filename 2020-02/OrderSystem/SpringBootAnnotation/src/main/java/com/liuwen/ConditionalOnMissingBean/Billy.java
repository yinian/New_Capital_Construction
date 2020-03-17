package com.liuwen.ConditionalOnMissingBean;

import org.springframework.stereotype.Service;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 09:14
 * @Description:
 * 1、@ConditionalOnBean（xxx.class）就是为了判断 xxx.class是否存在，
 * 并已注释了springboot容器里了;
 * 2、@ConditionalOnMissingBean 则是在第一点不存在的情况下起作用；
 */
//@Service
public class Billy implements Fighter {
    public void fight(){
        System.out.println("Billy：吾乃新日暮里的王，三界哲学的主宰。");

    }
}
