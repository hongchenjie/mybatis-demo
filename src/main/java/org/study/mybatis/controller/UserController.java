package org.study.mybatis.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.mybatis.service.UserService;
import org.study.mybatis.util.PageForm;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    //http://localhost:8080/user/list?pageNum=1&pageSize=2
    @RequestMapping("list")
    public Object list(PageForm form) {
        log.info(form.toString());
        PageInfo pageInfo = userService.list(form);
        return pageInfo;
    }

}
