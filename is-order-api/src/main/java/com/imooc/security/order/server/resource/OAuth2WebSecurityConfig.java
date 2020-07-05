package com.imooc.security.order.server.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.*;

/**
 * ClassName: OAuth2WebSecurityConfig
 * Description: TODO(描述)
 * Date: 2020/7/4 21:52
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Configuration
@EnableWebSecurity
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("userDetailServiceImpl")
    private UserDetailsService userDetailsService ;


    /**
     * 校验token合法性
     * @return
     */
    @Bean
    public ResourceServerTokenServices tokenServices(){
        RemoteTokenServices tokenServices = new RemoteTokenServices() ;
        tokenServices.setClientId("orderService");
        tokenServices.setClientSecret("123456");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
        // 修改配置使其使用UserDetailServiceImpl来读取相关的用户信息
        // AccessTokenConverter的作用就是将令牌转换成用户信息
        tokenServices.setAccessTokenConverter(getAccessTokenConverter());
        return tokenServices ;
    }

    /**
     * 作用是将从服务器获取的token信息转换成用户信息
     * @return
     */
    private AccessTokenConverter getAccessTokenConverter() {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter() ;
        DefaultUserAuthenticationConverter authenticationConverter = new DefaultUserAuthenticationConverter() ;
        authenticationConverter.setUserDetailsService(userDetailsService);
        accessTokenConverter.setUserTokenConverter(authenticationConverter);
        return accessTokenConverter ;
    }


    // 做了这个配置表示，当资源服务器拦截到请求里面的token以后，要验token的时候，就会调tokenServices来验
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager() ;
        authenticationManager.setTokenServices(tokenServices());
        return authenticationManager;
    }
}
