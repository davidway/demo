package com.mymiaosha.demo.controller;

import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.result.Result;
import com.mymiaosha.demo.service.GoodsService;
import com.mymiaosha.demo.service.MiaoshaUserService;
import com.mymiaosha.demo.service.UserService;
import com.mymiaosha.demo.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    private  static Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    RedisService redisService;


    @RequestMapping("info")
    @ResponseBody
    public Result<MiaoshaUser> toList(Model model,MiaoshaUser miaoshaUser){


        return Result.success(miaoshaUser);
    }



}
