package com.imooc.security.order.server.resource;

import org.springframework.context.annotation.Configuration;
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
}
