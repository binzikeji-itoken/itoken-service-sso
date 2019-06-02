package com.binzikeji.itoken.service.sso.service;

import com.binzikeji.itoken.common.domain.TbSysUser;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 15:28
 **/
public interface LoginService {

    /**
     * 登录
     * @param loginCode 账号
     * @param password 密码
     * @return
     */
    TbSysUser login(String loginCode, String password);
}
