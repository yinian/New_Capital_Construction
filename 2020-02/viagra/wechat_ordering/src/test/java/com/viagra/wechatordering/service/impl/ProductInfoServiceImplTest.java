package com.viagra.wechatordering.service.impl;

import com.viagra.wechatordering.enums.ProductStatusEnum;
import com.viagra.wechatordering.pojo.ProductInfo;
import com.viagra.wechatordering.repository.ProductInfoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Selune
 * @Date: 5/14/19 2:13 PM
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest = new PageRequest(2, 2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123450");
        productInfo.setProductName("可乐鸡翅");
        productInfo.setProductPrice(new BigDecimal(25.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("可乐鸡翅");
        productInfo.setProductIcon("xxxxxxxxxxx");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(6);

        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);

    }

    @Test
    @Transactional
    public void onSale() throws Exception {
        productInfoService.onSale("123456");
        Assert.assertEquals("结果正确", 0,
                (int) productInfoRepository.findOne("123456").getProductStatusEnum().getCode());
    }

    @Test
    public void offSale() throws Exception {
        productInfoService.offSale("123457");
        Assert.assertEquals("结果正确", 1,
                (int) productInfoRepository.findOne("123457").getProductStatusEnum().getCode());
    }
}