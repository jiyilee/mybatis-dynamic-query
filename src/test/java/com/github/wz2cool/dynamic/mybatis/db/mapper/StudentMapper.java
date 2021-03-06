package com.github.wz2cool.dynamic.mybatis.db.mapper;

import com.github.wz2cool.dynamic.mybatis.db.model.entity.table.StudentDO;
import com.github.wz2cool.dynamic.mybatis.mapper.DynamicQueryMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Frank
 * @date 2020/05/02
 **/
@Mapper
public interface StudentMapper extends DynamicQueryMapper<StudentDO> {
}
