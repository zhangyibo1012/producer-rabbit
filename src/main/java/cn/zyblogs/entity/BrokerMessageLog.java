package cn.zyblogs.entity;

import lombok.*;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "broker_message_log")
public class BrokerMessageLog {
    @Id
    @Column(name = "message_id")
    private String messageId;

    private String message;

    @Column(name = "try_count")
    private Integer tryCount;

    private String status;

    @Column(name = "next_retry")
    private Date nextRetry;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}