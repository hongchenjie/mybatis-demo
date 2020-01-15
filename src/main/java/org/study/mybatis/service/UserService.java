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
import org.study.mybatis.model.UserQuery;
import org.study.mybatis.util.PageForm;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    private UserService2 userService2;

    public Object transaction2() {
        for (int i = 0; i < 1; i++) {
            try {
                userService2.doTransaction();

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return "ok";
    }

    public Object transaction() {
        for (int i = 0; i < 1; i++) {
            try {
                //transactionTemplate.execute(tc -> {

                    doTransaction();

                //    return true;
                //});
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

        return "ok";
    }

    //同类的方法调用，事务注解@Transactional不起作用，使用事务模版
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

    @Transactional(rollbackFor = Exception.class)
    public Object add() {
        doTransaction();
        return "ok";
    }

    public Object insertList() {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setName("insertList");
        list.add(user);

        User user2 = new User();
        user2.setName("insertList2");
        list.add(user2);

        userMapper.insertList(list);

        return "ok";
    }

    public Object interceptor() {
        return userMapper.list();
    }


    /**
     * tk不会sql注入，因为是预编译的，而且条件都用括号扩起来
     * SELECT id,name,age,create_time,update_time FROM user WHERE ( name = ? )
     * @param query
     * @return
     */
    public Object sqlInject(UserQuery query) {
        Example example = new Example(User.class);
        //example.createCriteria().andEqualTo("name", "lipo");
        example.createCriteria().andEqualTo("name", "' or 1=1#");
        List<User> users = userMapper.selectByCondition(example);
        log.info(users.toString());
        return users;
    }

    public Object addControllerTransactional() {
        Date date = new Date();
        User user = new User();
        user.setName("addControllerTransactional");
        user.setCreateTime(date);
        userMapper.insertSelective(user);

        int i = 1 / 0;

        User user2 = new User();
        user2.setName("addControllerTransactional2");
        user2.setCreateTime(date);
        userMapper.insertSelective(user2);

        return "ok";
    }

    @Autowired
    private OrderService orderService;

    @Transactional
    public void methodA() {
        try{
            orderService.methodB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
