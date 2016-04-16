/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：FaceServiceImpl.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月1日上午9:08:09         Create
 */
package com.iflytek.aio.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.msg.MsgKey;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.Subject;
import com.iflytek.aio.mapper.FaceImgMapper;
import com.iflytek.aio.mapper.FaceMapper;
import com.iflytek.aio.mapper.SubjectMapper;

/**
 * @author haihu
 *
 * @create 2015年7月1日 上午9:08:09
 *
 * @version 1.0
 * 
 * @description
 * 
 */

@Service
public class PadServiceImpl {
    /**
     * 日志记录器
     */
    private static Logger logger = Logger.getLogger(PadServiceImpl.class);

    /**
     * 人脸信息表
     */
    @Autowired
    private FaceMapper faceMapper;

    /**
     * 人脸信息表
     */
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 人脸信息与图片对应表
     */
    @Autowired
    private FaceImgMapper faceImgMapper;

    /**
     * @descrption pad端接口，返回所有主题基本信息
     * @author admin
     * @create 2015年8月4日下午4:19:20
     * @version 1.0
     * @param orgCode 组织编号
     * @return Result<List<Subject>>
     */
    public Result<List<Subject>> findSubListForPad(String orgCode) {
        logger.info("[findSubListForPad] | pad端 查找当前组织下所有主题信息~");
        List<Subject> subjectList = new ArrayList<Subject>();
        try {
            subjectList = subjectMapper.findSubjectsByOrgCode(orgCode);
            return new Result<List<Subject>>(MsgKey.SUCCESS_OPERATION,
                    subjectList);
        } catch (Exception e) {
            logger.error("[findSubListForPad] | 查找当前所有主题模块出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 查找主题ID下对应的所有与会人员信息
     * @author haihu
     * @create 2015年8月4日上午9:10:13
     * @version 1.0
     * @param subjectId 主题编号
     * @return Result<List<Face>>
     * @throws RuntimeException 运行时异常
     */
    public Result<List<Face>> findAllFaceForPad(Long subjectId)
        throws RuntimeException {
        logger.info("[findAllFaceForPad] | 查找主题ID下对应的所有与会人员信息~");
        List<Face> faceList = new ArrayList<Face>();
        try {
            faceList = faceMapper.findAllBySubId(subjectId);
        } catch (Exception e) {
            logger.error("[findAllFaceForPad] | 查找主题ID下对应的所有与会人员信息出错", e);
            throw new ServiceException(e);
        }
        return new Result<List<Face>>(MsgKey.SUCCESS_OPERATION, faceList);
    }

    /**
     * @descrption 查找主题ID下对应所有更新的与会人员信息
     * @author haihu
     * @create 2015年8月21日上午10:08:16
     * @version 1.0
     * @param subjectId 主题编号
     * @param dateLine 时间线
     * @return Result<List<Face>>
     * @throws RuntimeException 运行时异常
     */
    public Result<List<Face>> findUpdateFaceById(Long subjectId, String dateLine)
        throws RuntimeException {
        logger.info("[findUpdateFaceById] | 查找主题ID下对应所有更新的与会人员信息~");
        List<Face> faceList = new ArrayList<Face>();
        try {
            faceList = faceMapper.findUpdateInfoById(subjectId, dateLine);
            if (null == faceList) {
                return new Result<List<Face>>(MsgKey.ERROR_SEARCH_RESULT_EMPTY,
                        faceList);
            }
        } catch (Exception e) {
            logger.error("[findUpdateFaceById] | 查找主题ID下对应所有更新的与会人员信息", e);
            throw new ServiceException(e);
        }
        return new Result<List<Face>>(MsgKey.SUCCESS_OPERATION, faceList);
    }

}
