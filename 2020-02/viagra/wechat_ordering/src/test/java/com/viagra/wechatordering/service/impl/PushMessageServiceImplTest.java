package com.viagra.wechatordering.service.impl;

import com.viagra.wechatordering.dto.OrderDTO;
import com.viagra.wechatordering.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: Selune
 * @Date: 7/9/19 8:58 AM
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1557836463939");
        pushMessageService.orderStatus(orderDTO);
    }
}