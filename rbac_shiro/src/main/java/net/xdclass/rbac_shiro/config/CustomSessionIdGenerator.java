package net.xdclass.rbac_shiro.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 自定义sessionid
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        return "xdclass" + UUID.randomUUID().toString();
    }
}
