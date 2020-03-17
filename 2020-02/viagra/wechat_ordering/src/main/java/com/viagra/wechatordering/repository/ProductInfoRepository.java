package com.viagra.wechatordering.repository;

import com.viagra.wechatordering.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: viagra
 * @Date: 2020/2/16 12:51
 * @Description:
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /** 查询上架商品 */
    List<ProductInfo> findByProductStatus(Integer productStatus);


}
