package net.xdclass.rbac_shiro.controller;

import net.xdclass.rbac_shiro.domain.JsonData;
import net.xdclass.rbac_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pub")
public class PublicController {

    @Autowired
    private UserService userService;

    @RequestMapping("need_login")
    public JsonData needLogin() {
        return JsonData.buildSuccess("温馨提示：请使用对应的账号登录", -2);
    }

    public JsonData notPermit() {
        return JsonData.buildSuccess("温馨提示：拒绝访问，没权限",-3);
    }

    @RequestMapping("index")
    public JsonData index() {
        List<String> videoList = new ArrayList<>();
        videoList.add("Mysql零基础入门到实战 数据库教程");
        videoList.add("Redis高并发高可用集群百万级秒杀实战");
        videoList.add("Zookeeper+Dubbo视频教程 微服务教程分布式教程");
        videoList.add("2019年新版本RocketMQ4.X教程消息队列教程");
        videoList.add("微服务SpringCloud+Docker入门到高级实战");

        return JsonData.buildSuccess(videoList);
    }

    @RequestMapping("find_user_info")
    public Object findUserInfo(@RequestParam("username")String username){

        return userService.findAllUserInfoByUsername(username);
    }

}