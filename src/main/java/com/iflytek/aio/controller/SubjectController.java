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

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.Subject;
import com.iflytek.aio.service.impl.FaceServiceImpl;
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
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    SubjectServiceImpl subjectServiceImpl;

    @Autowired
    FaceServiceImpl faceServiceImpl;

    /**
     * @descrption 获取系统中所有主题的名称信息
     * @author haihu
     * @create 2015年7月7日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("findNameList")
    @ResponseBody
    public Result<List<HashMap<String, String>>> findNameList(
            HttpSession session) {
        return subjectServiceImpl.findAllSubjectName(session);
    }

    /**
     * @descrption 按姓名展示对应人员信息，若姓名为空,则分页展示主题模块信息
     * @author haihu
     * @create 2015年7月4日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("findPager")
    @ResponseBody
    public Result<Pager<Subject>> findPager(Pager<Subject> pager,
            @RequestParam(value = "id") Long subjectId, @RequestParam(
                value = "faceName") String faceName) {
        return subjectServiceImpl.findPager(pager, subjectId, faceName);
    }

    /**
     * @descrption 增加主题模块信息
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public Result<String> save(Subject subject) {
        return subjectServiceImpl.saveSubject(subject);
    }

    /**
     * @descrption 删除指定主题模块信息
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Result<String> delete(Long id) {
        return subjectServiceImpl.delSubjectById(id);
    }

    /**
     * @descrption 更新主题模块信息
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Result<String> update(Subject subject) {
        return subjectServiceImpl.updateSubject(subject);
    }   
    
    /**
     * @descrption 更新主题模块信息
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("copy")
    @ResponseBody
    public Result<String> copy(Subject subject) {
        return subjectServiceImpl.copySubject(subject);
    }

    /**
     * @descrption 根据主题ID下载对应的二维码图片
     * @author haihu
     * @create 2015年8月3日下午3:57:17
     * @version 1.0
     * @param SubjectId
     * @param response
     */
    @RequestMapping(value = "generateQRCode")
    @ResponseBody
    public Result<String> generateQRCodeBySubjectId(HttpServletRequest request,
            Long subjectId) {
        return subjectServiceImpl.generateQRCodeBySubjectId(request, subjectId);
    }

    /**
     * @descrption 下载人员信息模板
     * @author admin
     * @create 2015年8月6日下午3:48:09
     * @version 1.0
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("downloadTemplate")
    public Result<String> downloadTemplate(HttpServletRequest request,
            HttpServletResponse response) {
        return faceServiceImpl.downloadTemplate(response);
    }

    /**
     * @descrption 下载人员信息模板
     * @author admin
     * @create 2015年8月6日下午3:48:09
     * @version 1.0
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("downloadErrorList")
    public Result<String> downloadErrorList(HttpServletResponse response,
            String path) {
        return faceServiceImpl.downloadErrorFaceInfos(response, path);
    }

    /*------------------------------------对主题与会人员的相关操作-----------------------------------------*/
    /**
     * @descrption 上传图片
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
     * @descrption 批量上传与会人员信息
     * @author haihu
     * @create 2015年8月09日下午7:36:31
     * @version 1.0
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("batchUpload")
    public Result<List<Face>> batchUpload(MultipartHttpServletRequest request,
            Long subjectId) {
        return faceServiceImpl.uploadZip(request, subjectId);
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
        return faceServiceImpl.save(faceInfo, Constant.DefaultValue.FROM_USER);
    }

    /**
     * @descrption 删除与会人员列表
     * @author haihu
     * @create 2015年7月3日上午9:17:18
     * @version 1.0
     * @return
     */
    @RequestMapping("deleteParticipants")
    @ResponseBody
    public Result<String> deleteParticipants(
            @RequestParam(value = "ids[]") Long[] ids) {
        return faceServiceImpl.deleteByIds(ids);
    }

    @ResponseBody
    @RequestMapping("updateParticipants")
    public Result<String> updateParticipants(@RequestBody Face face) {
        return faceServiceImpl.update(face);
    }

    @ResponseBody
    @RequestMapping("addFromSharing")
    public Result<String> addFromSharing(
            @RequestParam(value = "id") Long subjectId, @RequestParam(
                value = "ids[]") Long[] sharingIds) {
        return faceServiceImpl.addFromSharing(subjectId, sharingIds);
    }

}
