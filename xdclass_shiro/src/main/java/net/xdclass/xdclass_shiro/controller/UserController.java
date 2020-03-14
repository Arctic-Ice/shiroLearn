package net.xdclass.xdclass_shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apiV1_0/admin/user")
public class UserController {
    //    @RequiresRoles(value = {"admin", "editor"}, logical = Logical.AND)
    @RequiresPermissions(value = {"user:add", "user:del"}, logical = Logical.OR)
    @RequestMapping("list_user")
    public Object listUser() {
        return null;
    }
}
