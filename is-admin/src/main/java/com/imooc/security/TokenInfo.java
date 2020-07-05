package com.imooc.security;

import lombok.Data;

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
  private String access_token ;
  private String token_type ;
  private String expires_in ;
  private String scope ;
}
