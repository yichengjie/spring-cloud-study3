package com.imooc.security.order.server.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * ClassName: OAuth2ResourceServerConfig
 * Description: TODO(描述)
 * Date: 2020/7/4 21:47
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // 配置资源服务器的id
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置我就是order-server
        resources.resourceId("order-server") ;
    }

    // 使用scope来控制acl访问权限
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 表示只有write的scope的时候才能掉post请求
            .antMatchers(HttpMethod.POST).access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.GET).access("#oauth2.hasScope('read')");
    }
}
