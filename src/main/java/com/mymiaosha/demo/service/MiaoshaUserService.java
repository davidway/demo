package com.mymiaosha.demo.service;

import com.mymiaosha.demo.dao.MoocUserDao;
import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.exception.GlobalException;
import com.mymiaosha.demo.redis.MiaoshaUserKey;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.result.CodeMsg;
import com.mymiaosha.demo.util.MD5Util;
import com.mymiaosha.demo.util.UUIDUtil;
import com.mymiaosha.demo.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN="token";
    @Autowired
    MoocUserDao moocUserDao;
    @Autowired
    RedisService redisService;
    @Autowired
    HttpServletResponse response;

    public MiaoshaUser getByMobile(String mobile) {
        MiaoshaUser user = moocUserDao.getByMobile(mobile);
        return user;
    }

    public boolean login(LoginVo loginVo) {
        String mobile =loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser miaoshaUser = this.getByMobile(mobile);
        if (miaoshaUser ==null){
            throw  new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbPassword = MD5Util.formPassToDBPass(password, miaoshaUser.getSalt());
        if ( !miaoshaUser.getPassword().equals(dbPassword)){
            throw  new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        //token来识别用户信息
        redisService.set(MiaoshaUserKey.token,token, miaoshaUser);

        //生城cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    public MiaoshaUser getByToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        return redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
    }
}
