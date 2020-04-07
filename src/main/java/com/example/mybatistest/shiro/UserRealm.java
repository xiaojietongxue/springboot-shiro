package com.example.mybatistest.shiro;

import com.example.mybatistest.bean.User;
import com.example.mybatistest.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;
    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("执行授权逻辑");
        //授权逻辑
       SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
 //      info.addStringPermission("1");
        Subject subject = SecurityUtils.getSubject();
       User user = (User) subject.getPrincipal();
        System.out.println(user.getUserName());
        System.out.println("*********************");
       User douser = userMapper.getUserRoleByName(user.getUserName());
       info.addStringPermission(String.valueOf(douser.getUserRole()));
        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
        //数据库里面的密码
        User user = userMapper.getUserPasswordByName(token.getUsername());

        if (user == null){
            //用户名不存在

            return null;//shiro底层会抛出UnKnowAccountException
        }
        return new SimpleAuthenticationInfo(user,user.getUserPassword(),"");
    }

}