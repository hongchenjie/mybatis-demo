package org.study.mybatis.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.study.mybatis.model.UserQuery;
import org.study.mybatis.service.UserService;
import org.study.mybatis.util.PageForm;

import java.util.Date;

//@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    //http://localhost:8080/user/list?pageNum=1&pageSize=2
    @GetMapping("list")
    public Object list(PageForm form) {
        log.info(form.toString());
        PageInfo pageInfo = userService.list(form);
        return pageInfo;
    }

    @GetMapping("transaction")
    public Object transaction() {
        return userService.transaction();
    }

    @GetMapping("add")
    public Object add() {
        return userService.add();
    }

    @GetMapping("insertList")
    public Object insertList() {
        return userService.insertList();
    }

    @GetMapping("interceptor")
    public Object interceptor() {
        return userService.interceptor();
    }

    @GetMapping("sqlInject")
    public Object sqlInject(UserQuery query) {
        return userService.sqlInject(query);
    }

    /**
     * controller类加事务注解，也起作用的，可以回滚
     * @return
     */
    @GetMapping("addControllerTransactional")
    public Object addControllerTransactional() {
        return userService.addControllerTransactional();
    }


    @GetMapping("methodA")
    public Object methodA() {
        userService.methodA();
        return "ok";
    }

    @PostMapping("/date")
    public Object date(@RequestBody UserQuery query) {
        Date inDate = query.getInDate();
        System.out.println(inDate);
        return inDate;
    }

}
