package com.yicj.study.config;

import com.yicj.study.model.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * ClassName: AppConfig
 * Description: TODO(描述)
 * Date: 2020/7/10 15:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class AppConfig {
    @Value("${username}")
    private String username ;
    @Value("${password}")
    private String password ;

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer() ;
        Resource location = new ClassPathResource("jdbc.properties") ;
        propertyPlaceholderConfigurer.setLocation(location);
        return propertyPlaceholderConfigurer ;
    }

    @Bean
    public UserInfo userInfo(){
        UserInfo userInfo = new UserInfo() ;
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        return userInfo ;
    }

}
