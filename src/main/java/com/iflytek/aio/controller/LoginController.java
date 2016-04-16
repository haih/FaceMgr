/* 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：LoginController.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年6月30日上午10:54:52         Create
 */

package com.iflytek.aio.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.dto.ServiceResult;
import com.iflytek.aio.common.system.session.SessionHander;
import com.iflytek.aio.service.impl.RestEasyService;

/**
 * @author haihu 
 *
 * @create 2015年6月30日 上午10:54:52
 *
 * @version 1.0
 * 
 * @description
 * 
 */
@Controller
public class LoginController {
    
    @Autowired
    RestEasyService restEasyService;
    /**
     * @author: ycli5
     * @createTime: 2015年5月8日 上午8:47:43
     * @description:初始化首页界面
     * @return String
     */
    @RequestMapping("aiologin")
    public String aiologin() {
        
        return "login";
    }
    
    /**
     * 提交登陆
     * 
     * @author: haihu
     * @createTime: 2015-6-30 上午11:34:51
     * @param session
     * @param userName
     * @param password
     * @return String
     */ 
    @RequestMapping("login")
    public String login(HttpSession session, String username, String password,
            Model model) {
        ServiceResult result = restEasyService.loginCheck(username, password);
        if (result !=null && result.isSuccess()) {
            session.setAttribute(Constant.SESSION_KEY, result.getResult());
            return "index";
        } else {
            model.addAttribute("msg", result.getMessage());
            return "login";
        }
    }
    
    /**
     * 提交登出
     * 
     * @author: haihu
     * @createTime: 2015-7-7 上午11:34:51
     */
    @RequestMapping(value = "logout")
    public String logout() throws Exception {
        SessionHander.removeUserFully();
        return "redirect:/aiologin.do";
    }
}
