package com.imooc.security.log;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

/**
 * ClassName: AuditLog
 * Description: TODO(描述)
 * Date: 2020/7/2 21:42
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
@Entity
// 在需要做审计值注入的类里面加入下面注解
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime ;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime ;

    private String method ;

    private String path ;

    private Integer status ;

    @LastModifiedBy
    private String username ;
}
