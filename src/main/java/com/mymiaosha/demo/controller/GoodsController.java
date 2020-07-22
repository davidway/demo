package com.mymiaosha.demo.controller;

import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.service.GoodsService;
import com.mymiaosha.demo.service.MiaoshaUserService;
import com.mymiaosha.demo.service.UserService;
import com.mymiaosha.demo.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    @Autowired
    GoodsService goodsService;

    @RequestMapping("to_list")
    public String toList(Model model,MiaoshaUser miaoshaUser){
        List<GoodsVo> goodsList = goodsService.listGoodsVo();

      model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }
    @RequestMapping("to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId")long goodsId){
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus=0;
        int remainSeconds=0;
        if ( now <startAt){
            miaoshaStatus=0;
            remainSeconds = (int)((startAt-now)/1000);
        }else if ( now >endAt){
            miaoshaStatus=2;
            remainSeconds = -1;
        }else{
            miaoshaStatus=1;
            remainSeconds =0;

        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goods_detail";
    }



}
