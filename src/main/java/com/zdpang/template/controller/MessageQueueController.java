package com.zdpang.template.controller;


import com.zdpang.template.common.CrudController;
import com.zdpang.template.model.MessageQueue;
import com.zdpang.template.service.MessageQueueService;
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
@RequestMapping("/messageQueue")
public class MessageQueueController implements CrudController<MessageQueue, MessageQueueService> {
  @Autowired
  private MessageQueueService messageQueueService;
  @Override
  public MessageQueueService getService() {
    return messageQueueService;
  }
}

