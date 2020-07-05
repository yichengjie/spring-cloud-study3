package com.imooc.security.filter;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: TokenInfo
 * Description: TODO(描述)
 * Date: 2020/7/5 12:18
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
public class TokenInfo {
    // 令牌是否可用
    private boolean active ;
    // 令牌是发给哪个客户端应用的
    private String client_id ;
    // scope 集合
    private String [] scope ;
    // 令牌是发给哪个用户的
    private String user_name ;
    // 这个令牌可以访问哪些资源服务器的id
    private String [] aud ;
    // 令牌的过期时间
    private Date exp ;
    // 当前这个令牌对应用户的所有的权限
    private String [] authorities ;

}
