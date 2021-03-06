package com.mymiaosha.demo.dao;

import com.mymiaosha.demo.domain.Goods;
import com.mymiaosha.demo.domain.MiaoshaGoods;
import com.mymiaosha.demo.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where mg.id = #{goodsId}" )
    GoodsVo getGoodBoByGoodsId(@Param("goodsId")long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id= #{goodsId}")
    void reduceStock(MiaoshaGoods g);
}
