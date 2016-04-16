/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：SubjectServiceImpl.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月3日上午9:28:58         Create
 */
package com.iflytek.aio.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.dto.UserFully;
import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.msg.MsgKey;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.common.system.session.SessionHander;
import com.iflytek.aio.common.util.ConfigRead;
import com.iflytek.aio.common.util.DateUtil;
import com.iflytek.aio.common.util.NumberUtils;
import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.common.util.ZXingUtil;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.Subject;
import com.iflytek.aio.mapper.FaceMapper;
import com.iflytek.aio.mapper.SubjectMapper;

/**
 * @author haihu
 *
 * @create 2015年7月3日 上午9:28:58
 *
 * @version 1.0
 * 
 * @description 主题模块业务层，包括指定主题的新增，删除，修改，分页查看主题信息
 * 
 */
@Service
public class SubjectServiceImpl {
    /**
     * 日志记录器
     */
    private static Logger logger = Logger.getLogger(SubjectServiceImpl.class);
    /**
     * 基础路径
     */
    private static final String BASE_PATH = ConfigRead.newInstance().readValue(
            Constant.BASE_PATH);

    /**
     * 上传文件路径
     */
    private static final String UPLOAD_PATH = ConfigRead.newInstance()
            .readValue(Constant.UPLOAD_PATH);

    /**
     * 手机端注册URI前缀
     */
    private static final String MOBILEREG_URI_PREFIX = ConfigRead.newInstance()
            .readValue(Constant.MOBILEREG_URI_PREFIX);

    /**
     * 手机端注册URI后缀
     */
    private static final String MOBILEREG_URI_SUFFIX = ConfigRead.newInstance()
            .readValue(Constant.MOBILEREG_URI_SUFFIX);

    /**
     * 主题库信息表
     */
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 人脸库信息表
     */
    @Autowired
    private FaceMapper faceMapper;

    /**
     * 人脸业务类
     */
    @Autowired
    private FaceServiceImpl faceServiceImpl;

    /**
     * @descrption 增加主题模块信息
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param subject
     *            主题实例
     * @return Result<String>
     * @throws RuntimeException
     *             运行时异常
     */
    public Result<String> saveSubject(Subject subject) throws RuntimeException {
        logger.info("[saveSubject] | 新增主题模块~");
        UserFully userFully = SessionHander.getUserFully();
        if (null == userFully || StringUtils.isEmpty(subject.getSubjectName())) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            subject.setOrgCode(userFully.getOrgCode());
            subject.setDelFlag(Constant.DefaultValue.NOT_DELETE);
            if (isExistSubjectName(subject)) {
                return new Result<String>(MsgKey.SUBJECT_IS_EXIST,
                        subject.getSubjectName());
            }
            // 检测主题编号是否为空，不为空则设置其为空
            if (subject.getId() != null) {
                subject.setId(null);
            }
            subject.setCreateUserId(userFully.getUserId());
            subject.setCreateTime(DateUtil.date2Str(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            trimSubjectProps(subject);
            subjectMapper.save(subject);
            Subject dbSubject = subjectMapper.findById(subject.getId());
            if (null == dbSubject) {
                logger.error("[saveSubject] | 添加组织机构后，查询该组织机构失败，事务回滚");
                throw new ServiceException();
            }
            return new Result<String>(MsgKey.SUCCESS_OPERATION,
                    String.valueOf(dbSubject.getId()));
        } catch (Exception e) {
            logger.error("[saveSubject] | 添加组织机构时运行出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 去除主题属性值两端的空格
     * 
     * @author: haihu
     * @createTime: 2015-07-11 下午05:18:59
     * @param subject
     *            void
     */
    private void trimSubjectProps(Subject subject) {
        String name = subject.getSubjectName();
        name = StringUtils.isEmpty(name) ? name : name.trim();
        subject.setSubjectName(name);
    }

    /**
     * @descrption 校验该主题名称是否已存在
     * @author haihu
     * @create 2015年7月28日下午4:29:26
     * @version 1.0
     * @param subject
     *            主题实例
     * @return boolean
     */
    private boolean isExistSubjectName(Subject subject) {
        if (logger.isDebugEnabled()) {
            logger.debug("执行isExistSubject方法开始.subject:" + subject);
        }
        try {
            // 获取该主题名称个数
            int count = subjectMapper.findCountBySubjectName(subject);
            // 主题名称已存在
            if (count > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("isExistAccountName:判断指定主题名称是否已存在时出错.", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 删除主题
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param id
     *            主题编号
     * @return Result<String>
     * @throws RuntimeException
     *             运行时异常
     */
    public Result<String> delSubjectById(Long id) throws RuntimeException {
        logger.info("[delSubjectById] | 通过主键删除主题~");
        if (NumberUtils.isNullOfId(id)) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            subjectMapper.deleteById(id);
            return new Result<String>(MsgKey.SUCCESS_OPERATION,
                    String.valueOf(id));
        } catch (Exception e) {
            logger.error("[delSubjectById] | 通过主键删除主题出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 更新主题模块信息
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param subject 主题实例
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> updateSubject(Subject subject)
        throws RuntimeException {
        logger.info("[updateSubject] | 更新主题模块~");
        if (null == subject) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            UserFully userFully = SessionHander.getUserFully();
            subject.setOrgCode(userFully.getOrgCode());
            subject.setDelFlag(Constant.DefaultValue.NOT_DELETE);
            Subject dbSubject = subjectMapper.findById(subject.getId());
            if (null == dbSubject) {
                logger.error("[updateSubject] | 未找到该主题模块，无法更新");
                throw new ServiceException();
            }
            if (isExistSubjectName(subject)) {
                return new Result<String>(MsgKey.SUBJECT_IS_EXIST,
                        subject.getSubjectName());
            }
            subject.setUpdateTime(DateUtil.date2Str(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            subjectMapper.update(subject);
            return new Result<String>(MsgKey.SUCCESS_OPERATION,
                    String.valueOf(subject.getId()));
        } catch (Exception e) {
            logger.error("[updateSubject] | 更新主题模块时运行出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 复制指定主题编号对应的与会人员信息~
     * @author admin
     * @create 2015年8月17日下午1:52:32
     * @version 1.0
     * @param subject
     *            主题实例
     * @return Result<String>
     * @throws RuntimeException
     *             运行时异常
     */
    public Result<String> copySubject(Subject subject) throws RuntimeException {
        logger.info("[copySubject] | 复制指定主题编号对应的与会人员信息~~");
        Long oldSubjectId = subject.getId();
        String newSubjectId = "";
        if (subject.getId() == null || "".equals(subject.getId().toString())) {
            logger.error("[copySubject] | 复制指定主题时运行出错");
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            // 添加新的主题信息
            Result<String> result = this.saveSubject(subject);
            if (!result.getKey().equals(MsgKey.SUCCESS_OPERATION)) {
                return result;
            }
            newSubjectId = result.getData();
            faceServiceImpl.copyBySubjectId(oldSubjectId,
                    Long.valueOf(newSubjectId));
        } catch (Exception e) {
            logger.error("[copySubject] | 复制指定主题时运行出错", e);
            throw new ServiceException(e);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION, newSubjectId);
    }

    /**
     * @descrption 按姓名展示对应人员信息，若姓名为空,则分页展示主题模块信息
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param pager 分页参数
     * @param subjectId 主题ID
     * @param faceName 人员姓名
     * @return Result<Pager<Subject>>
     * @throws RuntimeException 运行时异常
     */
    public Result<Pager<Subject>> findPager(Pager<Subject> pager,
            Long subjectId, String faceName) throws RuntimeException {
        logger.info("[findPager] | 分页查找主题对应与会人员信息~");
        List<Subject> subjectList = new ArrayList<Subject>();
        try {
            Result<Pager<Subject>> result = validatePagerParam(pager, null,
                    Constant.DefaultValue.SUBJECT_PAGER_SIZE);
            if (!result.isStatus()) {
                return result;
            }
            pager = result.getData();
            Subject subject = subjectMapper.findById(subjectId);
            pager.setEntity(subject);
            List<Face> faceList = faceMapper.findPagerList(pager, faceName);
            Long totalRows = faceMapper.findPagerCount(subjectId);
            subject.setFaceList(faceList);
            subjectList.add(subject);
            pager.setData(subjectList);
            pager.setTotalRows(totalRows);
            return new Result<Pager<Subject>>(MsgKey.SUCCESS_OPERATION, pager);
        } catch (Exception e) {
            logger.error("[findSubjectInfoByName] | 分页查找主题对应与会人员信息出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 生成指定主题ID对应的二维码
     * @author admin
     * @create 2015年8月4日下午4:27:36
     * @version 1.0
     * @param request 请求参数
     * @param subjectId 主题ID
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> generateQRCodeBySubjectId(HttpServletRequest request,
            Long subjectId) throws RuntimeException {
        logger.info("[generateQRCodeBySubjectId] | 生成指定主题ID对应的二维码~");
        String contents = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort();
        // 生成手机注册的地址url
        contents = contents + MOBILEREG_URI_PREFIX + subjectId.toString()
                + MOBILEREG_URI_SUFFIX;
        String folderPath = BASE_PATH + File.separator + "qrcode";
        String qrcodeName = "";
        String qrcodePath = "";
        try {
            File file = new File(folderPath);
            if (!file.exists()) {
                file.mkdir();
            }
            qrcodeName = ZXingUtil.encode(contents.toString(), file.getPath());
            qrcodePath = UPLOAD_PATH + "qrcode" + File.separator + qrcodeName;
            subjectMapper.saveQRCode(subjectId, qrcodePath);
        } catch (Exception e) {
            logger.error("[generateQRCodeBySubjectId] | 生成二维码出错", e);
            throw new ServiceException(e);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION, qrcodePath);
    }

    /**
     * @descrption 查找当前所有主题模块名称~
     * @author haihu
     * @create 2015年7月7日下午3:17:40
     * @version 1.0
     * @param session httpsesion
     * @return Result<List<HashMap<String, String>>>
     * @throws RuntimeException 运行时异常
     */
    public Result<List<HashMap<String, String>>> findAllSubjectName(
            HttpSession session) throws RuntimeException {
        logger.info("[findAllSubjectName] | 查找当前所有主题模块名称~");
        List<HashMap<String, String>> subjectNameList = new ArrayList<HashMap<String, String>>();
        if (null == session.getAttribute(Constant.SESSION_KEY)) {
            return new Result<List<HashMap<String, String>>>(
                    MsgKey.ERROR_PARAM_ILLEGAL, null);
        }
        UserFully userFully = (UserFully) session
                .getAttribute(Constant.SESSION_KEY);
        if (null == userFully.getOrgCode() || "".equals(userFully.getOrgCode())) {
            return new Result<List<HashMap<String, String>>>(
                    MsgKey.ERROR_PARAM_ILLEGAL, null);
        }
        try {
            subjectNameList = subjectMapper.findAllSubNameByOrgCode(userFully
                    .getOrgCode());
            return new Result<List<HashMap<String, String>>>(
                    MsgKey.SUCCESS_OPERATION, subjectNameList);
        } catch (Exception e) {
            logger.error("[delSubjectById] | 查找当前所有主题模块出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 查找主题模块名称~
     * @author haihu
     * @create 2015年7月7日下午3:17:40
     * @version 1.0
     * @param id 主题编号
     * @return String
     * @throws RuntimeException 运行时异常
     */
    public String findSubjectNameById(Long id) throws RuntimeException {
        logger.info("[findSubjectNameById] | 通过编号查找主题模块名称~");
        try {
            String subjectName = subjectMapper.findNameById(id);
            return subjectName;
        } catch (Exception e) {
            logger.error("[delSubjectById] | 通过编号查找主题模块名称出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @author: haihu
     * @createTime: 2015年7月6日 上午10:16:04
     * @description: 验证分页条件
     * @param pager
     *            分页条件
     * @param subject
     *            查询条件
     * @param pageSize
     *            显示条数
     * @return Result<Pager<Face>>
     */
    private Result<Pager<Subject>> validatePagerParam(Pager<Subject> pager,
            Subject subject, int pageSize) {
        if (pager == null) {
            return new Result<Pager<Subject>>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        if (pager.getCurPage() <= 0) {
            pager.setCurPage(1);
        }
        if (subject == null) {
            subject = new Subject();
        }
        pager.setEntity(subject);
        pager.setPageSize(pageSize);
        return new Result<Pager<Subject>>(MsgKey.SUCCESS_OPERATION, pager);
    }
}
