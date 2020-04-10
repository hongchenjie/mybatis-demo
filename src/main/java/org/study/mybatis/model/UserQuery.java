package org.study.mybatis.model;

import lombok.Data;

import java.util.Date;

/**
 * @author lipo
 * @version v1.0
 * @date 2019-12-27 11:02
 */
@Data
public class UserQuery {
    private String name;
    private Date inDate;
}
