package com.zdpang.template.service.impl;

import com.zdpang.template.model.MessageUser;
import com.zdpang.template.dao.MessageUserMapper;
import com.zdpang.template.service.MessageUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zdpang
 * @since 2019-08-30
 */
@Service
public class MessageUserServiceImpl extends ServiceImpl<MessageUserMapper, MessageUser> implements MessageUserService {

}
