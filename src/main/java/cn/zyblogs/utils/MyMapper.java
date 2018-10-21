package cn.zyblogs.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Title: MyMapper.java
 * @Package cn.zyblogs.utils
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
