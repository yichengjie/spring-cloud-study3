package com.imooc.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: PermissionService
 * Description: TODO(描述)
 * Date: 2020/7/7 21:37
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface PermissionService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication) ;
}
