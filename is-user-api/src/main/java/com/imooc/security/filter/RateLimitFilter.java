package com.imooc.security.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 限流
 * ClassName: RateLimitFilter
 * Description: TODO(描述)
 * Date: 2020/7/1 22:46
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Order(1)
@Component
public class RateLimitFilter extends OncePerRequestFilter {
    // 每秒一个请求的限流器
    private RateLimiter rateLimiter = RateLimiter.create(1) ;

    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        if (rateLimiter.tryAcquire()){
            chain.doFilter(req,resp);
        }else {
            resp.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            resp.getWriter().write("to many request!!！");
            resp.getWriter().flush();
        }
    }
}
