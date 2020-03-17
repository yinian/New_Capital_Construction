package com.viagra.wechatordering.service.impl;

import com.viagra.wechatordering.converter.OrderMaster2OrderDTOConverter;
import com.viagra.wechatordering.dto.CartDTO;
import com.viagra.wechatordering.dto.OrderDTO;
import com.viagra.wechatordering.enums.OrderStatusEnum;
import com.viagra.wechatordering.enums.PayStatusEnum;
import com.viagra.wechatordering.enums.ResultEnum;
import com.viagra.wechatordering.exception.WeChatOrderException;
import com.viagra.wechatordering.pojo.OrderDetail;
import com.viagra.wechatordering.pojo.OrderMaster;
import com.viagra.wechatordering.pojo.ProductInfo;
import com.viagra.wechatordering.repository.OrderDetailRepository;
import com.viagra.wechatordering.repository.OrderMasterRepository;
import com.viagra.wechatordering.service.OrderService;
import com.viagra.wechatordering.service.ProductInfoService;
import com.viagra.wechatordering.service.PushMessageService;
import com.viagra.wechatordering.service.WebSocket;
import com.viagra.wechatordering.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Selune
 * @Date: 5/14/19 6:45 PM
 */

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date time= null;
        try {
            time = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 1. 查询商品( 数量, 价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (null == productInfo) {
                throw new WeChatOrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);



            // 3. 订单详情入库 ( orderDetail)
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);

            orderDetailRepository.save(orderDetail);
        }


        // 3. 主订单入库( orderMaster)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(time);
        orderMaster.setUpdateTime(time);
        orderMasterRepository.save(orderMaster);

        // 4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                        .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                        .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        // 5. 发送WebSocket消息
        webSocket.sendMessage("新订单：" + orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        // 查看主订单
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (null == orderMaster) {
            throw new WeChatOrderException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 查看订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new WeChatOrderException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,
                pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.conver(orderMasterPage.getContent());


        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，orderId= {}, orderStatus= {}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (null == updateResult) {
            log.error("【取消订单】订单更新失败，orderMaster= {}", orderMaster);
            throw new WeChatOrderException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 3. 返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO= {}", orderDTO);
            throw new WeChatOrderException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList =
                orderDTO.getOrderDetailList().stream()
                        .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                        .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        // 4. 如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO: 退款
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // 1. 订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId= {}, orderStatus= {}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (null == updateResult) {
            log.error("【完结订单】更新失败，orderMaster= {}", orderMaster);
            throw new WeChatOrderException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 3. 推送微信模板信息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】订单状态不正确，orderId= {}, orderStatus= {}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new WeChatOrderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付】订单支付状态不正确, orderDTO= {}", orderDTO);
            throw new WeChatOrderException(ResultEnum.ORDER_PAY_STATUS);
        }

        // 3. 修改支付状态
        orderDTO .setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (null == updateResult) {
            log.error("【订单支付】支付失败， orderMaster= {}", orderMaster);
            throw new WeChatOrderException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList =
                OrderMaster2OrderDTOConverter.conver(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
