package com.binzikeji.itoken.service.sso.service;

import com.binzikeji.itoken.common.domain.TbSysUser;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/18 16:56
 **/
public interface RegisterService {

    /**
     * 注册
     * @param tbSysUser
     */
    void register (TbSysUser tbSysUser);
}
