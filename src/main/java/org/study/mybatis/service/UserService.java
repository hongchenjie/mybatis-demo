package org.study.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.study.mybatis.dao.entity.User;
import org.study.mybatis.dao.mapper.UserMapper;
import org.study.mybatis.util.PageForm;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public PageInfo list(PageForm form) {
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<User> list = userMapper.list();
        return new PageInfo<>(list);
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    public Object transaction() {
        for (int i = 0; i < 1; i++) {
            try {
                transactionTemplate.execute(tc -> {
                    doTransaction();
                    return true;
                });
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

        return "ok";
    }

    //同类的方法调用，事务注解@Transactional不起作用，使用事务模版
    //@Transactional(rollbackFor = Exception.class)
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
