package com.mymiaosha.demo.controller;

import com.mymiaosha.demo.domain.MiaoshaOrder;
import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.domain.OrderInfo;
import com.mymiaosha.demo.result.CodeMsg;
import com.mymiaosha.demo.service.GoodsService;
import com.mymiaosha.demo.service.MiaoshaUserService;
import com.mymiaosha.demo.service.OrderService;
import com.mymiaosha.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class MiaoshaController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaUserService miaoshaUserService;
    @RequestMapping("do_miaosha")
    public String toList(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId")long goodsId){
        if ( miaoshaUser==null){
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId( goodsId);
        int stock = goods.getStockCount();
        if ( stock<=0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER);

            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(),goodsId);
        if ( order!=null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA);

            return "miaosha_fail";
        }

        //减库存， 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaUserService.miaosha(miaoshaUser,goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }
}
