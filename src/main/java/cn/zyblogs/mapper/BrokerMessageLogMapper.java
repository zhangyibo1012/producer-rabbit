package cn.zyblogs.mapper;

import cn.zyblogs.entity.BrokerMessageLog;
import cn.zyblogs.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Title: BrokerMessageLogMapper.java
 * @Package cn.zyblogs.mapper
 * @Description: TODO
 * @Author ZhangYB
 * @Date 2018-10-17 17:28
 * @Version V1.0
 */
@Mapper
public interface BrokerMessageLogMapper extends MyMapper<BrokerMessageLog> {

    /**
     * 更新消息状态
     *
     */
    @Update(value = " update broker_message_log set status = #{status} , updateTime = sysdate() where messageId = #{messageId}")
    void changeBrokerMessageLogStatus(BrokerMessageLog messageLog);

    /**
     * 查询消息状态为0 且 已经超时的消息
     *
     * @return 消息日志集合
     */
    @Select(value = "select * from broker_message_log where status = '0' and nextRetry <= sysdate()")
    List<BrokerMessageLog> listSendFailureAndTimeoutMessage();

    /**
     * 更新重试次数+1
     *update broker_message_log  set try_count = try_count + 1,update_time = sysdate() where message_id = '1539773166143$b41600e6-5b9a-4525-9b97-601c46e0e23e';
     * @param po 消息日志
     */
    @Update(value = "update broker_message_log  set tryCount = tryCount + 1,updateTime = sysdate() where messageId = #{messageId}")
    void updateRetryCount(BrokerMessageLog po);
}
