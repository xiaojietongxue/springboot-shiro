package com.example.mybatistest.controller;

import com.example.mybatistest.bean.User;
import com.example.mybatistest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    StringRedisTemplate stringredisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/user/{id}")
    public User getUserByid(@PathVariable("id")Integer id){
        String key = "user"+id;

        if (redisTemplate.hasKey(key)){
            Map entries = redisTemplate.opsForHash().entries(key);
//            Map<String,Object> redisMap
            User user = new User();
            user.setId((Integer) entries.get("id"));
            user.setUserName((String) entries.get("name"));
            user.setUserPassword((String) entries.get("userPassword"));
            return  user;
        }
        else{
            User user =userMapper.getUserById(id);
            Map<String,Object> map = new HashMap<>();
            map.put("id",user.getId() );
            map.put("name",user.getUserName());
            map.put("userPassword",user.getUserPassword());

            //key<, map<,>>
            redisTemplate.opsForHash().putAll(key,map);
//            valueOperations.set(String.valueOf(id),user);
        return  user;
        }
    }
    @GetMapping("/delete/{id}")
    public int deldteUserById(@PathVariable("id")Integer id){
        return userMapper.deleteUserById(id);
    }
    @GetMapping("/update")
    public  int update(User user){
        return  userMapper.updateUser(user);
    }

    @GetMapping("/Role")
    public User getUserRoleByName(String name){
        return  userMapper.getUserRoleByName(name);
    }

    @GetMapping("/redis/{id}")
    public String getUserNameById(@PathVariable("id")Integer id){
      String key = "id"+id;
        ValueOperations<String, String> stringStringValueOperations = stringredisTemplate.opsForValue();
        //判断redis里面是有有key值缓存
        boolean haskey = stringredisTemplate.hasKey(key);
        if (haskey){
            String user   = stringStringValueOperations.get(key);
            return user+" from redis" ;
        }else{
            String user = userMapper.getUserNameById(id);
            if (user!=null){
            stringStringValueOperations.set(key,user,30, TimeUnit.SECONDS);
            return  user+" from mysql";
            }
            else return  "没有该id的用户";
        }

    }



    @GetMapping("/hello")
    public String hello(){
        return  "hello";
    }

}
