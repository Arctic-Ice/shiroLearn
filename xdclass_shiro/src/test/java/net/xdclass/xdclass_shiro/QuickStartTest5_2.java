package net.xdclass.xdclass_shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.shiro.mgt.SecurityManager;

/**
 * 单元测试用例执行顺序
 *
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass；
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuickStartTest5_2 {
    @Test
    public void testAuthentication() {
        // 创建SecurityManager工厂，通过配置文件ini创建
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        // 将securityManager设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("xdclass", "123");
        subject.login(usernamePasswordToken);

        System.out.println("认证结果" + subject.isAuthenticated());
        System.out.println("是否有对应的root角色：" + subject.hasRole("root"));
        System.out.println("getPrincipal=" + subject.getPrincipal());
        subject.checkPermission("video:delete");
        System.out.println("是否有权限" + subject.isPermitted("video:delete"));
        subject.logout();
        System.out.println("logout后认证结果：" + subject.isAuthenticated());
    }
}
