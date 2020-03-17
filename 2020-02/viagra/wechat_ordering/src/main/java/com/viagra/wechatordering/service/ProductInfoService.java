package com.viagra.wechatordering.service;

import com.viagra.wechatordering.dto.CartDTO;
import com.viagra.wechatordering.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: viagra
 * @Date: 2020/2/16 12:44
 * @Description:
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有上架商品
     * @return Up
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /** 增加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减少库存 */
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 上架 */
    ProductInfo onSale(String productId);

    /** 下架 */
    ProductInfo offSale(String productId);
}
