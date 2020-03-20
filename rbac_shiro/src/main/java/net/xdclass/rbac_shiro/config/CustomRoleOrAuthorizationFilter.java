package net.xdclass.rbac_shiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * 自定义Filter
 */
public class CustomRoleOrAuthorizationFilter extends AuthorizationFilter {
    public CustomRoleOrAuthorizationFilter() {
    }

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for (String role: roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
