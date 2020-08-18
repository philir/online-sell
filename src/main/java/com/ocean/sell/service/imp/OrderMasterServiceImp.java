package com.ocean.sell.service.imp;

import com.ocean.sell.converter.OrderMaster2OrderDTOConverter;
import com.ocean.sell.dataobject.OrderDetail;
import com.ocean.sell.dataobject.OrderMaster;
import com.ocean.sell.dataobject.ProductInfo;
import com.ocean.sell.dto.CarDTO;
import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.PayStatusEnum;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.ResponseException;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.repository.OrderMasterRepository;
import com.ocean.sell.repository.OrdereDetailRepository;
import com.ocean.sell.service.OrderMasterService;
import com.ocean.sell.service.PayService;
import com.ocean.sell.service.ProductInfoService;
import com.ocean.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImp implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrdereDetailRepository ordereDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private PayService payService;
    @Autowired
    private WebSocket webSocket;

    @Autowired
    private PushMessageImpl pushMessage;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String productDes = "";
        String orderId = KeyUtil.genUniqueKey();
        orderDTO.setOrderId(orderId);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        List<CarDTO> carDTOList = new ArrayList<>();
        //1.查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                ///throw new ResponseException();
            }
            productDes += productInfo.getProductName() + "+";
            CarDTO carDTO = new CarDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            carDTOList.add(carDTO);
            //2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //(订单详情入库orderDetail)
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            /*orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductIcon(productInfo.getProductIcon());*/
            BeanUtils.copyProperties(productInfo,orderDetail);
            ordereDetailRepository.save(orderDetail);
        }

        //3.写入订单数据库（orderMaster）
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setBuyerName(orderDTO.getBuyerName());
        orderMaster.setBuyerPhone(orderDTO.getBuyerPhone());
        orderMaster.setBuyerAddress(orderDTO.getBuyerAddress());
        orderMaster.setBuyerOpenid(orderDTO.getBuyerOpenid());
        orderMaster.setOrderAmount(orderAmount);
        //订单商品描述处理
        productDes = productDes.substring(0,productDes.length()-1);
        if(productDes.length() <= 7){
            productDes += " 1件商品";
        }else{
            productDes = productDes.substring(0,7) + "...等1件商品";
        }
        orderMaster.setOrderProductDes(productDes);
        orderMasterRepository.save(orderMaster);
        //4.扣库存
        productInfoService.decreaseStock(carDTOList);
        return orderDTO;
    }

    /**
     * 查找单个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = ordereDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 查询个人的订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 查找所有订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Integer orderStatus,Pageable pageable) {
       Page<OrderMaster> orderMasterPage = orderMasterRepository.findOrderListByOrderStatus(orderStatus,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 查找已支付的新订单
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findNewOrderList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findNewOrderByOrderStatusAndPayStatus(OrderStatusEnums.NEW.getCode(),PayStatusEnum.SUCCESS.getCode(),pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 查找未支付的新订单
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findUnpaidList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findNewOrderByOrderStatusAndPayStatus(OrderStatusEnums.NEW.getCode(),PayStatusEnum.WAIT.getCode(),pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 查找已取消的订单
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findCancelList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findOrderListByOrderStatus(OrderStatusEnums.CANCEL.getCode(),pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 查找已完结的订单
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findFinshedList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findOrderListByOrderStatus(OrderStatusEnums.FINISHED.getCode(),pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 订单取消
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[取消订单]订单状态错误，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[取消订单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        /*for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            productInfo.setProductStock(productInfo.getProductStock()+orderDetail.getProductQuantity());
            productInfoService.save(productInfo);
        }*/
        List<CarDTO> carDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CarDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(carDTOS);
        //如果已支付，需要退款
        if(!"oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls".equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            return orderDTO;
        }
        if(orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * 删除订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO delete(OrderDTO orderDTO){
        List<OrderDetail> orderDetailList = ordereDetailRepository.findByOrderId(orderDTO.getOrderId());
        if(orderDetailList.size() <= 0){
            log.error("[删除订单]: 订单详情不存在");
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        ordereDetailRepository.deleteInBatch(orderDetailList);
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if(orderMaster == null){
            log.error("[删除订单]: 订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        orderMasterRepository.delete(orderMaster);
        return orderDTO;
    }

    /**
     * 订单接收
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO accept(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[接收订单]订单状态错误，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.ACCEPTED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[接收订单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 订单完结
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[完成订单]订单状态错误，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[完成订单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板消息
        pushMessage.orderStatus(orderDTO);

        return orderDTO;
    }

    /**
     * 订单支付
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[订单支付完成]订单状态错误，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付完成]订单支付状态错误，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[订单支付完成] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

}
