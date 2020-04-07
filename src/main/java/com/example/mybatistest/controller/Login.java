package com.example.mybatistest.controller;

import com.example.mybatistest.bean.User;
import com.example.mybatistest.mapper.UserMapper;
import com.example.mybatistest.utils.TokenSubjectUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller


public class Login {
    @ResponseBody
    @RequestMapping("/fuck")
    public String fuck(){
        return "想啥呢？不怕坐牢啊！";
    }
    @ResponseBody
    @RequestMapping("/unauthor")
    public String unauthor(){
        return "没有权限哦！";
    }
    @RequestMapping("/index")
    public  String index(){
        return "index";
    }
    @RequestMapping("/success")
    public  String success(){
        return "success";
    }


    @RequestMapping("/toindex")
    public String login(String userName, String userPassword, Model model){
        System.out.println("name"+userName+"------>"+"password"+userPassword);
        /**
         * 使用Shiro编写认证操作
         */
        try {
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName,userPassword);

        //3.执行登录方法
            subject.login(token);
            //认证成功后会将user信息放到subject内，生成token并返回
            //登录成功
            //跳转到test.html
            return "redirect:success";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "index";
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            model.addAttribute("msg", "密码错误");
            return "index";
        }
    }
}
