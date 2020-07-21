package com.mymiaosha.demo.dao;

import com.mymiaosha.demo.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MoocUserDao {
    @Select("SELECT * FROM miaosha_user WHERE id = #{mobile}")
    public MiaoshaUser getByMobile(String mobile);
}
