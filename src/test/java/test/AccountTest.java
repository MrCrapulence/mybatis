package test;

import com.alibaba.fastjson.JSON;
import com.hank.dao.AccountDao;
import com.hank.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AccountTest {

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
    public void findAccountWithUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        List<Account> list = accountDao.findAccountWithUser();
        for (Account account : list) {
            System.out.println(account.getMoney());
            System.out.println(account.getUser().getUsername());
            System.out.println();
        }
        System.out.println(JSON.toJSON(list));
    }


}
