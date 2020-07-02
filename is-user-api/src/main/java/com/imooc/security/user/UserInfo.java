package com.imooc.security.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * 封装服务请求和响应的
 * ClassName: UserInfo
 * Description: TODO(描述)
 * Date: 2020/7/1 22:02
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
public class UserInfo {

    private Long id ;

    private String name ;

    @NotBlank(message = "用户名不能为空")
    private String username ;

    @NotBlank(message = "密码不能为空")
    private String password ;
}
