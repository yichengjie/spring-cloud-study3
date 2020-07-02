package com.imooc.security.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ClassName: UserServiceImpl
 * Description: TODO(描述)
 * Date: 2020/7/1 22:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository ;

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User() ;
        BeanUtils.copyProperties(userInfo, user) ;
        userRepository.save(user) ;
        userInfo.setId(user.getId());
        return userInfo;
    }

    public UserInfo update(UserInfo user) {
        return user;
    }

    public void delete(Long id) {

    }

    public UserInfo get(Long id) {

        return userRepository.findById(id).get().buildInfo();
    }

    public List<UserInfo> query(String name) {
        return new ArrayList<UserInfo>();
    }
}
