package com.zdpang.template.controller;


import com.zdpang.template.common.CrudController;
import com.zdpang.template.model.MessagePayload;
import com.zdpang.template.service.MessagePayloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/messagePayload")
public class MessagePayloadController implements CrudController<MessagePayload, MessagePayloadService> {
  @Autowired
  private MessagePayloadService messagePayloadService;

  @Override
  public MessagePayloadService getService() {
    return messagePayloadService;
  }
}

