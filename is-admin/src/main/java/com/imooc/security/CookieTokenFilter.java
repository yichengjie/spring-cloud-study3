package com.imooc.security;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: CookieTokenFilter
 * Description: TODO(描述)
 * Date: 2020/7/6 21:49
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@Component
public class CookieTokenFilter extends ZuulFilter {
    private RestTemplate restTemplate = new RestTemplate() ;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String accessToken = getCookie("imooc_access_token") ;
        if (StringUtils.isNotBlank(accessToken)){
            requestContext.addZuulRequestHeader("Authorization", "bearer " + accessToken);
        }else {
            String refreshToken = getCookie("imooc_refresh_token") ;
            if (StringUtils.isNotBlank(refreshToken)){
                refreshToken(requestContext, refreshToken ,restTemplate);
            }else {
                requestContext.getResponse().setContentType("application/json");
                requestContext.setResponseStatusCode(500);
                requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
                requestContext.setSendZuulResponse(false);
            }
        }
        return null;
    }

    private String getCookie(String name) {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies){
            if(StringUtils.equals(name,cookie.getName())){
                return cookie.getValue() ;
            }
        }
        return null ;
    }


    public static void refreshToken(RequestContext requestContext, String refreshToken, RestTemplate restTemplate){
        String oauthServiceUrl = "http://gateway.imooc.com:9070/token/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<TokenInfo> newToken = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
            log.info("refresh! token info: " + newToken.getBody().toString());
            AdminApplication.addTokenCookies(newToken.getBody(), requestContext.getResponse());
            requestContext.addZuulRequestHeader("Authorization", "bearer "+ newToken.getBody().getAccess_token());
        } catch (Exception e) {
            requestContext.getResponse().setContentType("application/json");
            requestContext.setResponseStatusCode(500);
            requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
            requestContext.setSendZuulResponse(false);
        }
    }

}
