package com.yicj.study.runner;

import com.yicj.study.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ClassName: MyRunner
 * Description: TODO(描述)
 * Date: 2020/7/10 16:07
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
@Component
public class MyRunner implements ApplicationRunner {

    @Autowired
    private UserInfo userInfo ;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("username : {} ", userInfo);
    }
}
