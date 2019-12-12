package org.study.mybatis.util;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface TkMapper<T> extends ConditionMapper<T>, BaseMapper<T>, RowBoundsMapper<T>, Marker, InsertListMapper<T> {
}
