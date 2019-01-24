package com.zdpang.template.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdpang.template.model.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author zdpang
 * @since 2019-01-24
 */
@Mapper
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

}
