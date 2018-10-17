package cn.zyblogs.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Title: BrokerMessageLog.java
 * @Package cn.zyblogs.entity
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Getter
@Setter
@ToString
@Table(name = "broker_message_log")
public class BrokerMessageLog implements Serializable {

    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息内容
     */
    private String message;
    /**
     * '重试次数'
     */
    private Integer tryCount;
    /**
     * 投递状态 {@code com.myimooc.rabbitmq.ha.constant.Constants.OrderSendStatus}
     */
    private String status;
    /**
     * 下次重试时间
     */
    private Date nextRetry;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
