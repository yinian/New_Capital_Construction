package com.viagra.wechatordering.repository;

import com.viagra.wechatordering.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Selune
 * @Date: 7/8/19 11:03 AM
 */

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    /**
     * 通过openId查找
     * @param openId
     * @return
     * @throws Exception
     */
    SellerInfo findByOpenId(String openId) throws Exception;
}
