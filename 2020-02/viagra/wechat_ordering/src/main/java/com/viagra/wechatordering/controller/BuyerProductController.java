package com.viagra.wechatordering.controller;

import com.viagra.wechatordering.pojo.ProductCategory;
import com.viagra.wechatordering.pojo.ProductInfo;
import com.viagra.wechatordering.service.ProductCategoryService;
import com.viagra.wechatordering.service.ProductInfoService;
import com.viagra.wechatordering.utils.ResultVOUtil;
import com.viagra.wechatordering.vo.ProductInfoVO;
import com.viagra.wechatordering.vo.ProductVO;
import com.viagra.wechatordering.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: viagra
 * @Date: 2020/2/16 11:47
 * @Description:
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;



    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123") // 存储redis，下一次不在访问方法
    public ResultVO list() {
        // 1. 查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        // 2.2 Lambda
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList =
                productCategoryService.findByCategoryTypeIn(categoryTypeList);

        // 3.数据拼接
        List<ProductVO> productVOList = new ArrayList<>();
        // 3.1 先拼装类目
        for (ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            // 3.2 再拼装商品详情
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
