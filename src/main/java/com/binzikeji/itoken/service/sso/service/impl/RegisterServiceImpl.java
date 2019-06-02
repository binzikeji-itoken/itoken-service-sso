package com.binzikeji.itoken.service.sso.service.impl;

import com.binzikeji.itoken.common.domain.TbSysUser;
import com.binzikeji.itoken.service.sso.mapper.TbSysUserMapper;
import com.binzikeji.itoken.service.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/18 16:57
 **/
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Override
    public void register(TbSysUser tbSysUser) {
        tbSysUser.setPassword(DigestUtils.md5DigestAsHex(tbSysUser.getPassword().getBytes()));
        tbSysUserMapper.insert(tbSysUser);
    }
}
