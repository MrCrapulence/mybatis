package test;

import com.alibaba.fastjson.JSON;
import com.hank.dao.UserDao;
import com.hank.domain.Account;
import com.hank.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UserTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws Exception {
        try {
            // 得到主配置文件流信息
            InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
            // 获取SqlSessionFactory工厂对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAll() throws Exception {
        // 通过SqlSessionFactory工厂来生产SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 使用SqlSession对象来生成接口代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 调用代理对象中的方法
        List<User> list = userDao.findAll();
        sqlSession.commit();
        sqlSession.close();
        // 处理结果
        System.out.println(list);
    }

    @Test
    public void insert() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUsername("hank1");
        user.setBirthday(Date.from((LocalDateTime.now().atZone(ZoneId.systemDefault())).toInstant()));
        user.setSex("0");
        user.setAddress("china");
        userDao.insert(user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void update() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setId(3);
        user.setUsername("hank2");
        user.setBirthday(Date.from((LocalDateTime.now().atZone(ZoneId.systemDefault())).toInstant()));
        user.setSex("1");
        userDao.update(user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void delete() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.deleteById(4);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 查询测试所有用户及用户下账户集合
     * 每次都建SqlSession是因为创建多个session实例，防止多线程问题
     */
    @Test
    public void findAllWithAccount() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> list = userDao.findAllwithAccount();
        for (User user : list) {
            System.out.println(user.getUsername());
            if (user.getAccount() != null) {
                for (Account account : user.getAccount()) {
                    System.out.println(user.getUsername() + ":" + account.getMoney());
                }
            }
        }
    }


}
