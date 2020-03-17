package com.viagra.wechatordering.service;

import com.lly835.bestpay.model.PayResponse;
import com.viagra.wechatordering.dto.OrderDTO;

/**
 * @Author: Selune
 * @Date: 5/30/19 4:47 PM
 */

public interface PayService {

    PayResponse create(OrderDTO orderDTO);
}
