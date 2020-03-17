package com.viagra.wechatordering.controller;

import com.viagra.wechatordering.converter.OrderForm2OrderDTOConverter;
import com.viagra.wechatordering.dto.OrderDTO;
import com.viagra.wechatordering.enums.ResultEnum;
import com.viagra.wechatordering.exception.WeChatOrderException;
import com.viagra.wechatordering.form.OrderForm;
import com.viagra.wechatordering.service.BuyerService;
import com.viagra.wechatordering.service.OrderService;
import com.viagra.wechatordering.utils.ResultVOUtil;
import com.viagra.wechatordering.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: viagra
 * @Date: 2020/2/16 18:44
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;


    // 创建订单

    /**
     * 使用BindingResult校验参数  @Valid 是一一对应的
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> WechatAccountConfigcreate(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {

        // 1. 判断表单校验有没有错误
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数错误，orderForm= {}", orderForm);
            throw new WeChatOrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.conver(orderForm);

        // 2. 判断购物车是否为空
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车为空");
            throw new WeChatOrderException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }


}
