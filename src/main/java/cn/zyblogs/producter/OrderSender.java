package cn.zyblogs.producter;

import cn.zyblogs.constant.Constants;
import cn.zyblogs.entity.BrokerMessageLog;
import cn.zyblogs.entity.Order;
import cn.zyblogs.mapper.BrokerMessageLogMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: OrderSender.java
 * @Package cn.zyblogs.producter
 * @Description: TODO 发送消息
 * @Author ZhangYB
 * @Version V1.0
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 回调方法：confirm确认
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.out.println("数据correlationData：" + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                // 如果confirm返回成功，则进行更新
                BrokerMessageLog messageLog = new BrokerMessageLog();
                messageLog.setMessageId(messageId);
                messageLog.setStatus(Constants.OrderSendStatus.SEND_SUCCESS);
                brokerMessageLogMapper.updateByPrimaryKeySelective(messageLog);
            } else {
                // 失败则进行具体的后续操作：重试或者补偿等
                System.out.println("异常处理...");
            }

        }
    };

//        public void send(Order order) throws Exception {
//            // exchange 交换机 routingKey 路由键 object 消息体内容 correlationData消息唯一id
//
//            CorrelationData correlationData = new CorrelationData();
//            correlationData.setId(order.getMessageId());
//
//            rabbitTemplate.convertAndSend("order-exchange", "order.abcd", order, correlationData);
//        }
    /**
     * 发送订单
     *
     * @param order 订单
     */
    public void send(Order order) {
        // 设置回调方法
        this.rabbitTemplate.setConfirmCallback(confirmCallback);
        // 消息ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        // 发送消息
        this.rabbitTemplate.convertAndSend("order-exchange", "order.a", order, correlationData);
    }
}