package cn.zyblogs.task;

import cn.zyblogs.constant.Constants;
import cn.zyblogs.entity.BrokerMessageLog;
import cn.zyblogs.entity.Order;
import cn.zyblogs.mapper.BrokerMessageLogMapper;
import cn.zyblogs.producter.OrderSender;
import cn.zyblogs.utils.FastJsonConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: RetryMessageTask.java
 * @Package cn.zyblogs.task
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Component
@Slf4j
public class RetryMessageTask {

    @Autowired
    private OrderSender orderSender;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 启动完成3秒后开始执行，每隔10秒执行一次
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void retrySend() {
        log.info("--------重发消息定时任务开始--------");
        // 查询 status = 0 和 timeout 的消息日志
        List<BrokerMessageLog> pos = this.brokerMessageLogMapper.listSendFailureAndTimeoutMessage();

        System.out.println(">>>>>>>" + pos.size());
        System.out.println(pos.toString());

        for (BrokerMessageLog po : pos) {
            log.info("--------处理消息日志：{}--------", po);
            if (po.getTryCount() >= Constants.MAX_RETRY_COUNT) {
                // 更新状态为失败
                BrokerMessageLog messageLog = new BrokerMessageLog();
                messageLog.setMessageId(po.getMessageId());
                messageLog.setStatus(Constants.OrderSendStatus.SEND_FAILURE);
                //   根据主键更新全部字段  值不能为null updateByPrimaryKey
                //   根据主键更新部分字段      updateByPrimaryKeySelective
                this.brokerMessageLogMapper.updateByPrimaryKeySelective(messageLog);
            } else {
                // 进行重试，重试次数+1
                this.brokerMessageLogMapper.updateRetryCount(po.getMessageId());
                Order reSendOrder = FastJsonConvertUtils.convertJsonToObject(po.getMessage(), Order.class);
                try {
                    this.orderSender.send(reSendOrder);
                } catch (Exception ex) {
                    // 异常处理
                    log.info("--------消息发送异常：{}--------", ex);
                }
            }
        }
        log.info("--------重发消息定时任务结束--------");
    }
}
