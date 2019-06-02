package com.binzikeji.itoken.service.sso.service.impl;

import com.binzikeji.itoken.common.domain.TbSysUser;
import com.binzikeji.itoken.common.utils.MapperUtils;
import com.binzikeji.itoken.service.sso.mapper.TbSysUserMapper;
import com.binzikeji.itoken.service.sso.service.LoginService;
import com.binzikeji.itoken.service.sso.service.consumer.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 15:30
 **/
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Override
    public TbSysUser login(String loginCode, String plantPassword) {
        TbSysUser tbSysUser = null;

        String json = redisService.get(loginCode);
        // 缓存中没有数据，从数据库取数据
        if (json == null){
            Example example = new Example(TbSysUser.class);
            example.createCriteria().andEqualTo("loginCode", loginCode);

            tbSysUser = tbSysUserMapper.selectOneByExample(example);
            String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes());

            if (tbSysUser != null && password.equals(tbSysUser.getPassword())){
                try {
                    redisService.put(loginCode, MapperUtils.obj2json(tbSysUser), 60 * 60 * 24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return tbSysUser;
            }

            else {
                return null;
            }
        }

        // 缓存中有数据
        else {
            try {
                tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("登录 --> 触发熔断", e.getMessage());
            }
        }

        return  tbSysUser;
    }
}
