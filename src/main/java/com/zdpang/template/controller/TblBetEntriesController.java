package com.zdpang.template.controller;


import com.zdpang.template.common.CrudController;
import com.zdpang.template.model.TblBetEntries;
import com.zdpang.template.service.TblBetEntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单玩法表 前端控制器
 * </p>
 *
 * @author zdpang
 * @since 2019-06-25
 */
@RestController
@RequestMapping("/entry")
public class TblBetEntriesController implements CrudController<TblBetEntries, TblBetEntriesService> {
  @Autowired
  private TblBetEntriesService tblBetEntriesService;

  @Override
  public TblBetEntriesService getService() {
    return tblBetEntriesService;
  }
}

