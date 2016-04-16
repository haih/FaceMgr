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

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.dto.ServiceResult;
import com.iflytek.aio.common.dto.UserFully;
import com.iflytek.aio.common.msg.MsgKey;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.FaceImg;
import com.iflytek.aio.entity.Subject;
import com.iflytek.aio.service.impl.FaceServiceImpl;
import com.iflytek.aio.service.impl.PadServiceImpl;
import com.iflytek.aio.service.impl.RestEasyService;
import com.iflytek.aio.service.impl.SubjectServiceImpl;

/**
 * @author haihu
 *
 * @create 2015年7月3日 上午9:04:45
 *
 * @version 1.0
 * 
 * @description
 * 
 */

@Controller
@RequestMapping("pad")
public class PadController {
    
    @Autowired
    RestEasyService restEasyService;
    
    @Autowired
    SubjectServiceImpl subjectServiceImpl;

    @Autowired
    FaceServiceImpl faceServiceImpl;
    
    @Autowired
    PadServiceImpl padServiceImpl;
    
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
    @ResponseBody
    public Result<String> login(HttpSession session, String username, String password,
            Model model) {
        ServiceResult result = restEasyService.loginCheck(username, password);
        if (result.isSuccess()) {
            UserFully userFully = (UserFully)result.getResult();
            return new Result<String>(MsgKey.SUCCESS_OPERATION,userFully.getOrgCode());
        } else {
            return new Result<String>(MsgKey.ERROR_OPERATION);
        }
    }
    
    /**
     * @descrption 获取系统中所有主题的名称信息
     * @author haihu
     * @create 2015年7月7日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("findSubList")
    @ResponseBody
    public Result<List<Subject>> findSubList(
            String orgCode) {
        return padServiceImpl.findSubListForPad(orgCode);
    }

    /**
     * @descrption 通过主题id查找对应所有的与会人员信息
     * @author haihu
     * @create 2015年7月4日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("findAllBySubjectId")
    @ResponseBody
    public Result<List<Face>> findAllBySubjectId(Long subjectId) {
        return padServiceImpl.findAllFaceForPad(subjectId);
    }

    /**
     * @descrption 获取指定主题的更新列表
     * @author haihu 
     * @create 2015年8月21日上午9:55:39
     * @version 1.0
     * @param subjectId
     * @return
     */
    @RequestMapping("getUpdateList")
    @ResponseBody
    public Result<List<Face>> getUpdateList(Long subjectId,String dateLine) {
        return padServiceImpl.findUpdateFaceById(subjectId,dateLine);
    }
    
    
    /**
     * @descrption 新增单条与会人员
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("addParticipants")
    @ResponseBody
    public Result<String> addParticipants(@RequestBody Face faceInfo) {
        return faceServiceImpl.save(faceInfo,Constant.DefaultValue.FROM_USER);
    }

    /**
     * @descrption 添加单个人员单张照片信息
     * @author haihu
     * @create 2015年9月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("addImg")
    @ResponseBody
    public Result<String> addImg(@RequestBody FaceImg faceImg) {
        return faceServiceImpl.addImg(faceImg);
    }
    
    /**
     * @descrption 删除单个人员单张照片信息
     * @author haihu
     * @create 2015年9月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("delImg")
    @ResponseBody    
    public Result<String> delImg(String imgPath) {
        return faceServiceImpl.delImg(imgPath);
    }
    
    @RequestMapping("upload")
    @ResponseBody
    public Result<String> upload(MultipartHttpServletRequest request,
            HttpServletResponse response) {
        return faceServiceImpl.uploadImg(request, response);
    }
}
