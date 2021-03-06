package com.imooc.security.filter;

import com.imooc.security.user.User;
import com.imooc.security.user.UserRepository;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 2. 认证
 * ClassName: BasicAuthenticationFilter
 * Description: TODO(描述)
 * Date: 2020/7/2 19:55
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Order(2)
@Component
public class BasicAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository ;
    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = req.getHeader("Authorization");
        if (StringUtils.isNoneBlank(authHeader)){
            String token64 = StringUtils.substringAfter(authHeader, "Basic ") ;
            String token = new String(Base64Utils.decodeFromString(token64)) ;
            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token, ":");
            String username = items[0] ;
            String password = items[1] ;
            User user = userRepository.findByUsername(username) ;
            if (user != null && SCryptUtil.check(password, user.getPassword())){
                req.getSession().setAttribute("user", user.buildInfo());
                req.getSession().setAttribute("temp","yes");
            }
        }

        try {
            chain.doFilter(req,resp);
        }finally {
            HttpSession session = req.getSession();
            if (session.getAttribute("temp") != null){
                session.invalidate();
            }
        }

    }
}
