package com.viagra.wechatordering.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 表单验证
 * 订单验证
 * @Author: Selune
 * @Date: 5/20/19 2:12 PM
 */

@Data
public class OrderForm {

    /** 买家姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;

    /** 买家手机号 */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /** 买家地址 */
    @NotEmpty(message = "地址必填")
    private String address;

    /** 买家微信openid */
    @NotEmpty(message = "openid必填")
    private String openid;

    /** 买家购物车信息 */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
