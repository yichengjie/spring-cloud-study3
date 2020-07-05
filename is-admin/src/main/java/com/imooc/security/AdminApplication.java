package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

/**
 * ClassName: AdminPplication
 * Description: TODO(描述)
 * Date: 2020/7/5 16:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@EnableZuulProxy
@RestController
@SpringBootApplication
public class AdminApplication {
    private RestTemplate restTemplate = new RestTemplate() ;

    public static void main(String[] args) {
      SpringApplication.run(AdminApplication.class, args);
    }

    @PostMapping("/login")
    public void login(@RequestBody Credentials credentials, HttpSession session) {
        String oauthServiceUrl = "http://localhost:9070/token/oauth/token" ;
        // 配置头里面带的信息
        HttpHeaders headers = new HttpHeaders() ;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin", "123456");
        // 带上令牌
        // 注意这里只能用MultiValueMap，如果使用HashMap会报错
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>() ;
        params.add("username", credentials.getUsername());
        params.add("password", credentials.getPassword());
        params.add("grant_type", "password");
        params.add("scope", "read write");
        // 组装请求实体
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params, headers) ;
        // 发送http请求
        ResponseEntity<TokenInfo> response =
          restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class) ;
        session.setAttribute("token", response.getBody());
    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }
}
