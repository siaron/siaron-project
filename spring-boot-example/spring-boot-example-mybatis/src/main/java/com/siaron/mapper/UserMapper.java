package com.siaron.mapper;

import com.siaron.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xielongwang
 * @create 2019-01-219:22 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public interface UserMapper {

    @Insert("INSERT INTO user (name,age) VALUES (#{name},#{age})")
    boolean addUser(@Param("name") String name, @Param("age") Integer age);

    @Select("SELECT * FROM user WHERE age > #{age}")
    List<User> selectUsers(@Param("age") Integer age);
}