package net.xdclass.rbac_shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("----执行shiroFilter----");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须配置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 需要登录的接口，如果访问某个接口，需要登录却没登录，则调用此接口（如果不是前后端分离，则跳转页面）
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

        // 登录成功，跳转url，如果前后端分离，则没这个调用
        shiroFilterFactoryBean.setSuccessUrl("/");

        // 登录但是没有权限
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");


        // 设置自定义filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roleOrFilter", new CustomRoleOrAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        // 拦截器路径，坑一，部分路径无法进行拦截，时有时无；因为同学使用的是hashMap，无序的，应该使用LinkedHashMap
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");

        // 匿名可以访问，也是游客模式
        filterChainDefinitionMap.put("/pub/**", "anon");

        // 登录用户可以访问
        filterChainDefinitionMap.put("/authc/**", "authc");

        // 管理员角色才可以访问
//        filterChainDefinitionMap.put("/admin/**", "roles[admin,root]");
        filterChainDefinitionMap.put("/admin/**", "roleOrFilter[admin,root]");

        // 有编辑权限才可以访问
        filterChainDefinitionMap.put("/video/update", "perms[video_update]");

        // 坑二：过滤链是顺序执行，从上而下，一般将/**放到最下面

        // authc:url定义必须通过认证才可以访问
        // anon：url可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //如果不是前后端分离，则不必设置下面的sessionManager
        securityManager.setSessionManager(sessionManager());

        // 使用自定义的cache
        securityManager.setCacheManager(cacheManager());

        // 设置realm（推荐放到最后，不如某些情况会不生效）
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        // 设置散列算法：这里使用的MD5算法
        credentialsMatcher.setHashAlgorithmName("md5");

        // 散列2次
        credentialsMatcher.setHashIterations(2);

        return credentialsMatcher;

    }

    // 自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        // 超时时间，默认30分钟，回话超时，单位ms
        customSessionManager.setGlobalSessionTimeout(200000);
        return customSessionManager;
    }

    /**
     * 配置redisManager
     * @return
     */
    public RedisManager getRedisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.10.127");
        redisManager.setPort(6379);
        return redisManager;
    }

    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());
        return redisCacheManager;
    }


}
