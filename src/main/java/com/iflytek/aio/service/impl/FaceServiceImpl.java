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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import com.google.common.collect.Lists;
import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.excel.FaceExcel;
import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.ifa.IFAUtils;
import com.iflytek.aio.common.msg.MsgKey;
import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.common.util.ConfigRead;
import com.iflytek.aio.common.util.DateUtil;
import com.iflytek.aio.common.util.FileUtils;
import com.iflytek.aio.common.util.NumberUtils;
import com.iflytek.aio.common.util.ResponseUtil;
import com.iflytek.aio.entity.ExcelData;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.FaceImg;
import com.iflytek.aio.entity.SharingFace;
import com.iflytek.aio.entity.SharingFaceImg;
import com.iflytek.aio.mapper.FaceImgMapper;
import com.iflytek.aio.mapper.FaceMapper;
import com.iflytek.aio.mapper.SharingFaceImgMapper;
import com.iflytek.aio.mapper.SharingFaceMapper;
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
public class FaceServiceImpl {
    /**
     * 日志记录器
     */
    private static Logger logger = Logger.getLogger(FaceServiceImpl.class);
    /**
     * 本地存储基地址
     */
    private  static final String BASE_PATH = ConfigRead.newInstance().readValue(
            Constant.BASE_PATH);

    /**
     * 上传文件路径
     */
    private static final String UPLOAD_PATH = ConfigRead.newInstance().readValue(
            Constant.UPLOAD_PATH);
    /**
     * 临时文件路径
     */
    private static final String TEMPLATE_PATH = ConfigRead.newInstance().readValue(
            Constant.TEMPLATE_PATH);
    /**
     * excel名称
     */
    private static final String EXCEL_NAME = "与会人员信息列表";
    
    /**
     * byte数组大小
     */
    private static final int BYTE_SIZE = 100;

    /**
     * 人脸信息表
     */
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 人脸信息表
     */
    @Autowired
    private FaceMapper faceMapper;

    /**
     * 人脸信息与图片对应表
     */
    @Autowired
    private FaceImgMapper faceImgMapper;
    /**
     * 公共库信息表
     */
    @Autowired
    private SharingFaceMapper sharingFaceMapperr;

    /**
     * 公共库信息与图片对应表
     */
    @Autowired
    private SharingFaceImgMapper sharingFaceImgMapper;

    /**
     * @descrption 插入单张主题模块人脸库信息以及与图片路径的对应信息
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param faceInfo 参数
     * @param inputFrom 参数
     * @throws RuntimeException 运行时异常
     * @return Result<String>
     */

    public Result<String> save(Face faceInfo, String inputFrom)
        throws RuntimeException {
        logger.info("[save] | 新增人脸信息~");
        if (null == faceInfo || faceInfo.getSubjectId().longValue() == 0) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        if (null == faceInfo.getCreateTime()
                || "".equals(faceInfo.getCreateTime())) {
            faceInfo.setCreateTime(DateUtil.date2Str(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
        }
        faceInfo.setUpdateTime(DateUtil.date2Str(new Date(),
                "yyyy-MM-dd HH:mm:ss"));
        faceInfo.setActionFlag(Constant.DefaultValue.ACTION_INSERT);
        faceInfo.setInputFrom(inputFrom);
        try {
            trimFaceProps(faceInfo);
            faceMapper.save(faceInfo);
            subjectMapper.timeUpdate(faceInfo.getSubjectId(),
                    DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Long id = faceInfo.getId();
            FaceImg faceImg = new FaceImg();
            List<String> imgPathList = faceInfo.getImgPath();
            if (null != imgPathList) {
                for (String imgPath : imgPathList) {
                    faceImg.setFaceId(id);
                    faceImg.setImgPath(imgPath);
                    faceImgMapper.save(faceImg);
                }
                return new Result<String>(MsgKey.SUCCESS_OPERATION,
                        String.valueOf(faceInfo.getId()));
            } else {
                logger.error("[saveFaceInfo] | 此人脸信息无对应图像");
                throw new ServiceException();
            }
        } catch (Exception e) {
            logger.error("[saveFaceInfo] | 添加人脸信息时运行出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * @descrption 新增单张图片信息
     * @author haihu
     * @create 2015年9月8日下午3:25:50
     * @version 1.0
     * @param faceImg 参数
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> addImg(FaceImg faceImg) throws RuntimeException {
        logger.info("[addImg] | 新增单张人脸照片信息~");
        if (null == faceImg || faceImg.getFaceId().longValue() == 0) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            faceImgMapper.save(faceImg);
        } catch (Exception e) {
            logger.error("[addImg] | 新增单张人脸照片信息异常", e);
            return new Result<String>(MsgKey.ERROR_OPERATION);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 删除单张照片信息
     * @author haihu
     * @create 2015年9月8日下午3:24:56
     * @version 1.0
     * @param imgPath 图片路径
     * @return Result<String>
     */
    public Result<String> delImg(String imgPath) {
        logger.info("[delImg] | 删除单张照片信息~");
        if (null == imgPath || "".equals(imgPath)) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            faceImgMapper.deleteByImgPath(imgPath);
        } catch (Exception e) {
            logger.error("[delImg] | 删除单张照片信息", e);
            return new Result<String>(MsgKey.ERROR_OPERATION);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 通过主题编号复制对应的与会人员信息，包括头像
     * @author admin
     * @create 2015年8月18日上午9:49:27
     * @version 1.0
     * @param oldId 旧编号
     * @param newId 新编号
     * @throws RuntimeException 运行时异常
     */
    public void copyBySubjectId(Long oldId, Long newId) throws RuntimeException {
        try {
            List<Face> faceList = faceMapper.findAllBySubId(oldId);
            for (Face face : faceList) {
                face.setSubjectId(newId);
                this.save(face, Constant.DefaultValue.FROM_USER);
            }
        } catch (Exception e) {
            logger.error("[saveFaceInfo] | 添加人脸信息时运行出错", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 去除主题库人员属性值两端的空格
     * 
     * @author: haihu 2015年7月21日上午11:08:18
     * @version 1.0
     * @param face 参数
     */
    private void trimFaceProps(Face face) {
        String name = face.getName();
        name = StringUtils.isEmpty(name) ? name : name.trim();
        String gender = face.getGender();
        gender = StringUtils.isEmpty(gender) ? gender : gender.trim();
        String job = face.getJob();
        job = StringUtils.isEmpty(job) ? job : job.trim();
        String department = face.getDepartment();
        department = StringUtils.isEmpty(department) ? department : department
                .trim();
        String mobile = face.getMobile();
        mobile = StringUtils.isEmpty(mobile) ? mobile : mobile.trim();
        face.setName(name);
        face.setGender(gender);
        face.setJob(job);
        face.setDepartment(department);
        face.setMobile(mobile);
    }

    /**
     * @descrption 批量删除人脸库信息 (逻辑删除)
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param  ids 删除数组
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> deleteByIds(Long[] ids) throws RuntimeException {
        logger.info("[deleteById] | 删除指定人脸信息~");
        for (Long id : ids) {
            if (NumberUtils.isNullOfId(id)) {
                return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
            }
            // 逻辑删除人脸库信息，将删除标记修改,同时写入更新时间
            try {
                faceMapper.updateDelStatus(id,
                        DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                logger.error("[deleteById]|通过主键删除人脸信息出错", e);
                throw new ServiceException(e);
            }
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 更新单条主题库信息
     * @author haihu
     * @create 2015年7月29日上午11:08:18
     * @version 1.0
     * @param  face 参数
     * @throws RuntimeException 运行时异常
     * @return Result<String>
     */
    public Result<String> update(Face face) throws RuntimeException {
        logger.info("[update] | 更新主题库信息~");
        if (null == face) {
            return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        try {
            Long id = face.getId();
            List<FaceImg> faceImgList = Lists.newArrayList();
            // List<FaceImg> faceImgList = new ArrayList<FaceImg>();
            List<String> imgPathList = face.getImgPath();
            face.setActionFlag(Constant.DefaultValue.ACTION_EDIT);
            face.setUpdateTime(DateUtil.date2Str(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            // 更新单条主题库信息
            if (!NumberUtils.isNullOfId(id)) {
                faceMapper.update(face);
                // 把人员id对应的图片删除，再重新插入编辑后的图片
                faceImgMapper.deleteByFaceId(id);
                subjectMapper.timeUpdate(face.getSubjectId(),
                        DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if (null != imgPathList) {
                    for (String imgPath : imgPathList) {
                        FaceImg faceImg = new FaceImg();
                        faceImg.setFaceId(id);
                        faceImg.setImgPath(imgPath);
                        faceImgList.add(faceImg);
                    }
                    faceImgMapper.saveBatch(faceImgList);
                } else {
                    logger.error("[update] | 此人脸信息无对应图像");
                    throw new ServiceException();
                }
            } else {
                logger.error("[update] | 无法获取对应信息ID");
                return new Result<String>(MsgKey.ERROR_PARAM_ILLEGAL);
            }
            // 查看此数据在共享库中是否存在
            Long sharingId = faceMapper.findSharingIdById(face.getId());
            List<SharingFaceImg> sharingFaceImgList = Lists.newArrayList();
            if (sharingId != null) {
                // 存在，则更新共享库人员信息(包括图片路径信息)
                SharingFace sharingFace = transferToSharingFace(face);
                sharingFace.setId(sharingId);
                sharingFaceMapperr.update(sharingFace);
                SharingFaceImg sharingFaceImg = new SharingFaceImg();
                sharingFaceImgMapper.deleteBySharingFaceId(sharingId);
                for (String imgPath : sharingFace.getImgPath()) {
                    sharingFaceImg.setSharingFaceId(sharingFace.getId());
                    sharingFaceImg.setImgPath(imgPath);
                    sharingFaceImgList.add(sharingFaceImg);
                    sharingFaceImgMapper.save(sharingFaceImg);
                }
                // sharingFaceImgMapper.saveBatch(sharingFaceImgList);
            }
        } catch (Exception e) {
            logger.error("[update] | 更新共享库信息时运行出错", e);
            throw new ServiceException(e);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION, String.valueOf(face
                .getId()));
    }

    /**
     * 将主题人员对象转换成公共库类型
     * 
     * @author: haihu
     * @createTime: 2015年7月29日上午11:08:18
     * @param face 参数
     * @return SharingFace
     */
    public static SharingFace transferToSharingFace(Face face) {
        SharingFace sharingFace = new SharingFace();
        sharingFace.setName(face.getName());
        sharingFace.setGender(face.getGender());
        sharingFace.setJob(face.getJob());
        sharingFace.setDepartment(face.getDepartment());
        sharingFace.setImgPath(face.getImgPath());
        sharingFace.setMobile(face.getMobile());
        return sharingFace;
    }

    /**
     * @descrption 批量插入人脸信息入库
     * @author haihu
     * @create 2015年7月1日上午11:08:18
     * @version 1.0
     * @param folderPath 文件夹路径
     * @param subjectId 主题ID
     * @return Result<List<Face>>
     */
    public Result<List<Face>> addFaceInfoList(String folderPath, Long subjectId) {
        logger.info("[addFaceInfoList] | 批量插入人脸信息~");
        // 组件完整的faceInfo信息
        List<Face> faceInfoList = Lists.newArrayList();
        File unzipFolder = new File(folderPath);
        List<Face> errorList = buildFacesInfo(faceInfoList, unzipFolder,
                subjectId);
        if (!faceInfoList.isEmpty()) {
            for (Face face : faceInfoList) {
                save(face, Constant.DefaultValue.FROM_USER);
            }
        } else {
            return new Result<List<Face>>(MsgKey.ERROR_PARAM_ILLEGAL);
        }
        if (!errorList.isEmpty()) {
            // 本地生成错误列表
            File tempFolder = new File(BASE_PATH + TEMPLATE_PATH);
            if (!tempFolder.exists()) {
                tempFolder.mkdirs();
            }
            File tempFile = new File(tempFolder.getPath() + File.separator
                    + UUID.randomUUID() + Constant.DefaultValue.EXCEL_SUFFIX);
            try {
                if (!tempFile.exists()) {
                    tempFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(tempFile);
                    FaceExcel.exportErrorList(errorList, fos);
                }
            } catch (Exception e) {
                logger.error("[addFaceInfoList] | 新增用户列表出错 ", e);
            }
            return new Result<List<Face>>(tempFile.getPath(), errorList);
        } else {
            return new Result<List<Face>>(MsgKey.SUCCESS_OPERATION);
        }
    }

    /**
     * @descrption 从公共库向主题库导入
     * @author admin
     * @create 2015年8月4日下午1:51:25
     * @version 1.0
     * @param subjectId 主题ID
     * @param sharingIds 公共库人员ID
     * @return Result<String>
     */
    public Result<String> addFromSharing(Long subjectId, Long[] sharingIds) {
        List<SharingFace> sharingFaces = sharingFaceMapperr
                .findByIds(sharingIds);
        for (SharingFace sharingFace : sharingFaces) {
            Face face = SharingServiceImpl.transferToFaceInfo(sharingFace);
            face.setSubjectId(subjectId);
            face.setSharingId(sharingFace.getId());
            this.save(face, Constant.DefaultValue.FROM_SHARING);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 检测用户是否存在
     * @author haihu
     * @create 2015年8月4日下午2:55:13
     * @version 1.0
     * @param name 姓名
     * @param subjectId 主题ID
     * @param department 单位
     * @return 若存在此用户则返回success.user.isexist标示
     */
    public Result<String> isUserAvailable(String name, String department,
            Long subjectId) {
        logger.info("[isUserAvailable] | 检测用户是否存在 ");
        try {
            int result = faceMapper.isUserExist(name, department, subjectId);
            if (result > 0) {
                return new Result<String>(MsgKey.USER_IS_EXIST);
            }
        } catch (Exception e) {
            logger.error("[isUserAvailable] | 检测用户是否存在出错 ");
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 上传批量信息压缩包
     * @author haihu
     * @create 2015年8月13日上午10:48:05
     * @version 1.0
     * @param request 请求参数
     * @param subjectId 主题ID
     * @return Result<List<Face>>
     */
    public Result<List<Face>> uploadZip(MultipartHttpServletRequest request,
            Long subjectId) {
        logger.info("[uploadZip] | 上传压缩文件 ");
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        File baseFolder = new File(BASE_PATH + TEMPLATE_PATH);
        String suffix = "";
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            suffix = FileUtils.getFileSuffix(mpf.getOriginalFilename());
            String desFileName = baseFolder.getPath() + File.separator
                    + mpf.getOriginalFilename();
            if (!".zip".equals(suffix)) {
                return new Result<List<Face>>(MsgKey.ERROR_UPLOAD_TYPE_ILLEGAL);
            }
            try {
                if (!baseFolder.exists()) {
                    baseFolder.mkdir();
                }
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
                        desFileName));
                desFileName = FileUtils.extractZip(desFileName);
                return addFaceInfoList(desFileName, subjectId);
            } catch (IOException e) {
                logger.error("[uploadZip] | 上传文件异常");
                e.printStackTrace();
                return new Result<List<Face>>(MsgKey.ERROR_OPERATION, null);
            }
        }
        return new Result<List<Face>>(MsgKey.SUCCESS_OPERATION, null);
    }

    /**
     * @descrption 接收上传图片，返回本地存放图片的路径
     * @author haihu
     * @create 2015年7月21日上午11:08:18
     * @version 1.0
     * @param request 请求参数
     * @param response 响应参数
     * @return Result<String>
     */
    public Result<String> uploadImg(MultipartHttpServletRequest request,
            HttpServletResponse response) {
        logger.info("[uploadImg] | 上传文件 ");
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
                logger.info("[uploadImg] | 调用图片引擎对上传的图片做可用性检测");
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
     * @descrption 下载批量导入错误的人脸信息列表
     * @author haihu
     * @create 2015年7月16日下午4:51:51
     * @version 1.0
     * @param response 响应参数
     * @param filePath 文件路径
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> downloadErrorFaceInfos(HttpServletResponse response,
            String filePath) throws RuntimeException {
        logger.debug("[downloadErrorFaceInfos] |　下载批量导入出错的人脸库数据");
        try {
            // 读到流中,// 文件的存放路径
            InputStream inStream = new FileInputStream(filePath);
            // 设置输出的格式
            response.reset();
            ResponseUtil.downloadResponseHeader(response,
                    URLEncoder.encode(EXCEL_NAME, Constant.DefaultValue.UTF8)
                            + Constant.DefaultValue.EXCEL_SUFFIX);
            // 循环取出流中的数据
            byte[] b = new byte[BYTE_SIZE];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            logger.error("[downloadErrorFaceInfos] 下载错误列表异常", e);
            return new Result<String>(MsgKey.ERROR_OPERATION);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    /**
     * @descrption 下载与会人员信息模板
     * @author admin
     * @create 2015年8月6日下午3:11:06
     * @version 1.0
     * @param response 参数
     * @return Result<String>
     * @throws RuntimeException 运行时异常
     */
    public Result<String> downloadTemplate(HttpServletResponse response)
        throws RuntimeException {
        logger.info("[downloadTemplate] | 下载模板文件 ");
        File tempFolder = new File(BASE_PATH + TEMPLATE_PATH);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        File tempFile = new File(tempFolder.getPath() + File.separator
                + EXCEL_NAME + Constant.DefaultValue.EXCEL_SUFFIX);
        try {
            if (!tempFile.exists()) {
                tempFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(tempFile);
                FaceExcel.exportTemplate(fos);
            }
            // 读到流中，文件的存放路径
            InputStream inStream = new FileInputStream(tempFile.getPath());
            // 设置输出的格式
            response.reset();
            ResponseUtil.downloadResponseHeader(response,
                    URLEncoder.encode(EXCEL_NAME, Constant.DefaultValue.UTF8)
                            + Constant.DefaultValue.EXCEL_SUFFIX);
            // 循环取出流中的数据
            byte[] b = new byte[BYTE_SIZE];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            logger.error("[downloadTemplate] 下载模板文件 异常", e);
            return new Result<String>(MsgKey.ERROR_OPERATION);
        }
        return new Result<String>(MsgKey.SUCCESS_OPERATION);
    }

    // /**
    // * @author: haihu
    // * @createTime: 2015年7月15日 上午9:27:45
    // * @description:下载人脸库数据
    // * @param response
    // * 响应
    // * @param pager
    // * 下载条数
    // * @param userModel
    // * 搜索条件
    // * @param appableCodes
    // * 导出内容选择 void
    // */
    // public Result<Face> downloadFaceInfo(HttpServletResponse response,
    // Pager<Face> pager, Face face) throws RuntimeException {
    // logger.debug("[downloadFaceInfo] |　下载人脸库数据");
    // ZipOutputStream zos = null;
    // try {
    // Result<Pager<Face>> result = validatePagerParam(pager, face,
    // pager.getPageSize());
    // if (!result.isStatus()) {
    // return new Result<Face>(MsgKey.ERROR_PARAM_ILLEGAL);
    // }
    // if (SessionHander.getAccountName() == null) {
    // return new Result<Face>(MsgKey.ERROR_NO_PERMISSION);
    // }
    // List<Face> faceInfoList = faceMapper.findAll();
    // if (EmptyUtils.isEmpty(faceInfoList)) {
    // return new Result<Face>(MsgKey.ERROR_GET_EXCEL_DATA);
    // }
    // ResponseUtil.downloadResponseHeader(response,
    // URLEncoder.encode("人脸库数据列表", "UTF-8")
    // + Constant.DefaultValue.RAR_SUFFIX);
    // zos = new ZipOutputStream(response.getOutputStream());
    // zos.setEncoding("gbk");
    // FaceExcel.exportFaceList(faceInfoList, zos);
    // zos.flush();
    // return new Result<Face>(MsgKey.SUCCESS_OPERATION);
    // } catch (Exception e) {
    // logger.error("[downloadFaceInfo] 下载人脸库数据 异常", e);
    // throw new ServiceException(e);
    // } finally {
    // try {
    // if (zos != null) {
    // zos.close();
    // }
    // } catch (IOException e) {
    // logger.error("[downloadFaceInfo] 下载人脸库数据 异常", e);
    // throw new ServiceException(e);
    // }
    // }
    // }

    /**
     * @descrption 从excel中获取数据并构建Face实例,如果出现有误信息(这里只检测人脸是否符合)，则返回错误信息列表
     * @author haihu
     * @create 2015年7月2日下午2:19:41
     * @version 1.0
     * @param faceList 参数
     * @param srcPath 参数
     * @param subjectId 参数
     * @return 返回错误信息的列表
     * @throws IOException
     */
    private List<Face> buildFacesInfo(List<Face> faceList, File srcPath,
            Long subjectId) {
        logger.info("[buildFacesInfo] | 构建Face实例");
        boolean availFlag = true;
        // 判断当天的文件夹是否存在，不存在则新建文件夹
        File imgFolderPath = new File(BASE_PATH
                + DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
        List<Face> errorList = new ArrayList<Face>();
        List<ExcelData> result = FileUtils.getExcelData(srcPath.getPath()
                + File.separator + EXCEL_NAME
                + Constant.DefaultValue.EXCEL_SUFFIX);
        if (null == result || result.isEmpty()) {
            logger.error("[buildFacesInfo] | 获取excel中的数据异常！");
            return null;
        } else {
            if (!imgFolderPath.exists()) {
                imgFolderPath.mkdir();
            }
            for (ExcelData data : result) {
                List<String> imgPathList = new ArrayList<String>();
                // if (!data.isAvailableFlag()) {
                // availFlag = false;
                // }
                if (listFaceImgPath(imgPathList, srcPath.getPath(),
                        imgFolderPath, data.getCol0()) == -1) {
                    availFlag = false;
                }
                Face face = new Face();
                face.setInputId(data.getCol0());
                face.setImgPath(imgPathList);
                face.setName(data.getCol1());
                face.setGender(data.getCol2());
                face.setJob(data.getCol3());
                face.setDepartment(data.getCol4());
                face.setMobile(data.getCol5());
                face.setCreateTime(DateUtil.date2Str(new Date(),
                        "yyyy-MM-dd HH:mm:ss"));
                // 操作标记 0：编辑；1：插入；-1：删除
                face.setActionFlag(Constant.DefaultValue.ACTION_INSERT);
                face.setSubjectId(subjectId);
                // 图片检测人脸不符合规则，增加到错误列表中
                if (!availFlag) {
                    errorList.add(face);
                    availFlag = true;
                } else {
                    faceList.add(face);
                }
            }
        }
        // 处理完毕，将上传解压后的文件夹删除
        try {
            FileUtils.clearFolder(BASE_PATH + TEMPLATE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorList;
    }

    /**
     * @author haihu
     * @create 2015年7月2日 下午5:16:42
     * @version 1.0
     * @description 照片文件夹过滤工具类
     */
    static class MyFilter implements FilenameFilter {
        
        /**
         * 前缀
         */
        private String prefix;

        public MyFilter(String prefix) {
            this.prefix = prefix;
        }

        /**
         * @author haihu
         * @create 2015年7月2日 下午5:16:42
         * @version 1.0
         * @param dir 路径
         * @param name 名称
         * @return boolean
         * @description 照片文件夹过滤
         */
        public boolean accept(File dir, String name) {
            if (name.startsWith(prefix) || name.startsWith(prefix + "-")) {
                return true;
            }
            return false;
        }
    }

    /**
     * @descrption 获取指定人脸对应的图片路径集合
     * @author haihu
     * @create 2015年7月2日下午4:57:39
     * @version 1.0
     * @param imgPathList
     *            图片路径列表(作为返回值)
     * @param srcPath
     *            (解压之后的源路径)
     * @param desPath
     *            (解析对应关系之后的目标路径)
     * @param id
     *            (xls表中的行数)
     * @return int
     * @exception RuntimeException 运行时异常
     */
    private int listFaceImgPath(List<String> imgPathList, String srcPath,
            File desPath, String id) throws RuntimeException {
        logger.info("[listFaceImgPath] | 获取指定人脸对应的图片路径");
        File fileFolder = new File(srcPath);
        MyFilter filter = new MyFilter(id);
        int ret = 0;
        try {
            File[] files = fileFolder.listFiles(filter);
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String suffix = FileUtils.getFileSuffix(file);
                    File desFile = new File(desPath.getPath() + File.separator
                            + UUID.randomUUID().toString() + suffix);
                    file.renameTo(desFile);
                    // 人脸检测引擎检测图片可用性
                    if (!IFAUtils.getInstance().isImgAvailable(
                            desFile.getPath())) {
                        imgPathList.add("");
                        return -1;
                    }
                    // 将目的图片的地址添加进list中，同时删除原图片文件
                    imgPathList.add(UPLOAD_PATH
                            + DateUtil.date2Str(new Date(), "yyyy-MM-dd")
                            + File.separator + desFile.getName());
                    FileUtils.delFile(file.getPath());
                }
            } else {
                logger.error("[listFaceImgPath] | 文件夹中无id对应的有效图片");
                return -1;
            }
        } catch (Exception e) {
            logger.error("[listFaceImgPath] | 列出人脸图片路径时运行出错", e);
            throw new ServiceException(e);
        }
        return ret;
    }

}
