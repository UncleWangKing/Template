package com.zdpang.template.service;

import com.zdpang.template.model.TblOrderBrief;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zdpang
 * @since 2019-06-25
 */
public interface TblOrderBriefService extends IService<TblOrderBrief> {
  boolean batchInsert();
}
