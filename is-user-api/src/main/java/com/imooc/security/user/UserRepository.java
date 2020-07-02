package com.imooc.security.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName: UserRepository
 * Description: TODO(描述)
 * Date: 2020/7/1 22:36
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Repository
public interface UserRepository
        extends JpaSpecificationExecutor<User>, CrudRepository<User,Long> {

    User findByUsername(String username);
}
