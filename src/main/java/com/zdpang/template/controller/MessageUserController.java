package com.zdpang.template.controller;


import com.zdpang.template.common.CrudController;
import com.zdpang.template.model.MessageUser;
import com.zdpang.template.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdpang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/messageUser")
public class MessageUserController implements CrudController<MessageUser, MessageUserService> {
  @Autowired
  private MessageUserService messageUserService;
  @Override
  public MessageUserService getService() {
    return messageUserService;
  }
}

