package com.mymiaosha.demo.service;

import com.mymiaosha.demo.dao.GoodsDao;
import com.mymiaosha.demo.domain.Goods;
import com.mymiaosha.demo.domain.MiaoshaGoods;
import com.mymiaosha.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        List<GoodsVo> list =  goodsDao.listGoodsVo();
        return list;
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
       return goodsDao.getGoodBoByGoodsId(goodsId);
    }

    public void reduceStock(Goods goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setId(goods.getId());
        g.setStockCount(goods.getGoodsStock()-1);
        goodsDao.reduceStock(g);
    }
}
