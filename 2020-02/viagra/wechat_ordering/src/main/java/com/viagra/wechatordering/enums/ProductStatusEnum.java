package com.viagra.wechatordering.enums;

import lombok.Getter;

/**
 * @Auther: viagra
 * @Date: 2020/2/16 12:21
 * @Description:
 */
@Getter
public enum  ProductStatusEnum implements CodeEnum {

    /**
     * UP 上架
     * DOWN 下架
     */
    UP(0, "上架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}