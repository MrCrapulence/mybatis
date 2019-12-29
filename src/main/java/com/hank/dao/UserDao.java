package com.hank.dao;

import com.hank.domain.Account;
import com.hank.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

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
    User findUserById(Integer id);

    /**
     * 一对多关联查询（用户表和账户表是一对多的关系）
     * @return
     */
    @Select("select * from user")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            @Result(property = "account", column = "id", javaType = List.class,
                    many = @Many(select = "com.hank.dao.AccountDao.findByUid", fetchType = FetchType.LAZY))
    })
    List<User> findAllwithAccount();

}
