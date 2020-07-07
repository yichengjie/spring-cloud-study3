package com.imooc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认实现是返回一个401的状态码
 * ClassName: GatewayAuthenticationEntryPoint
 * Description: TODO(描述)
 * Date: 2020/7/7 22:19
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@Component
public class GatewayAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        request.setAttribute("logUpdated","yes");
        if (authException instanceof AccessTokenRequiredException){
            log.info("===> 2. update log 401");
        }else {
            log.info("===> 1. add log 401");
        }
        super.commence(request, response, authException);
    }
}
