package com.zdpang.template.controller;

import com.zdpang.template.bean.MessageVo;
import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.service.MessagePayloadService;
import com.zdpang.template.service.MessageQueueService;
import com.zdpang.template.service.MessageService;
import com.zdpang.template.service.MessageUserService;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  ResponseBean sendMessage(@RequestBody MessageVo messageVo) throws Exception {


    return new ResponseBean().success(messageService.saveMessage(Collections.singletonList(messageVo)));
  }

  @PostMapping(value="/sendTest")
  ResponseBean sendTest(@RequestBody MessageVo messageVo) throws Exception {

    return new ResponseBean().success(messageService.saveTest(Collections.singletonList(messageVo)));
  }

  @GetMapping(value="/get")
  ResponseBean getMessage(Long userId, String brand, Integer targetUserType, Integer pageSize, Integer pageNum) throws Exception {


    return new ResponseBean().success(messageService.getMessage(userId, brand, targetUserType, pageSize, pageNum));
  }

  @PostMapping(value="/client/single/update/status")
  ResponseBean clientUpdateStatus(Long seq, Integer status, String brand, Long userId, Integer targetUserType) throws Exception {


    return new ResponseBean().success(messageService.clientUpdateStatus(seq, status, brand, userId, targetUserType));
  }

  @PostMapping(value="/admin/broadcast/update/status")
  ResponseBean adminUpdateStatus(Long seq, Integer status) throws Exception {


    return new ResponseBean().success(messageService.adminUpdateStatus(seq, status));
  }
}
