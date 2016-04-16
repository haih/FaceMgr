/**	
 * <br>
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 * <br>			 
 * Package: com.iflytek.usap.web.common.contoller <br>
 * FileName: MenuController.java <br>
 * <br>
 * @version
 * @author sbwang
 * @created 2014-12-24
 * @last Modified 
 * @history
 */

package com.iflytek.aio.controller.menu;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.iflytek.aio.common.dto.MenuTree;
import com.iflytek.aio.common.dto.ServiceResult;
import com.iflytek.aio.common.util.MessageDto;
import com.iflytek.aio.service.MenusService;
import com.iflytek.aio.service.impl.RestEasyService;

/**
 * 菜单的Controller
 * 
 * @author sbwang
 * @lastModified
 * @history
 */
@Controller
@RequestMapping("/menu")
public class MenusController {

	// 注入菜单操作类
	@Autowired
	private MenusService menusService;

	/**
	 * 知识库系统首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/topmenus")
	@ResponseBody
	public String index(Model model, HttpSession session) {
//		SessionHander.restUserFully();
		List<MenuTree> topMenus = menusService.queryTopMenus();
		model.addAttribute("topmenus", topMenus);
		return JSON.toJSONString(topMenus);
	}

	/**
	 * 根据top菜单的ID查询左侧菜单
	 * 
	 * @author: yhsu
	 * @createTime: 2014-12-8 下午05:00:44
	 * @param id
	 * @return String
	 */
	@RequestMapping(value = "/queryLeftMenu")
	@ResponseBody
	public String queryLeftMenu(Long id, String type) {
		List<MenuTree> leftMenu = menusService.queryLeftMenus(id, type);
		return JSON.toJSONString(leftMenu);
	}

	/**
	 * 修改密码
	 * 
	 * @author: litong
	 * @createTime: 2015年1月5日 下午4:03:57
	 * @param oldPassword
	 * @param newPassWord
	 * @return String
	 */
	@RequestMapping(value = "/changePassword")
	@ResponseBody
	public String changePassWord(String oldPassword, String newPassword) {
		MessageDto<Object> msgDto = new MessageDto<Object>(false);
		if (StringUtils.isEmpty(oldPassword)
				|| StringUtils.isEmpty(newPassword)) {
			msgDto.setMsg("传入旧密码或新密码为空！");
			return msgDto.toString();
		}
		ServiceResult serviceResult = RestEasyService.getInstance().updatePwd(
				newPassword, oldPassword);
		msgDto.setFlag(serviceResult.isSuccess());
		msgDto.setMsg(serviceResult.getResult().toString());
		return msgDto.toString();
	}

}
