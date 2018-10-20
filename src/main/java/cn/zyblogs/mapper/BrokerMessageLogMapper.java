package cn.zyblogs.mapper;

import cn.zyblogs.entity.BrokerMessageLog;
import cn.zyblogs.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BrokerMessageLogMapper extends MyMapper<BrokerMessageLog> {

    /**
     * 更新消息状态
     *
     */
//    @Update(value = "update broker_message_log set status = 2 , update_time = sysdate() where message_id = #{message_id}")
//    void changeBrokerMessageLogStatus( @Param("message_id") String messageId);

    /**
     * 查询消息状态为0 且 已经超时的消息
     *
     *  @Select(value = "select try_count as tryCount from broker_message_log where status = '0' and next_retry <= sysdate()")
     *
     *  查询出来的数据，与实体bean的字段不相匹配，导致null
     *
     * @return 消息日志集合
     */
    @Select(value = "select * from broker_message_log where status = '0' and next_retry <= sysdate()")
    List<BrokerMessageLog> listSendFailureAndTimeoutMessage();

    /**
     * 更新重试次数+1
     *update broker_message_log  set try_count = try_count + 1,update_time = sysdate() where message_id = '1539773166143$b41600e6-5b9a-4525-9b97-601c46e0e23e';
     * @param po 消息日志
     */
    @Update(value = "update broker_message_log  set try_count = try_count + 1,update_time = sysdate() where message_id = #{message_id}")
    void updateRetryCount(@Param("message_id") String messageId );
}