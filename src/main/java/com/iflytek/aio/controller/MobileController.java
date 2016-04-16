/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：SubjectController.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月3日上午9:04:45         Create
 */
package com.iflytek.aio.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.service.impl.FaceServiceImpl;
import com.iflytek.aio.service.impl.RestEasyService;
import com.iflytek.aio.service.impl.SubjectServiceImpl;

/**
 * @author haihu
 *
 * @create 2015年7月3日 上午9:04:45
 *
 * @version 1.0
 * 
 * @description 手机端的路径
 * 
 */

@Controller
@RequestMapping("mobile")
public class MobileController {
    
    @Autowired
    RestEasyService restEasyService;
    
    @Autowired
    SubjectServiceImpl subjectServiceImpl;

    @Autowired
    FaceServiceImpl faceServiceImpl;
    

    /**
     * @descrption 手机用户注册界面
     * @author haihu
     * @create 2015年8月3日下午3:04:43
     * @version 1.0
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/{subjectId}/reg")
    public ModelAndView mobileReg(@PathVariable Long subjectId) {
        String subjectName = subjectServiceImpl.findSubjectNameById(subjectId);
        ModelAndView mv = new ModelAndView();
        if (null == subjectName || "".equals(subjectName)) {
            mv.addObject("error");
        }
        mv.addObject("subjectName", subjectName);
        mv.addObject("subjectId", subjectId);
        mv.setViewName("mobileClient");
        return mv;
    }

    /**
     * @descrption 手机注册界面校验用户信息是否可用(通过返回结果标识判断)
     * @author haihu
     * @create 2015年8月4日下午3:04:43
     * @version 1.0
     * @param subjectId
     * @return
     */
    @RequestMapping("isUserAvailable")
    @ResponseBody
    public Result<String> isUserAvailable(String name, String department,Long subjectId) {
        return faceServiceImpl.isUserAvailable(name, department,subjectId);
    }
    
    /**
     * @descrption 手机端上传图片
     * @author haihu
     * @create 2015年7月29日下午7:36:31
     * @version 1.0
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("upload")
    public Result<String> upload(MultipartHttpServletRequest request,
            HttpServletResponse response) {
        return faceServiceImpl.uploadImg(request, response);
    }
    
    /**
     * @descrption 手机端注册单条与会人员
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("addParticipants")
    @ResponseBody
    public Result<String> addParticipants(@RequestBody Face faceInfo) {
        return faceServiceImpl.save(faceInfo, Constant.DefaultValue.FROM_USER);
    }
}
