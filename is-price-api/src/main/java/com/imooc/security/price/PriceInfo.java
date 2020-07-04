package com.imooc.security.price;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: PriceInfo
 * Description: TODO(描述)
 * Date: 2020/7/4 18:54
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Data
public class PriceInfo {
    // id
    private Long id ;
    // 价格
    private BigDecimal price ;
}
