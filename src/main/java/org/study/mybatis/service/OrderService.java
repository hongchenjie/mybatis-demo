package org.study.mybatis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lipo
 * @version v1.0
 * @date 2020-01-15 16:28
 */
@Service
public class OrderService {

    @Transactional(propagation = Propagation.NESTED)
    public void methodB() {
        throw new RuntimeException();
    }
}
