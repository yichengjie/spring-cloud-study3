package com.imooc.security.user;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public void login(@Validated UserInfo user, HttpServletRequest request){
        UserInfo userInfo = userService.login(user) ;
        //1. 注意这里getSession传递参数false
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        //2. 注意这里getSession传递参数时true
        request.getSession(true).setAttribute("user", userInfo);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session){

        session.invalidate();
    }

    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo user){

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
    public UserInfo get(@PathVariable Long id, HttpSession session){
        UserInfo user = (UserInfo)session.getAttribute("user") ;
        if (user == null || !user.getId().equals(id)){
            throw new RuntimeException("身份认证信息异常，获取用户信息失败") ;
        }
        return userService.get(id) ;
    }

    @GetMapping
    public List<UserInfo> query(String name){

        return userService.query(name) ;
    }
}
