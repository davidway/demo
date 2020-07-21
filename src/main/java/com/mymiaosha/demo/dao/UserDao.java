package com.mymiaosha.demo.dao;

import com.mymiaosha.demo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user(id,name) values (#{id},#{name})")
    public void insert(User user);


}