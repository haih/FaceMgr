package com.iflytek.aio.common.ifa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.util.ConfigRead;
import com.iflytek.aio.service.impl.FaceServiceImpl;
import com.iflytek.engine.IFA;

public class IFAUtils {
    private static Logger logger = Logger.getLogger(FaceServiceImpl.class);
    /**
     * 日志记录器
     */
    private final static String JniIFA_PATH = ConfigRead.newInstance()
            .readValue(Constant.JniIFA_PATH);
    // private static String JniIFA_PATH = "/home/aio/ifa_engine/bin/libifa.so";

    // 判断人脸是否可用的坐标差值门限
    private final static int FACE_THRESHOLD = Integer.parseInt(ConfigRead
            .newInstance().readValue(Constant.FACE_THRESHOLD));

    private static IFAUtils ifaUtils;

    // private static int write(String path,byte[] data)
    // {
    // FileOutputStream fout;
    // try {
    // fout = new FileOutputStream(path);
    // fout.write(data);
    // fout.close();
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // return -1;
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // return -1;
    // }
    // return 0;
    // }

    IFAUtils() {
        int err = 0;
        err = IFA.JniIFAInitialize(JniIFA_PATH, null);
        System.out.println(err);
    }

    /**
     * @descrption 实例化单例
     * @author haihu
     * @create 2015年7月15日上午11:08:08
     * @version 1.0
     * @return
     */
    public static IFAUtils getInstance() {
        if (null == ifaUtils) {
            return new IFAUtils();
        }
        return ifaUtils;
    }

    /**
     * 读取数据，返回byte[]
     * 
     * @return
     */
    private static byte[] readData(String filepath) {
        File regfile = new File(filepath);
        if (!regfile.exists()) {
            return null;
        }
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filepath);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String faceSizeCheck(File imgFile) {
        logger.info("[faceSizeCheck] | 人脸图片大小检测");
        int err = 0;
        IFA i = new IFA();
        int[] status = new int[2];
        status[0] = IFA.IFA_RESULT_NOT_BEGIN;
        err = i.JniIFACreateInst(null);
        if (0 != err) {
            logger.error("[faceSizeCheck] | JniIFACreateInst err = " + err);
        }
        byte[] data = readData(imgFile.getPath());
        try {
            err = i.JniIFASessionBegin("", "operation_type=detect,image_cnt=1",
                    null, null, 1);
            if (0 != err) {
                logger.error("[faceSizeCheck] | JniIFASessionBegin err = "
                        + err);
                return "";
            }
            err = i.JniIFAImageWrite("", data, data.length, null);
            if (0 != err) {
                logger.error("[faceSizeCheck] | JniIFAImageWrite err = " + err);
                return "";
            }
            err = i.JniIFAGetResult(1000, status);
            if (0 != err) {
                logger.error("[faceSizeCheck] | JniIFAGetResult err = " + err);
                return "";
            }
            // 输出json结果
            System.out.println(i.mResult);

            err = i.JniIFASessionEnd();
            if (0 != err) {
                logger.error("[faceSizeCheck] | JniIFASessionEnd err = " + err);
                return "";
            }
            err = i.JniIFADestroyInst();
            if (0 != err) {
                logger.error("[faceSizeCheck] | JniIFADestroyInst err = " + err);
            }
            // err = IFA.JniIFAUninitialize();
            // System.out.println(err);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "";
        }
        return i.mResult;
    }

    /**
     * @descrption 检测图片的可用性
     * @author haihu
     * @create 2015年7月14日下午2:54:08
     * @version 1.0
     * @param imgFile
     * @return 通过引擎检查图片中人脸的大小是否符合规定
     */
    public boolean isImgAvailable(String imgPath) {
        logger.info("[isImgAvailable] | 图片引擎提取可用性检测");
        File imgFile = new File(imgPath);
        String result = faceSizeCheck(imgFile);
        // 从结果中提取出识别的分数
        if (result == null || result == "") {
            logger.error("[isImgAvailable] | 图片引擎提取结果出错,错误类型为：" + result);
            return false;
        }
        // 对图片的四个坐标进行记录
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        try {
            JSONObject jsonObj = JSONObject.fromObject(result);
            JSONArray array = jsonObj.getJSONArray("face");
            // 图片中若存在多张人脸，此图片不可用，直接返回
            if (array.size() > 1) {
                logger.error("[isImgAvailable] | 图片中存在多张人脸，此图片不可用");
                return false;
            }
            Object o = array.get(0);
            jsonObj = JSONObject.fromObject(o);
            jsonObj = JSONObject.fromObject(jsonObj.getString("position"));
            left = jsonObj.getInt("left");
            top = jsonObj.getInt("top");
            right = jsonObj.getInt("right");
            bottom = jsonObj.getInt("bottom");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("[isImgAvailable] | 检测图片的可用性异常e:" + e);
            return false;
        }
        if ((bottom - top) > FACE_THRESHOLD && (right - left) > FACE_THRESHOLD) {
            logger.info("[isImgAvailable] | 图片引擎提取可用性检测结果为：可用！");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // // TODO Auto-generated method stub
        // int err = 0;
        //
        // err = IFA.JniIFAInitialize("/home/aio/ifa_engine/bin/libifa.so",
        // null);
        // System.out.println(err);
        //
        // IFA i = new IFA();
        // int[] status = new int[2];
        // status[0] = IFA.IFA_RESULT_NOT_BEGIN;
        // err = i.JniIFACreateInst(null);
        // System.out.println(err);
        // byte[] data =
        // readData("F:\\runtime\\IFA\\bin\\wfr277f1d52@ch5137080fdd27477700.jpg");
        // for (int j = 0; j < 3; j++) {
        // System.out.println("enroll : "+j);
        // //人脸检测
        // err = i.JniIFASessionBegin("", "operation_type=detect,image_cnt=1",
        // null, null, 1);
        // System.out.println(err);
        // err = i.JniIFAImageWrite("", data, data.length, null);
        // System.out.println(err);
        // err = i.JniIFAGetResult(1000, status);
        // System.out.println("JniIFAGetResult ret: "+err);
        // // //输出json结果
        // // System.out.println(i.mResult);
        // err = i.JniIFASessionEnd();
        // System.out.println(err);
        // }
        // err = i.JniIFADestroyInst();
        // System.out.println(err);
        // err = IFA.JniIFAUninitialize();
        // System.out.println(err);
        String result = "{\"sst\":\"detect\",\"rst\":\"success\",\"ret\":\"0\",\"face\":[{\"attribute\":{\"pose\":{\"pitch\":1}},\"position\":{\"left\":66,\"top\":198,\"right\":290,\"bottom\":422},\"confidence\":\"11.127522\",\"tag\":\"\"}]}";
        JSONObject jsonObj = JSONObject.fromObject(result);
        JSONArray array = jsonObj.getJSONArray("face");
        System.out.println(array.toString());
    }

}
