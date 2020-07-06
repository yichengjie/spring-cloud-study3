package com.imooc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 3. 授权
 * ClassName: AuthorizationFilter
 * Description: TODO(描述)
 * Date: 2020/7/5 12:38
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("=========> authorization start ....");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (isNeedAuth(request)){
            TokenInfo tokenInfo = (TokenInfo)request.getAttribute("tokenInfo") ;
            if (tokenInfo !=null && tokenInfo.isActive()){
                if (!hasPermission(tokenInfo, request)){
                    // 更新审计日志处理（更新数据库）
                    log.info("audit log update fail 401");
                    handleError(403, requestContext) ;
                }
                // 添加信息,这样在微服务那边就能在header中取到这个值
                requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());

            }else {
                // 请求地址不是以/token开头的才会报警，如果是token开头的可能是取请求token
                if (!StringUtils.startsWith(request.getRequestURI(), "/token")){
                    // 更新审计日志处理（更新数据库）
                    log.info("audit log update fail 401");
                    handleError(401, requestContext) ;
                }
            }
        }

        return null;
    }

    /**
     * 判断是否有权限操作
     * @param tokenInfo
     * @param request
     * @return
     */
    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        //return RandomUtils.nextInt() %2 == 0 ;
        return true ;
    }

    private void handleError(int status, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);
        //{"message":"auth fail"}
        requestContext.setResponseBody("{\"message\":\"auth fail\"}");
        // 这句话的意思就是不要向下走了，正常情况下走完handleError后还继续向后面走
        // 但是如果currentContext.setSendZuulResponse(false)，那么请求将不向下走了，不会调到后面的业务服务
        requestContext.setSendZuulResponse(false) ;
    }

    /**
     * 判断是否需要权限认证
     * @param request
     * @return
     */
    private boolean isNeedAuth(HttpServletRequest request) {
        // 这里写业务判断url是否需要权限认证
        return true ;
    }
}
