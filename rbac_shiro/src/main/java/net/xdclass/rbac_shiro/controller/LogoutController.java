package net.xdclass.rbac_shiro.controller;

import net.xdclass.rbac_shiro.domain.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * logout,shiro自带过滤器
 */
@RestController
public class LogoutController {
//    @RequestMapping("/update")
//    public JsonData updateVideo() {
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.getPrincipals() != null) {
//
//        }
//        SecurityUtils.getSubject().logout();
//        return JsonData.buildSuccess("退出成功");
//    }
}
