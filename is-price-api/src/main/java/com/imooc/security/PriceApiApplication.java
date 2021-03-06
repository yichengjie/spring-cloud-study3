package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * ClassName: PriceApiApplication
 * Description: TODO(描述)
 * Date: 2020/7/4 18:52
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@EnableResourceServer
@SpringBootApplication
public class PriceApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(PriceApiApplication.class, args) ;
    }
}
