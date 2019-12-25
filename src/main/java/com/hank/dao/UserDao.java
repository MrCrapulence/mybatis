package com.hank.dao;

import com.hank.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Insert("insert into user (username,birthday,sex,address) " +
            " values" +
            "( #{username},#{birthday},#{sex},#{address} )")
    void insert(User user);

    @Delete("delete from user where id = #{id}")
    void deleteById(Integer id);

    @Update("update user set " +
            "username = #{username}, " +
            "birthday = #{birthday}, " +
            "sex = #{sex}, " +
            "address = #{address} " +
            "where id = #{id}")
    void update(User user);

    @Select("select * from User")
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

}
