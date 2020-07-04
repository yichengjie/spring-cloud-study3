package com.imooc.security.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

/**
 * ClassName: PriceController
 * Description: TODO(描述)
 * Date: 2020/7/4 18:53
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@RestController
@RequestMapping("/prices")
public class PriceController {

    @GetMapping("/{id}")
    public PriceInfo getPrice(@PathVariable Long id){
        log.info("ProductId is : {}", id);
        PriceInfo info = new PriceInfo() ;
        info.setId(id);
        info.setPrice(new BigDecimal(100));
        return info ;
    }

}
