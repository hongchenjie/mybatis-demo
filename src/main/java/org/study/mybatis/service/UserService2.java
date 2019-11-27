package org.study.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.mybatis.dao.entity.User;
import org.study.mybatis.dao.mapper.UserMapper;

import java.util.Date;

@Service
@Slf4j
public class UserService2 {
    @Autowired
    private UserMapper userMapper;

    //不同类的方法调用，事务注解@Transactional起作用
    @Transactional(rollbackFor = Exception.class)
    public void doTransaction() {
        Date date = new Date();
        User user = new User();
        user.setName("transaction");
        user.setCreateTime(date);
        userMapper.insertSelective(user);

        int i = 1 / 0;

        User user2 = new User();
        user2.setName("transaction2");
        user2.setCreateTime(date);
        userMapper.insertSelective(user2);

    }

}
