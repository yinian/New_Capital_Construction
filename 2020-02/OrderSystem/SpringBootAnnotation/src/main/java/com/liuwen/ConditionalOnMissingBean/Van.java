package com.liuwen.ConditionalOnMissingBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 09:12
 * @Description:
 */
@Service
public class Van {
    @Autowired
    private Fighter fighter;

    public void fight(){
        System.out.println("van：boy next door,do you like 玩游戏");
        fighter.fight();
    }
}
