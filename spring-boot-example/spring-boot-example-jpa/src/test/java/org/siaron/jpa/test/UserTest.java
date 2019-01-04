package org.siaron.jpa.test;

import org.junit.Test;
import org.siaron.jpa.BaseTest;
import org.siaron.jpa.entity.UserEntity;
import org.siaron.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xielongwang
 * @create 2018-11-189:17 PM
 * @description
 */
public class UserTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userAddTest() {
        //1. 如果指定id,会生成select from example_jpa.user where id=?;的语句,但是不会按照你设置的id保存,因为指定了id序列生成
        userRepository.save(new UserEntity(100000L, "11", "22", "22"));
        //2.不加id 则不会生成
        /**
         * 插入过程:
         *  1. select nextval ('user_seq')
         *  2. insert into example_jpa.user (pass_word, ssn, user_name, id) values (?, ?, ?, ?)
         */
        userRepository.save(new UserEntity("111", "212", "221"));
    }
}