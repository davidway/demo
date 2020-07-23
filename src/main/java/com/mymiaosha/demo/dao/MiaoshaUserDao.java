package com.mymiaosha.demo.dao;

import com.mymiaosha.demo.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaUserDao {
    @Select("SELECT * FROM miaosha_user WHERE id = #{mobile}")
    public MiaoshaUser getByMobile(String mobile);

    @Update("update miaosha_user set password=#{password} where id = #{id}")
    void update(MiaoshaUser toBeUpdate);
}
