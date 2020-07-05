package com.imooc.security.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public OrderInfo create(@RequestBody OrderInfo info, @RequestHeader String username){
        log.info("======> username is : {}" , username );
//        String url = "http://localhost:9060/prices/" + info.getProductId() ;
//        PriceInfo priceInfo = restTemplate.getForObject(url, PriceInfo.class) ;
//        log.info("price is " + priceInfo.getPrice());
        return info ;
    }

    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id, @RequestHeader String username){
        log.info("======> username is : {}" , username );
        log.info("orderId : {}", id);
        return new OrderInfo() ;
    }
}
