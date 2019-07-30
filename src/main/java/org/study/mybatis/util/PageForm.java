package org.study.mybatis.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 分页表单.
 *
 * @author <a href="mailto:simonzbf@163.com">大兵</a>
 * @version 1.0 18/4/24
 * @since 1.0
 */
@ToString(callSuper = true)
@Data
public class PageForm extends BaseForm {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

}