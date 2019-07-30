package org.study.mybatis.util;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;

public interface TkMapper<T> extends ConditionMapper<T>, BaseMapper<T>, RowBoundsMapper<T>, Marker {
}
