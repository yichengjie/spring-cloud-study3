package com.imooc.security.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

/**
 * 限流错误扩展点
 * ClassName: MyRateLimitErrorHandler
 * Description: TODO(描述)
 * Date: 2020/7/5 15:32
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Component
public class MyRateLimitErrorHandler extends DefaultRateLimiterErrorHandler {

    @Override
    public void handleError(String msg, Exception e) {
        // limit限流时扩展点
        super.handleError(msg, e);
    }
}
