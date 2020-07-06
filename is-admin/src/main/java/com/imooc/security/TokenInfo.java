package com.imooc.security;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: TokenInfo
 * Description: TODO(描述)
 * Date: 2020/7/5 18:31
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
public class TokenInfo {
    private String access_token;
    private String refresh_token ;
    private String token_type;
    private Long expires_in;
    private String scope;
    // 过期时间
    private LocalDateTime expireTime ;

    public TokenInfo init() {
        this.expireTime = LocalDateTime.now().plusSeconds(expires_in - 3);
        return this;
    }

    public boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }
}
