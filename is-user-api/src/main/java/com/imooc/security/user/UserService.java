package com.imooc.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: UserService
 * Description: TODO(描述)
 * Date: 2020/7/1 22:58
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface UserService {

    UserInfo create(UserInfo user) ;

    UserInfo update(UserInfo user) ;

    void delete(Long id) ;

    UserInfo get(Long id) ;

    List<UserInfo> query(String name) ;
}
