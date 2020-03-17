package com.liuwen.ConditionalOnMissingBean;

import org.springframework.stereotype.Service;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 09:13
 * @Description:
 */
@Service
public class Babana implements Fighter {
    @Override
    public void fight(){
        System.out.println("Banana: 自由的气息，蕉迟但到");
    }
}
