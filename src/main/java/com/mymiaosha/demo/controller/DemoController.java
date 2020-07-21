package com.mymiaosha.demo.controller;

import com.mymiaosha.demo.domain.User;
import com.mymiaosha.demo.redis.KeyPrefix;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.redis.UserKey;
import com.mymiaosha.demo.result.Result;
import com.mymiaosha.demo.service.MiaoshaUserService;
import com.mymiaosha.demo.service.UserService;
import com.mymiaosha.demo.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("demo")
public class DemoController {
    private  static Logger log = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    MiaoshaUserService moocUserService;

    @RequestMapping("test")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo){
       log.info(loginVo.toString());
        moocUserService.login(loginVo);
        return Result.success(true);
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        Boolean result = userService.tx();
        return Result.success(result);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet(){
        KeyPrefix keyPrefix  = UserKey.getById;
        Integer a = 100;
        Integer b = Integer.valueOf(100);
        String v1 = redisService.get(keyPrefix,""+1,String.class);
       return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){

        KeyPrefix keyPrefix  = UserKey.getById;
        User user = new User(1,"1111");
        Boolean bl = redisService.set(keyPrefix,""+1,user);
        return Result.success(bl);
    }
}
