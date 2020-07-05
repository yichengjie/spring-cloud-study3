package com.imooc.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: SessionTokenFilter
 * Description: TODO(描述)
 * Date: 2020/7/5 20:36
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Component
public class SessionTokenFilter extends ZuulFilter {
  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
      RequestContext requestContext = RequestContext.getCurrentContext();
      HttpServletRequest request = requestContext.getRequest();
      TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute("token");
      if (tokenInfo != null){
         requestContext.addZuulRequestHeader("Authorization", "bearer " + tokenInfo.getAccess_token());
      }
      return null;
  }
}
