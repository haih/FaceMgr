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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.ifa.IFAUtils;
import com.iflytek.aio.common.msg.MsgKey;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.common.util.ConfigRead;
import com.iflytek.aio.common.util.DateUtil;
import com.iflytek.aio.common.util.FileUtils;
import com.iflytek.aio.common.util.NumberUtils;
import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.FaceImg;
import com.iflytek.aio.entity.SharingFace;
import com.iflytek.aio.entity.SharingFaceImg;
import com.iflytek.aio.mapper.FaceImgMapper;
import com.iflytek.aio.mapper.FaceMapper;
import com.iflytek.aio.mapper.SharingFaceImgMapper;
import com.iflytek.aio.mapper.SharingFaceMapper;

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
public class SharingServiceImpl {
    /**
     * 日志记录器
     */
    private static Logger logger = Logger.getLogger(SharingServiceImpl.class);

    /**
     * 基础路径
     */
    private static final String BASE_PATH = ConfigRead.newInstance().readValue(
            Constant.BASE_PATH);
    
    /**
     * 上传文件路径
     */ 
    private static final String UPLOAD_PATH = ConfigRead.newInstance().readValue(
            Constant.UPLOAD_PATH);

   // private List<Face> errorList = new ArrayList<Face>();

    /**
     * 共享库信息表
     */
    @Autowired
    private SharingFaceMapper sharingFaceMapper;

    /**
     * 共享库信息与图片对应表
     */
    @Autowired
    private SharingFaceImgMapper sharingFaceImgMapper;

    /**
     * 主题库信息表
     */
    @Autowired
    private FaceMapper faceMapper;

    /**
     * 主题库信息表
     */
    @Autowired
    private FaceImgMapper faceImgMapper;

    /**
     * @descrption 共享库分页显示数据
     * @author haihu
     * @create 2015年7月16日上午10:45:44
     * @version 1.0
     * @param pager 分页参数
     * @param sharingFace 公共库实例
     * @return Result<Pager<SharingFace>>
     * @throws RuntimeException 运行时异常
     */
    public Result<Pager<SharingFace>> findPager(Pager<SharingFace> pager,
            SharingFace sharingFace) throws RuntimeException {
        logger.debug("[findPager] | 共享库分页显示数据");
        try {
            Result<Pager<SharingFace>> result = validatePagerParam(pager,
                    Constant.DefaultValue.SHARING_PAGER_SIZE);
            if (!result.isStatus()) {
                return result;
            }
            pager = result.getData();
            List<SharingFace> sharingFaceList = sharingFaceMapper
                    .findPagerList(pager);
            Long totalRows = sharingFaceMapper.findPagerCount(pager);
            pager.setData(sharingFaceList);
            pager.setTotalRows(totalRows);
            return new Result<Pager<SharingFace>>(MsgKey.SUCCESS_OPERATION,
                    pager);
        } catch (Exception e) {
            logger.error("[findPager] | 共享库分页显示数据异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 通过姓名查找共享库信息（包括对应图片的路径）
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param pager 分页参数
     * @param sharingFace 公共库实例
     * @return Result<Pager<SharingFace>>
     * @throws RuntimeException 运行时异常
     */
    public Result<Pager<SharingFace>> findByName(Pager<SharingFace> pager,
            SharingFace sharingFace) throws RuntimeException {
        logger.info("[findByName] | 通过姓名查找共享库信息~");
        try {
            Result<Pager<SharingFace>> result = validatePagerParam(pager,
                    Constant.DefaultValue.SHARING_PAGER_SIZE);
            if (!result.isStatus()) {
                return result;
            }
            if (sharingFace == null
                    || StringUtils.isEmpty(sharingFace.getName())) {
                return new Result<Pager<SharingFace>>(
                        MsgKey.ERROR_PARAM_ILLEGAL);
            }
            pager = result.getData();
            pager.setEntity(sharingFace);
            List<SharingFace> sharingFaceList = sharingFaceMapper
                    .findByName(pager);
            Long count = sharingFaceMapper.findCountByName(pager);
            pager.setData(sharingFaceList);
            pager.setTotalRows(count);
            return new Result<Pager<SharingFace>>(MsgKey.SUCCESS_OPERATION,
                    pager);
        } catch (Exception e) {
            logger.error("[findPager] | 通过单位查找共享库信息异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 插入单张共享库信息以及与图片路径的对应信息
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param sharingFace 公共库实例
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> save(SharingFace sharingFace) throws RuntimeException {
        logger.info("[save] | 新增共享库信息~");
        if (null == sharingFace || StringUtils.isEmpty(sharingFace.getName())
                || StringUtils.isEmpty(sharingFace.getJob())
                || StringUtils.isEmpty(sharingFace.getGender())
                || StringUtils.isEmpty(sharingFace.getDepartment())) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            sharingFace.setCreateTime(DateUtil.date2Str(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            sharingFace.setDelFlag(Constant.DefaultValue.NOT_DELETE);
            trimSharingFaceProps(sharingFace);
            sharingFaceMapper.save(sharingFace);
            Long id = sharingFace.getId();
            SharingFaceImg sharingFaceImg = new SharingFaceImg();
            List<String> imgPathList = sharingFace.getImgPath();
            if (null != imgPathList) {
                for (String imgPath : imgPathList) {
                    sharingFaceImg.setSharingFaceId(id);
                    sharingFaceImg.setImgPath(imgPath);
                    sharingFaceImgMapper.save(sharingFaceImg);
                }
                return new Result<String>(MsgKey.SUCCESS_OPERATION,
                        String.valueOf(sharingFace.getId()));
            } else {
                logger.error("[save] | 此人脸信息无对应图像");
                throw new ServiceException();
            }
        } catch (Exception e) {
            logger.error("[save] | 添加共享库人脸信息时运行出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 去除公共库人员属性值两端的空格
     * 
     * @author: haihu 2015年7月21日上午11:08:18
     * @version 1.0
     * @param sharingFace 公共库人脸实例
     */
    private void trimSharingFaceProps(SharingFace sharingFace) {
        String name = sharingFace.getName();
        name = StringUtils.isEmpty(name) ? name : name.trim();
        String gender = sharingFace.getGender();
        gender = StringUtils.isEmpty(gender) ? gender : gender.trim();
        String job = sharingFace.getJob();
        job = StringUtils.isEmpty(job) ? job : job.trim();
        String department = sharingFace.getDepartment();
        department = StringUtils.isEmpty(department) ? department : department
                .trim();
        String mobile = sharingFace.getMobile();
        mobile = StringUtils.isEmpty(mobile) ? mobile : mobile.trim();
        sharingFace.setName(name);
        sharingFace.setGender(gender);
        sharingFace.setJob(job);
        sharingFace.setDepartment(department);
        sharingFace.setMobile(mobile);
    }

    /**
     * @descrption 更新单条共享库信息
     * @author haihu
     * @create 2015年7月21日上午11:08:18
     * @version 1.0
     * @param sharingFace 共享库实例
     * @return Result<String> 
     * @throws RuntimeException 运行时异常
     */
    public Result<String> update(SharingFace sharingFace)
        throws RuntimeException {
        logger.info("[update] | 更新共享库信息~");
        if (null == sharingFace) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            Long id = sharingFace.getId();
            SharingFaceImg sharingFaceImg = new SharingFaceImg();
            List<String> imgPathList = sharingFace.getImgPath();
            // 更新单条共享库信息
            if (!NumberUtils.isNullOfId(id)) {
                sharingFaceMapper.update(sharingFace);
                sharingFaceImgMapper.deleteBySharingFaceId(id);
                if (null != imgPathList && imgPathList.size() != 0) {
                    for (String imgPath : imgPathList) {
                        sharingFaceImg.setSharingFaceId(id);
                        sharingFaceImg.setImgPath(imgPath);
                        sharingFaceImgMapper.save(sharingFaceImg);
                    }
                } else {
                    logger.error("[update] | 此人脸信息无对应图像");
                    throw new ServiceException();
                }
            } else {
                logger.error("[update] | 无法获取对应信息ID");
                return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
            }
            // 查看此数据在主题库中是否存在
            Long[] faceIdList = faceMapper.findBySharingId(sharingFace.getId());
            if (faceIdList.length > 0) {
                // 存在，则同步更新主题库人员信息(包括图片路径信息)
                for (Long faceId : faceIdList) {
                    Face face = transferToFaceInfo(sharingFace);
                    face.setId(faceId);
                    face.setUpdateTime(DateUtil.date2Str(new Date(),
                            "yyyy-MM-dd HH:mm:ss"));
                    face.setActionFlag(Constant.DefaultValue.ACTION_EDIT);
                    faceMapper.update(face);
                    faceImgMapper.deleteByFaceId(faceId);
                    for (String imgPath : face.getImgPath()) {
                        FaceImg faceImg = new FaceImg();
                        faceImg.setFaceId(face.getId());
                        faceImg.setImgPath(imgPath);
                        faceImgMapper.save(faceImg);
                    }
                }
            } else {
                logger.info("[update] | 此数据在主题库中不存在~");
            }
        } catch (Exception e) {
            logger.error("[update] | 更新共享库信息时运行出错", e);
            throw new ServiceException(e);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION,
                String.valueOf(sharingFace.getId()));
    }

    /**
     * @descrption 转换sharingFace至Face实例
     * @author haihu
     * @create 2015年7月31日上午10:36:29
     * @version 1.0
     * @param sharingFace 公共库实例
     * @return Face
     */
    public static Face transferToFaceInfo(SharingFace sharingFace) {
        Face face = new Face();
        face.setName(sharingFace.getName());
        face.setGender(sharingFace.getGender());
        face.setJob(sharingFace.getJob());
        face.setDepartment(sharingFace.getDepartment());
        face.setImgPath(sharingFace.getImgPath());
        face.setMobile(sharingFace.getMobile());
        return face;
    }

    /**
     * @descrption 删除指定共享库信息 (逻辑删除可多张)
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param ids 被删除的数组
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> deleteByIds(Long[] ids) throws RuntimeException {
        logger.info("[deleteByIds] | 删除指定共享库信息~");
        for (Long id : ids) {
            if (NumberUtils.isNullOfId(id)) {
                return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
            }
            // 逻辑删除人脸库信息，将删除标记修改为1
            try {
                sharingFaceMapper.updateDelStatus(id);
            } catch (Exception e) {
                logger.error("[deleteByIds]|通过主键删除共享库信息出错", e);
                throw new ServiceException(e);
            }
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 从主题库中添加人员至公共库
     * @author haihu
     * @create 2015年7月30日下午7:36:46
     * @version 1.0
     * @param sharingIds 人员列表
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> addFromSubject(Long[] sharingIds)
        throws RuntimeException {
        logger.info("[addFromSubject] | 从主题库中添加人员至公共库~");
        List<Face> faces = faceMapper.findByIds(sharingIds);
        try {
            for (Face face : faces) {
                SharingFace sharingFace = FaceServiceImpl
                        .transferToSharingFace(face);
                this.save(sharingFace);
                faceMapper.updateSharingId(face.getId(), sharingFace.getId());
            }
        } catch (Exception e) {
            logger.error("[addBySubject] | 添加共享库信息时运行出错", e);
            throw new ServiceException(e);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 接收上传图片，返回本地存放图片的路径
     * @author haihu
     * @create 2015年7月11日上午11:08:18
     * @version 1.0
     * @param request 请求参数
     * @param response 响应参数
     * @return Result<String>
     */
    public Result<String> uploadImg(MultipartHttpServletRequest request,
            HttpServletResponse response) {
        logger.info("[uploadImg] | 上传照片 ");
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        String desImgPath = DateUtil.date2Str(new Date(), "yyyy-MM-dd")
                + File.separator;
        File imgFolder = new File(BASE_PATH + desImgPath);
        String desImgName = "";
        String suffix = "";
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            suffix = FileUtils.getFileSuffix(mpf.getOriginalFilename());
            try {
                if (!imgFolder.exists()) {
                    imgFolder.mkdir();
                }
                desImgName = UUID.randomUUID().toString() + suffix;
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
                        imgFolder.getPath() + File.separator + desImgName));
                // 调用图片引擎对上传的图片做可用性检测
                if (!IFAUtils.getInstance().isImgAvailable(
                        imgFolder.getPath() + File.separator + desImgName)) {
                    FileUtils.delFile(imgFolder.getPath() + File.separator
                            + desImgName);
                    return new Result<String>(MsgKey.IMG_NOT_AVAILABLE);
                }
            } catch (IOException e) {
                logger.error("[uploadImg] | 上传文件异常");
                e.printStackTrace();
                return new Result<String>(MsgKey.ERROR_OPERATION);
            }
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION, UPLOAD_PATH
                + desImgPath + desImgName);
    }

    /**
     * @author: haihu
     * @createTime: 2015年7月6日 上午10:16:04
     * @description: 验证分页条件
     * @param pager
     *            分页条件
     * @param pageSize
     *            显示条数
     * @return Result<Pager<Face>>
     */
    private Result<Pager<SharingFace>> validatePagerParam(
            Pager<SharingFace> pager, int pageSize) {
        if (pager == null) {
            return new Result<Pager<SharingFace>>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        if (pager.getCurPage() <= 0) {
            pager.setCurPage(1);
        }
        SharingFace sharingFace = new SharingFace();
        pager.setEntity(sharingFace);
        pager.setPageSize(pageSize);
        return new Result<Pager<SharingFace>>(MsgKey.SUCCESS_OPERATION, pager);
    }

}
