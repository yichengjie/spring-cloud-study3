package com.imooc.security.server.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * ClassName: OAuth2AuthServerConfig
 * Description: TODO(描述)
 * Date: 2020/7/4 20:25
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private DataSource dataSource ;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder() ;
    }

    @Bean
    public TokenStore tokenStore (){
        return new JdbcTokenStore(dataSource) ;
    }

    // 1. 配置客户端应用的详细信息，让认证服务器知道有哪些客户端应用会来请求令牌
    // 注册orderApp和orderService应用，现在这两个都是授权服务器可以认的应用了
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 直接去数据源里面查询
        clients.jdbc(dataSource);
    }

    //2. 添加配置让认证服务器知道哪些用户可以访问认证服务器
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .tokenStore(tokenStore()) // 配置token存放位置，默认存放在内存中
                .authenticationManager(authenticationManager) ;
    }

    //3. 谁能找我验token的合法性
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // isAuthenticated() 是权限表达式，表示如果你来验证token的请求，一定要是经过身份认证的
        // 这里的经过身份认证就是说你要带着用户名和密码，例如：orderApp-123456, 或则orderService-123456
        security.checkTokenAccess("isAuthenticated()") ;
    }
}
