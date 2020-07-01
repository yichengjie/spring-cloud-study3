package com.imooc.security.user;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @PostMapping
    public User create(@RequestBody User user){

        return user ;
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user){

        return user ;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id){

        return new User() ;
    }

    @GetMapping
    public List<User> query(String name){

        return new ArrayList<User>() ;
    }
}
