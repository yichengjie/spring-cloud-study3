package com.imooc.security.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * ClassName: OAuth2AuthServerConfig
 * Description: TODO(描述)
 * Date: 2020/7/4 20:25
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private AuthenticationManager authenticationManager ;


    //3. 谁能找我验token的合法性
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // isAuthenticated() 是权限表达式，表示如果你来验证token的请求，一定要是经过身份认证的
        // 这里的经过身份认证就是说你要带着用户名和密码，例如：orderApp-123456, 或则orderService-123456
        security.checkTokenAccess("isAuthenticated()") ;
    }

    //2. 添加配置让认证服务器知道哪些用户可以访问认证服务器
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.authenticationManager(authenticationManager) ;
    }

    // 1. 配置客户端应用的详细信息，让认证服务器知道有哪些客户端应用会来请求令牌
    // 注册orderApp和orderService应用，现在这两个都是授权服务器可以认的应用了
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 注册一个叫orderApp的应用密码是123456
                .withClient("orderApp")
                // 应用的密码
                .secret(passwordEncoder.encode("123456"))
                //orderApp可以获取到的权限的集合
                .scopes("read", "write")
                // token过期时间
                .accessTokenValiditySeconds(3600)
                // 签发token可以访问哪些资源服务器
                .resourceIds("order-server")
                // 授权类型，总共有四种模式
                .authorizedGrantTypes("password")
                .and()
                // 注册订单服务器
                .withClient("orderService")
                .secret(passwordEncoder.encode("123456"))
                .scopes("read")
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-server")
                .authorizedGrantTypes("password")


        ;
    }
}
