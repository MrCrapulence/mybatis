package com.hank.dao;

import com.hank.domain.Account;
import com.hank.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface AccountDao {

    /**
     * 一对一关联查询（账户表和用户表是一对一的关系）
     * fetchType = FetchType.LAZY 懒加载，用到才查，不用不查，且若是相同的sql语句查询，会从缓存中直接取结果
     *
     * @return
     */
    @Select("select * from account")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "money", column = "money"),
            // 下面是添加对象属性的方式，此处的column为两张表的关联列
            @Result(property = "user", column = "uid", javaType = User.class,
                    one = @One(select = "com.hank.dao.UserDao.findUserById", fetchType = FetchType.LAZY))
    })
    List<Account> findAccountWithUser();

    @Select("select * from account where uid = #{uid}")
    Account findByUid(Integer uid);
}