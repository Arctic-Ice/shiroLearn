package net.xdclass.xdclass_shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 * 单元测试用例执行顺序
 *
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass；
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuickStartTest5_3 {
    @Test
    public void testAuthentication() {
        // 创建SecurityManager工厂，通过配置文件ini创建
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbcrealm.ini");
        SecurityManager securityManager = factory.getInstance();

        // 将securityManager设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "123");
        subject.login(usernamePasswordToken);

        System.out.println("认证结果" + subject.isAuthenticated());
        System.out.println("是否有对应的role1角色：" + subject.hasRole("role1"));
        System.out.println("getPrincipal=" + subject.getPrincipal());
        subject.checkPermission("video:delete");
        System.out.println("是否有权限" + subject.isPermitted("video:find"));
        subject.logout();
        System.out.println("logout后认证结果：" + subject.isAuthenticated());
    }

    @Test
    public void test2() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://192.168.10.127:3306/xdclass_shiro?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        ds.setUsername("mysql");
        ds.setPassword("cy888888");

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setDataSource(ds);

        securityManager.setRealm(jdbcRealm);

        // 将securityManager设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 当前操作主体，application user
        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "123");
        subject.login(usernamePasswordToken);

        System.out.println("认证结果" + subject.isAuthenticated());
        System.out.println("是否有对应的role1角色：" + subject.hasRole("role1"));
        System.out.println("getPrincipal=" + subject.getPrincipal());
        subject.checkPermission("video:deleteff");
        System.out.println("是否有权限" + subject.isPermitted("video:find"));
        subject.logout();
        System.out.println("logout后认证结果：" + subject.isAuthenticated());
    }
}
