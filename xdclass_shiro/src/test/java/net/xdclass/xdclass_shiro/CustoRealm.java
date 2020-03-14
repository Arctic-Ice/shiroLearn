package net.xdclass.xdclass_shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustoRealm extends AuthorizingRealm {
    private final Map<String, String> userInfoMap = new HashMap<>();

    {
        userInfoMap.put("jack", "123");
        userInfoMap.put("xdclass", "456");
    }

    // role -> permission
    private final Map<String, Set<String>> permissionMap = new HashMap<>();

    {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("video:find");
        set1.add("video:buy");

        set2.add("video:add");
        set2.add("video:delete");

        permissionMap.put("jack", set1);
        permissionMap.put("xdclass", set2);
    }

    // user -> role
    private final Map<String, Set<String>> roleMap = new HashMap<>();

    {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("role1");
        set1.add("role2");

        set2.add("root");

        roleMap.put("jack", set1);
        roleMap.put("xdclass", set2);
    }

    // 进行权限校验的时候会调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限 doGetAuthorizationInfo");

        String name = (String) principals.getPrimaryPrincipal();

        Set<String> permissions = getPermissionsByNameFromDB(name);

        Set<String> roles = getRolesByNameFromDB(name);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    // 当用户登录的时候会调用
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证 doGetAuthenticationInfo");

        // 从token中获取身份信息，token代表用户输入的信息
        String name = (String) token.getPrincipal();
        String pwd = getPwdByUserNameFromDB(name);

        if (pwd == null || "".equals(pwd)) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());
        return simpleAuthenticationInfo;
    }

    // 模拟从数据库获取用户角色集合
    private Set<String> getRolesByNameFromDB(String name) {
        return roleMap.get(name);
    }

    // 模拟从数据库获取权限集合
    private Set<String> getPermissionsByNameFromDB(String name) {
        return permissionMap.get(name);
    }

    private String getPwdByUserNameFromDB(String name) {
        return userInfoMap.get(name);
    }
}
