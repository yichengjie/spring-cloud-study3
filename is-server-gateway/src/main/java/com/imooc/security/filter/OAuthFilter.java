package com.imooc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 1. 认证
 * ClassName: OAuthFilter
 * Description: TODO(描述)
 * Date: 2020/7/5 12:02
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@Component
public class OAuthFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate() ;

    // 真正要写的业务逻辑
    @Override
    public Object run() throws ZuulException {
        log.info("===> oauth start... ");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        // 发往认证服务器的请求是不需要做认证的
        if (StringUtils.startsWith(request.getRequestURI(), "/token")){
            // 直接返回不校验token
            return  null ;
        }
        String authHeader = request.getHeader("Authorization") ;
        if (StringUtils.isBlank(authHeader)){
            // 如果没带请求头直接放行，因为在认证阶段不会拦截请求，直接向下走即可
            return null ;
        }

        if (!StringUtils.startsWithIgnoreCase(authHeader, "bearer ")){
            // 如果不是oauth2的认证也直接向下走
            return  null ;
        }

        try {
            TokenInfo info = getTokenInfo(authHeader) ;
            request.setAttribute("tokenInfo", info);
        }catch (Exception e){
            log.error("get token info fail : ", e);
        }
        log.info("===> oauth end... ");
        return null;
    }

    private TokenInfo getTokenInfo(String authHeader) {
        String token = StringUtils.substringAfter(authHeader, "bearer ") ;
        String oauthServiceUrl = "http://localhost:9090/oauth/check_token" ;
        // 配置头里面带的信息
        HttpHeaders headers = new HttpHeaders() ;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");
        // 带上令牌
        // 注意这里只能用MultiValueMap，如果使用HashMap会报错
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>() ;
        params.add("token", token);
        // 组装请求实体
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params, headers) ;
        // 发送http请求
        ResponseEntity<TokenInfo> response =
                restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class) ;
        log.info("token info : " + response.getBody().toString());
        return response.getBody() ;
    }

    // 判断当前过滤器是否要起作用
    @Override
    public String filterType() {
        // pre, post, error, route
        // pre: 业务逻辑执行前会执行run里面的逻辑
        // post: 业务逻辑执行后会执行run里面的逻辑
        // error: 业务逻辑执行出错后会执行run里面的逻辑
        // route: 一般不写
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 过滤器的执行顺序
        return 1;
    }

    // 此过滤器是否起作用，这返回true表示一直起作用
    @Override
    public boolean shouldFilter() {
        return true;
    }
}
