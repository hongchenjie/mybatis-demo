package org.study.mybatis.dao.mapper;

import org.springframework.stereotype.Repository;
import org.study.mybatis.dao.entity.User;
import org.study.mybatis.util.TkMapper;

import java.util.List;

@Repository
public interface UserMapper extends TkMapper<User> {
    List<User> list();

}