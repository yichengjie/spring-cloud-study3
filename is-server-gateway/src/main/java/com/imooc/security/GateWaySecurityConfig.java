package com.imooc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * ClassName: GateWaySecurityConfig
 * Description: TODO(描述)
 * Date: 2020/7/7 19:52
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Configuration
@EnableResourceServer
public class GateWaySecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private GatewayWebSecurityExpressionHandler gatewayWebSecurityExpressionHandler ;

    @Autowired
    private GatewayAccessDeniedHandler gatewayAccessDeniedHandler ;

    @Autowired
    private GatewayAuthenticationEntryPoint gatewayAuthenticationEntryPoint ;

    // 添加表达式处理器
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .authenticationEntryPoint(gatewayAuthenticationEntryPoint)// 401认证失败处理器
                .accessDeniedHandler(gatewayAccessDeniedHandler) // 403访问被拒绝的处理器
                .expressionHandler(gatewayWebSecurityExpressionHandler) ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new GatewayRateLimitFilter(), SecurityContextPersistenceFilter.class)
            .addFilterBefore(new GatewayAuditLogFilter(), ExceptionTranslationFilter.class)
            .authorizeRequests()
            .antMatchers("/token/**").permitAll()
            //.anyRequest().authenticated()
            .anyRequest().access("#permissionService.hasPermission(request,authentication)")
        ;
    }

//    当数据中配置的resource_ids为空时，表示不管你的resourceId是什么，发出去的token都能访问
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("gateway ") ;
//    }
}
