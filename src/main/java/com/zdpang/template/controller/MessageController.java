package com.zdpang.template.controller;

import com.zdpang.template.bean.MessageVo;
import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.service.MessagePayloadService;
import com.zdpang.template.service.MessageQueueService;
import com.zdpang.template.service.MessageService;
import com.zdpang.template.service.MessageUserService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
  @Autowired
  private MessageQueueService messageQueueService;
  @Autowired
  private MessageUserService messageUserService;
  @Autowired
  private MessagePayloadService messagePayloadService;
  @Autowired
  private MessageService messageService;

  @PostMapping(value="/send")
  ResponseBean batchInsert(MessageVo messageVo) throws Exception {


    return new ResponseBean().success(messageService.saveMessage(Arrays.asList(messageVo)));
  }
}
