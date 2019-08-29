package com.zdpang.template.controller;


import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.common.CrudController;
import com.zdpang.template.model.TblOrderBrief;
import com.zdpang.template.service.TblOrderBriefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdpang
 * @since 2019-06-25
 */
@RestController
@RequestMapping("/order")
public class TblOrderBriefController implements CrudController<TblOrderBrief, TblOrderBriefService> {
  @Autowired
  TblOrderBriefService tblOrderBriefService;

  @Override
  public TblOrderBriefService getService() {
    return tblOrderBriefService;
  }

  @GetMapping(value="/batch/insert")
  ResponseBean batchInsert() throws Exception {

    return new ResponseBean().success(tblOrderBriefService.batchInsert());
  }


}

