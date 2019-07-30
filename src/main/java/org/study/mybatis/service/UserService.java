package org.study.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.mybatis.dao.entity.User;
import org.study.mybatis.dao.mapper.UserMapper;
import org.study.mybatis.util.PageForm;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //http://localhost:8080/user/list?pageNum=1&pageSize=2
    public PageInfo list(PageForm form) {
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<User> list = userMapper.list();
        return new PageInfo(list);
    }
}
