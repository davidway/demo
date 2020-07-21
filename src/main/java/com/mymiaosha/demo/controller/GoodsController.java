package com.mymiaosha.demo.controller;

import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.service.MiaoshaUserService;
import com.mymiaosha.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("goods")
public class GoodsController {
    private  static Logger log = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    MiaoshaUserService miaoshaUserService;


    @RequestMapping("to_list")
    public String toList(Model model, @CookieValue(value=MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                         @RequestParam(value=MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String paramToken){

      if (StringUtils.isEmpty(cookieToken)&& StringUtils.isEmpty(paramToken)){
          return "login";
      }
      String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
      MiaoshaUser user = miaoshaUserService.getByToken(token);
      model.addAttribute("user",user);
        return "goods_list";
    }



}
