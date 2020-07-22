package com.mymiaosha.demo.service;

import com.mymiaosha.demo.dao.GoodsDao;
import com.mymiaosha.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }
}
