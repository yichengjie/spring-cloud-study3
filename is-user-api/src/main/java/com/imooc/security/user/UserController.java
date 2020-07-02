package com.imooc.security.user;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserController
 * Description: TODO(描述)
 * Date: 2020/7/1 22:04
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping
    public UserInfo create(@RequestBody UserInfo user){

        return userService.create(user) ;
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user){

        return userService.update(user) ;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserInfo get(@PathVariable Long id){

        return userService.get(id) ;
    }

    @GetMapping
    public List<UserInfo> query(String name){

        return userService.query(name) ;
    }
}
