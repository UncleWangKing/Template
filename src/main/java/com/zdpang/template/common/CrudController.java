package com.zdpang.template.common;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdpang.template.bean.FieldValuePair;
import com.zdpang.template.bean.ResponseBean;
import com.zdpang.template.model.MessageQueue;
import java.util.Date;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface CrudController<T extends Model, K extends IService> {

    @GetMapping(value="/{id}")
    default ResponseBean get(@PathVariable("id") String id) throws Exception {
        return new ResponseBean().success(getService().getById(id));
    }

    @GetMapping(value="/{pageNum}/{pageSize}")
    default ResponseBean list(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = getService().list(null);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new ResponseBean().success(pageInfo);
    }

    @PostMapping(value="/field/{pageNum}/{pageSize}")
    default ResponseBean field(@RequestBody FieldValuePair pair, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put(pair.getField(), pair.getValue());
        List<T> list = (List) getService().listByMap(map);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new ResponseBean().success(pageInfo);
    }

    @PostMapping
    default ResponseBean insert(@Valid @RequestBody T obj, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseBean().failure(bindingResult.getFieldError().getDefaultMessage());
        }

        if (obj instanceof MessageQueue){
            ((MessageQueue) obj).setExpireTime(new Date());
        }

        return getService().save(obj) ?  new ResponseBean().success(obj) : new ResponseBean().failure("保存失败");
    }

    @PutMapping
    default ResponseBean update(@Valid @RequestBody T obj, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseBean().failure(bindingResult.getFieldError().getDefaultMessage());
        }

        return getService().updateById(obj) ?  new ResponseBean().success() : new ResponseBean().failure("更新失败");
    }

    /**
     * 统一使用removeByIds 而不用removeById 为了统一重写删除逻辑
     */
    @DeleteMapping
    default ResponseBean deleteBatchIds(@RequestBody List<String> ids){
        return getService().removeByIds(ids) ?  new ResponseBean().success() : new ResponseBean().failure("不存在");
    }
    /**
     * 统一使用removeByIds 而不用removeById 为了统一重写删除逻辑
     */
    @DeleteMapping(value="/{id}")
    default ResponseBean deleteById(@PathVariable("id") String id){
        return getService().removeByIds(Arrays.asList(id)) ?  new ResponseBean().success() : new ResponseBean().failure("不存在");
    }

    K getService();
}
