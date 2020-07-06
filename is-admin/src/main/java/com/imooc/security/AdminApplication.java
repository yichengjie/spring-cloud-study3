package com.imooc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ClassName: AdminPplication
 * Description: TODO(描述)
 * Date: 2020/7/5 16:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@EnableZuulProxy
@RestController
@SpringBootApplication
public class AdminApplication {
    private RestTemplate restTemplate = new RestTemplate() ;

    public static void main(String[] args) {
      SpringApplication.run(AdminApplication.class, args);
    }

    @GetMapping("/oauth/callback")
    public void login(@RequestParam String code, String state, HttpSession session, HttpServletResponse response) throws IOException {
        log.info("state is {}" , state);

        String oauthServiceUrl = "http://gateway.imooc.com:9070/token/oauth/token" ;
        // 配置头里面带的信息
        HttpHeaders headers = new HttpHeaders() ;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin", "123456");
        // 带上令牌
        // 注意这里只能用MultiValueMap，如果使用HashMap会报错
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>() ;
        params.add("code", code);
        params.add("grant_type", "authorization_code");//authorization_code
        // 认证服务器会比较之前传的redirect_uri与这次的redirect_uri是否相同，如果不同则会报错
        params.add("redirect_uri", "http://admin.imooc.com:8080/oauth/callback");
        // 组装请求实体
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params, headers) ;
        // 发送http请求
        ResponseEntity<TokenInfo> token =
          restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class) ;
        //session.setAttribute("token", token.getBody().init());
        addTokenCookies(token.getBody(), response) ;
        response.sendRedirect("/");
    }


    public static void addTokenCookies(TokenInfo info, HttpServletResponse response){
        Cookie accessTokenCookie = new Cookie("imooc_access_token", info.getAccess_token()) ;
        accessTokenCookie.setMaxAge(info.getExpires_in().intValue());
        accessTokenCookie.setDomain("imooc.com");
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("imooc_refresh_token", info.getRefresh_token()) ;
        refreshTokenCookie.setMaxAge(259200);
        refreshTokenCookie.setDomain("imooc.com");
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
    }

    @GetMapping("/me")
    public TokenInfo me(HttpSession session){
        TokenInfo info = (TokenInfo) session.getAttribute("token");
        return info ;
    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }
}
