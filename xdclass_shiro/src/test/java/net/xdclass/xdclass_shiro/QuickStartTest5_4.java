package net.xdclass.xdclass_shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 单元测试用例执行顺序
 *
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass；
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuickStartTest5_4 {

    private CustoRealm custoRealm = new CustoRealm();
    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        // 构建环境
        defaultSecurityManager.setRealm(custoRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testAuthentication() {
        // 获取当前操作的主体
        Subject subject = SecurityUtils.getSubject();
        // 用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "123");
        subject.login(usernamePasswordToken);

        System.out.println("认证结果" + subject.isAuthenticated());
        System.out.println("是否有对应的role1角色：" + subject.hasRole("role1"));
        System.out.println("getPrincipal=" + subject.getPrincipal());
//        subject.checkPermission("video:find");
        System.out.println("是否有权限" + subject.isPermitted("video:find1"));
        subject.logout();
        System.out.println("logout后认证结果：" + subject.isAuthenticated());
    }
}
