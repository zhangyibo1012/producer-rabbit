package cn.zyblogs.mapper;

import cn.zyblogs.entity.Order;
import cn.zyblogs.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title: OrderMapper.java
 * @Package cn.zyblogs.mapper
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Mapper
public interface OrderMapper extends MyMapper<Order> {
}
