package com.binzikeji.itoken.service.sso.register.test;

import com.binzikeji.itoken.common.domain.TbSysUser;
import com.binzikeji.itoken.service.sso.ServiceSSOApplication;
import com.binzikeji.itoken.service.sso.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/18 17:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceSSOApplication.class)
@ActiveProfiles(value = "prod")
@Transactional
@Rollback
public class RegisterTest {

    @Autowired
    private RegisterService registerService;

    @Test
    public void register(){
        TbSysUser tbSysUser = new TbSysUser();
        tbSysUser.setUserCode(UUID.randomUUID().toString());
        tbSysUser.setLoginCode("binzikeji@qq.com");
        tbSysUser.setUserName("binzikeji");
        tbSysUser.setPassword("123456");
        tbSysUser.setUserType("0");
        tbSysUser.setMgrType("1");
        tbSysUser.setStatus("0");
        registerService.register(tbSysUser);
    }
}
