package cn.zyblogs.service;

import cn.zyblogs.constant.Constants;
import cn.zyblogs.entity.BrokerMessageLog;
import cn.zyblogs.entity.Order;
import cn.zyblogs.mapper.BrokerMessageLogMapper;
import cn.zyblogs.mapper.OrderMapper;
import cn.zyblogs.producter.OrderSender;
import cn.zyblogs.utils.FastJsonConvertUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Title: OrderService.java
 * @Package cn.zyblogs.service
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired(required=true)
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private OrderSender orderSender;

    /**
     * 创建订单
     *
     * @param order 订单
     */
    public void  create(Order order){
        // 当前时间
        Date orderTime = new Date();
        // 业务数据入库
        this.orderMapper.insert(order);
        // 消息日志入库
        BrokerMessageLog messageLog = new BrokerMessageLog();
        messageLog.setMessageId(order.getMessageId());
        messageLog.setMessage(FastJsonConvertUtils.convertObjectToJson(order));
        messageLog.setTryCount(0);
        messageLog.setStatus(Constants.OrderSendStatus.SENDING);
        messageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        this.brokerMessageLogMapper.insert(messageLog);
        // 发送消息
        this.orderSender.send(order);
    }




}
