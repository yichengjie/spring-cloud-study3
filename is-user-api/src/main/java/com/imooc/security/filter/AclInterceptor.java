package com.imooc.security.filter;

import com.imooc.security.user.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 4. 授权
 * ClassName: AclInterceptor
 * Description: TODO(描述)
 * Date: 2020/7/2 22:35
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    private String [] permitUrls = {"/users/login"} ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true ;
        if (!ArrayUtils.contains(permitUrls, request.getRequestURI())){
            UserInfo user = (UserInfo) request.getSession().getAttribute("user");
            //1. 需不需要身份认证
            //2. 需要身份认证，身份认证过了，有没有权限
            response.setContentType("text/plain");
            if (user ==null){
                response.getWriter().write("need authentication");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                result = false ;
            }else {
                String method = request.getMethod();
                if (!user.hasPermission(method)){
                    response.getWriter().write("forbidden");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false ;
                }
            }
        }
        return result ;
    }
}
