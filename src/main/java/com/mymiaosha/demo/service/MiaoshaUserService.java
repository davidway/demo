package com.mymiaosha.demo.service;

import com.mymiaosha.demo.dao.MiaoshaUserDao;
import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.domain.OrderInfo;
import com.mymiaosha.demo.exception.GlobalException;
import com.mymiaosha.demo.redis.MiaoshaUserKey;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.redis.UserKey;
import com.mymiaosha.demo.result.CodeMsg;
import com.mymiaosha.demo.util.MD5Util;
import com.mymiaosha.demo.util.UUIDUtil;
import com.mymiaosha.demo.vo.GoodsVo;
import com.mymiaosha.demo.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN="token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;
    @Autowired
    RedisService redisService;
    @Autowired
    HttpServletResponse response;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    public MiaoshaUser getByMobile(String mobile) {
        //取缓存
        MiaoshaUser user = redisService.get(UserKey.getById,""+mobile,MiaoshaUser.class);
        if ( user!=null){
            return user;
        }else{
            //如果缓存不存在，则取数据库
             user = miaoshaUserDao.getByMobile(mobile);
            if ( user!=null){
                redisService.set(UserKey.getById,""+mobile,MiaoshaUser.class);
            }
        }
        return user;
    }

    public boolean updatePassword(String token,long id,String passwordNew) {
        //取缓存
        MiaoshaUser user = getByMobile(""+id);
        if ( user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(passwordNew,user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(UserKey.getById,""+id);
        user.setPassword(passwordNew);
        redisService.set(MiaoshaUserKey.token,""+id,user);

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
        addUserCookie(miaoshaUser,token);
        return true;
    }
    public void addUserCookie(MiaoshaUser miaoshaUser,String token){
        //生城cookie

        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        //token来识别用户信息
        redisService.set(MiaoshaUserKey.token,token, miaoshaUser);

        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }

        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if ( miaoshaUser!=null){
            addUserCookie(miaoshaUser,token);
        }
        return miaoshaUser;
    }

    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goods) {

        goodsService.reduceStock(goods);

        OrderInfo orderInfo = orderService.createOrder(miaoshaUser,goods);
        return orderInfo;
    }
}
