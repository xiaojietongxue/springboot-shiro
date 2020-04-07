package com.example.mybatistest.mapper;

import com.example.mybatistest.bean.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM smbms_user WHERE id = #{id}")
    public User getUserById(Integer id);

    @Delete(("delete from smbms_user where id = #{id}"))
    public int deleteUserById(Integer id);

   @Update("UPDATE smbms_user SET userName = #{userName} WHERE id =#{id}")
    public  int updateUser(User user);

   @Select("SELECT userName FROM smbms_user WHERE id = #{id}")
   public String getUserNameById(Integer id);

    @Select("SELECT userPassword,userName  FROM smbms_user WHERE userName =#{userName} ")
   public User getUserPasswordByName(String name);

    @Select("SELECT Userrole FROM smbms_user WHERE userName =#{userName}")
    public  User getUserRoleByName(String name);
}
