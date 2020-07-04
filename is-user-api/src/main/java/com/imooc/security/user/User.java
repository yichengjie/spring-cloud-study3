package com.imooc.security.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * ClassName: User
 * Description: TODO(描述)
 * Date: 2020/7/1 22:02
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;

    @NotBlank(message = "用户名不能为空")
    @Column(unique = true)
    private String username ;
    @NotBlank(message = "密码不能为空")
    private String password ;

    // 权限
    private String permissions ;

    public UserInfo buildInfo() {
        UserInfo userInfo = new UserInfo() ;
        BeanUtils.copyProperties(this, userInfo);
        return userInfo ;
    }


}
