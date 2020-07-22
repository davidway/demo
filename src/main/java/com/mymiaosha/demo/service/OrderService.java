package com.mymiaosha.demo.service;

import com.mymiaosha.demo.dao.GoodsDao;
import com.mymiaosha.demo.dao.OrderDao;
import com.mymiaosha.demo.domain.MiaoshaOrder;
import com.mymiaosha.demo.domain.MiaoshaUser;
import com.mymiaosha.demo.domain.OrderInfo;
import com.mymiaosha.demo.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;


    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long id, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId( id,  goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser miaoshaUser, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0); //未支付
        orderInfo.setUserId(miaoshaUser.getId());
        long orderId = orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setUserId(miaoshaUser.getId());

        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
