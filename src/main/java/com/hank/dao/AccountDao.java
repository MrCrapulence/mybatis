package com.hank.dao;

import com.hank.domain.Account;
import com.hank.domain.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
            @Result(property = "user", column = "uid", javaType = User.class,
                    one = @One(select = "com.hank.dao.UserDao.findById", fetchType = FetchType.LAZY))
    })
    List<Account> findAccountWithUser();
}
