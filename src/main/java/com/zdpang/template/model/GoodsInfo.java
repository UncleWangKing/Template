package com.zdpang.template.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author zdpang
 * @since 2019-01-24
 */
@TableName("goods_info")
@Data
@Accessors(chain = true)
public class GoodsInfo extends Model<GoodsInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片地址
     */
    private String imgUrl;

    /**
     * 商品标价
     */
    private String goodsPrice;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public GoodsInfo(String goodsId, String goodsName, String imgUrl, String goodsPrice){
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.imgUrl = imgUrl;
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
        "id=" + id +
        ", goodsId=" + goodsId +
        ", goodsName=" + goodsName +
        ", imgUrl=" + imgUrl +
        ", goodsPrice=" + goodsPrice +
        "}";
    }
}
