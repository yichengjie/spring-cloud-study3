package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * ClassName: GatewayServer
 * Description: TODO(描述)
 * Date: 2020/7/5 11:51
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayServer {

    public static void main(String[] args) {

        SpringApplication.run(GatewayServer.class, args) ;

    }
}
