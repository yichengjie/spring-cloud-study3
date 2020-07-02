package com.imooc.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ErrorHandler
 * Description: TODO(描述)
 * Date: 2020/7/2 22:12
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handle(Exception ex){
        Map<String, Object> info = new HashMap<>() ;
        info.put("message", ex.getMessage()) ;
        info.put("time", new Date().getTime()) ;
        return info ;
    }
}
