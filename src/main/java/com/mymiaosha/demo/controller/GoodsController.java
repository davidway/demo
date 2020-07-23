package com.mymiaosha.demo.controller;

import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.redis.GoodsKey;
import com.mymiaosha.demo.redis.RedisService;
import com.mymiaosha.demo.service.GoodsService;
import com.mymiaosha.demo.service.MiaoshaUserService;
import com.mymiaosha.demo.service.UserService;
import com.mymiaosha.demo.vo.GoodsVo;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @RequestMapping(value = "to_list",produces = "text/html")
    @ResponseBody
    public String toList(Model model,MiaoshaUser miaoshaUser){
        //先取缓存
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if ( StringUtils.isNotBlank(html)){
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if ( StringUtils.isNotBlank(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }
    @RequestMapping(value = "to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail(Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId")long goodsId){
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        String html = redisService.get(GoodsKey.getGoodsDetails,""+goodsId,String.class);
        if ( StringUtils.isNotBlank(html)){
            return html;
        }
        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if ( StringUtils.isNotBlank(html)){
            redisService.set(GoodsKey.getGoodsDetails,""+goodsId,html);
        }

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

        return html;
    }

   /* @RequestMapping("to_list")
    public String toList(Model model,MiaoshaUser miaoshaUser){
        List<GoodsVo> goodsList = goodsService.listGoodsVo();

        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }*/
   /* @RequestMapping("to_detail/{goodsId}")
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
*/


}
