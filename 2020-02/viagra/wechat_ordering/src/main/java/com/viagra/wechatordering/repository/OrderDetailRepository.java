package com.viagra.wechatordering.repository;

import com.viagra.wechatordering.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Selune
 * @Date: 5/14/19 4:22 PM
 */

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
