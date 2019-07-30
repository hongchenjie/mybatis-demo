package org.study.mybatis.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.mybatis.service.UserService;
import org.study.mybatis.util.PageForm;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    //
    @RequestMapping("list")
    public Object list(PageForm form) {
        PageInfo pageInfo = userService.list(form);
        return pageInfo;
    }

}
