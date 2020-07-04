package com.imooc.security.config;

import com.imooc.security.filter.AclInterceptor;
import com.imooc.security.filter.AuditLogInterceptor;
import com.imooc.security.user.UserInfo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * ClassName: SecurityConfig
 * Description: TODO(描述)
 * Date: 2020/7/2 21:57
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Configuration
@EnableJpaAuditing // jpa的审计开关打开
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInterceptor auditLogInterceptor ;

    @Autowired
    private AclInterceptor aclInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 指定针对哪些方法拦截
        // 不配置表示对所有方法拦截
        registry.addInterceptor(auditLogInterceptor) ;

        registry.addInterceptor(aclInterceptor) ;
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> {
            // 自己想办法去实现获取用户
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpSession session = servletRequestAttributes.getRequest().getSession();
            UserInfo user = (UserInfo) session.getAttribute("user");
            String username = null ;
            if (user != null){
                username = user.getUsername() ;
            }
            return Optional.ofNullable(username);
        };
    }
}
