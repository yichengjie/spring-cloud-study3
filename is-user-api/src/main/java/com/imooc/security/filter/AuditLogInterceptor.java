package com.imooc.security.filter;

import com.imooc.security.log.AuditLog;
import com.imooc.security.log.AuditLogRepository;
import com.imooc.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 3. 审计
 * ClassName: AuditLogInterceptor
 * Description: TODO(描述)
 * Date: 2020/7/2 21:48
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        AuditLog auditLog = new AuditLog() ;
        auditLog.setMethod(request.getMethod());
        auditLog.setPath(request.getRequestURI());
        User user = (User)request.getAttribute("user") ;
        if (user !=null){
            auditLog.setUsername(user.getUsername());
        }
        auditLogRepository.save(auditLog) ;
        request.setAttribute("auditLogId", auditLog.getId());
        return true;
    }

    //postHandle 方法是处理成功以后才会调用
    //afterCompletion 不管处理成功或失败都会调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long logId = (Long) request.getAttribute("auditLogId");
        AuditLog log = auditLogRepository.findById(logId).get();
        log.setStatus(response.getStatus());
        auditLogRepository.save(log) ;
    }
}
