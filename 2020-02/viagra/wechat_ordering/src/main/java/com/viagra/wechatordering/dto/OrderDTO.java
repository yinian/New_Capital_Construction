package com.viagra.wechatordering.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.viagra.wechatordering.enums.OrderStatusEnum;
import com.viagra.wechatordering.enums.PayStatusEnum;
import com.viagra.wechatordering.pojo.OrderDetail;
import com.viagra.wechatordering.utils.EnumUtils;
import com.viagra.wechatordering.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO: 数据传输对象 Data Transfer Object
 * @Author: Selune
 * @Date: 5/14/19 6:39 PM
 */

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) // 当字段为空时，不返回
//@JsonInclude(JsonInclude.Include.NON_NULL) // 当字段为空时，不返回
public class OrderDTO {

    /** 订单id */
    private String orderId;

    /** 买家姓名 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信 openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 默认未支付 */
    private Integer orderStatus;

    /** 支付状态 */
    private Integer payStatus;

    /** 创建时间
     *  Json 时间转换
     *  Date -> Long
     *  此注解用于属性或者getter方法上,比如将一个Date类型的变量转换成Long类型，
     *  或是序列化一个double时在其后面限制两位小数点。
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList = new ArrayList<>();

    /** 转成json格式时，忽略它@JsonIgnore用于字段上，表示该字段在序列化和反序列化的时候都将被忽略。     */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtils.getByCode(payStatus, PayStatusEnum.class);
    }
}
