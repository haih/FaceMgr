/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：FaceController.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author          Date                   Operation 
 * 1.0       haihu       2015年7月1日上午9:16:04         Create
 */
package com.iflytek.aio.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.entity.SharingFace;
import com.iflytek.aio.service.impl.SharingServiceImpl;

/**
 * @author haihu
 *
 * @create 2015年7月1日 上午9:16:04
 *
 * @version 1.0
 * 
 * @description
 * 
 */
@Controller
@RequestMapping("sharing")
public class SharingController {

    @Autowired
    SharingServiceImpl sharingServiceImpl;

    @ResponseBody
    @RequestMapping("findPager")
    public Result<Pager<SharingFace>> findPager(Pager<SharingFace> pager,
            SharingFace sharingFace)
        throws RuntimeException {
        if(sharingFace == null || sharingFace.getName() == null){
            return sharingServiceImpl.findPager(pager,sharingFace);
        }
        return sharingServiceImpl.findByName(pager, sharingFace);
    }

    @ResponseBody
    @RequestMapping("save")
    public Result<String> save(@RequestBody SharingFace sharingFace) {
        return sharingServiceImpl.save(sharingFace);
    }
    
    @ResponseBody
    @RequestMapping("update")
    public Result<String> update(@RequestBody SharingFace sharingFace) {
        return sharingServiceImpl.update(sharingFace);
    }

    @ResponseBody
    @RequestMapping("delete")
    public Result<String> delete(@RequestParam(value = "ids[]") Long[] ids) {
        return sharingServiceImpl.deleteByIds(ids);
    }

    //Deprecated！merger with findPager interface
    @ResponseBody
    @RequestMapping("findByName")
    public Result<Pager<SharingFace>> findByName(Pager<SharingFace> pager,
            SharingFace sharingFace) {
        return sharingServiceImpl.findByName(pager,sharingFace);
    }

    @ResponseBody
    @RequestMapping("upload")
    public Result<String> upload(MultipartHttpServletRequest request,
            HttpServletResponse response) {
        return sharingServiceImpl.uploadImg(request, response);
    }
    
    @ResponseBody
    @RequestMapping("addFromSubject")
    public Result<String> addFromSubject(@RequestParam(value = "ids[]") Long[] faceIds) {
        return sharingServiceImpl.addFromSubject(faceIds);
    }  
}
