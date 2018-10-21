package cn.zyblogs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Title: Order.java
 * @Package cn.zyblogs.entity
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order")
public class Order implements Serializable {

    private String id;
    private String name;

    /**
     * 存储消息发送的唯一标识
     */
    @Column(name = "message_id")
    private String messageId;
}
