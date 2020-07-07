package com.imooc.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: GatewayAuditLogFilter
 * Description: TODO(描述)
 * Date: 2020/7/7 21:57
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
// 不要把这个filter申明成Component，因为spring会默认将这个filter加载过滤器链中，
// 在GateWaySecurityConfig.configure(HttpSecurity http)中手动又添加了一次，则会存在两次
@Slf4j
public class GatewayAuditLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        log.info("====>1. add log for {}", username);
        filterChain.doFilter(request,response);
        if (StringUtils.isBlank((String)request.getAttribute("logUpdated"))){
            log.info("====>3. update log to success");
        }
    }
}
