package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * ClassName: OrderApiApplication
 * Description: TODO(描述)
 * Date: 2020/7/4 18:48
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
//@PreAuthorize("#oauth2.hasScope('fly')")
// 使这个生效必须加@EnableGlobalMethodSecurity(prePostEnabled = true)注解才行
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@SpringBootApplication
public class OrderApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderApiApplication.class, args) ;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(
            OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oAuth2ClientContext){
        return new OAuth2RestTemplate(resource, oAuth2ClientContext) ;
    }
}
