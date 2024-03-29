package com.binzikeji.itoken.service.sso.controller;

import com.binzikeji.itoken.common.domain.TbSysUser;
import com.binzikeji.itoken.common.utils.CookieUtils;
import com.binzikeji.itoken.common.utils.MapperUtils;
import com.binzikeji.itoken.service.sso.service.LoginService;
import com.binzikeji.itoken.service.sso.service.consumer.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 16:05
 **/
@Controller
public class LoginController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private LoginService loginService;

    /**
     * 页面
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String url, HttpServletRequest request, Model model){
        String token = CookieUtils.getCookieValue(request, "token");

        // token 可能以登录
        if (StringUtils.isNotBlank(token)){
            String loginCode = redisService.get(token);
            if (StringUtils.isNotBlank(loginCode)){
                String json = redisService.get(loginCode);
                if (StringUtils.isNotBlank(json)){
                    try {
                        TbSysUser tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
                        // 已登录
                        if (tbSysUser != null){
                            if (StringUtils.isNotBlank(url)){
                                return "redirect:" + url;
                            }
                        }
                        // 将登录信息传到登录页
                        model.addAttribute("tbSysUser", tbSysUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(url)){
            model.addAttribute("url", url);
        }

        return "login";
    }

    /**
     * 登录请求
     * @param loginCode
     * @param password
     * @param url
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String loginCode,
                        @RequestParam(required = true) String password,
                        @RequestParam(required = false) String url,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        TbSysUser tbSysUser = loginService.login(loginCode, password);

        // 登录失败
        if (tbSysUser == null){
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误,请从新输入");
        }

        // 登录成功
        else {
            String token = UUID.randomUUID().toString();
            String result = redisService.put(token, loginCode, 60 * 60 * 24);
            // 将 Token 放入缓存
            if (StringUtils.isNotBlank(result) && "ok".equals(result)){
                CookieUtils.setCookie(request, response, "token", token, 60 * 60 * 24);
                if (StringUtils.isNotBlank(url)){
                    return "redirect:" + url;
                }
            }

            // 熔断处理
            else {
                redirectAttributes.addFlashAttribute("message", "服务器异常,请稍后重试");
            }
        }

        return "redirect:/login";
    }

    /**
     * 注销
     * @param url
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "loginout", method = RequestMethod.GET)
    public String loginout(@RequestParam(required = false) String url, HttpServletRequest request, HttpServletResponse response, Model model){
        CookieUtils.deleteCookie(request, response, "token");
        return login(url, request, model);
    }
}
