package com.imooc.security.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName: OrderController
 * Description: TODO(描述)
 * Date: 2020/7/4 18:49
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info){
        String url = "http://localhost:9060/prices/" + info.getProductId() ;
        PriceInfo priceInfo = restTemplate.getForObject(url, PriceInfo.class) ;
        log.info("price is " + priceInfo.getPrice());
        return info ;
    }
}
