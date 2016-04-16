package com.iflytek.aio.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.eclipse.jdt.core.dom.ThisExpression;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.entity.ExcelData;

/**
 * *****************************************************************************
 * Copyrights @ 2014，Iflytek Information Co., Ltd All rights reserved.
 * Filename：FileUtils Description：文件工具类 Entity Author: ycli5
 * Finished：2014年12月12日 下午4:17:42
 * ****************************************************************************
 */
public class FileUtils {

    /**
     * 日志记录器
     */
    private static LoggerUtils logger = LoggerUtils.getLogger(FileUtils.class);

    private static int BUF_SIZE = 1024;
    // 本地存储基地址
    private static String BASE_PATH = ConfigRead.newInstance().readValue(
            Constant.BASE_PATH);

    private static String TEMPLATE_PATH = ConfigRead.newInstance().readValue(
            Constant.TEMPLATE_PATH);

    /**
     * @author: ycli5
     * @createTime: 2014年12月12日 下午4:21:40
     * @description: 复制文件夹
     * @param sourceDir
     *            原文件夹
     * @param targetDir
     *            目标文件夹
     * @throws IOException
     *             void
     */
    public static void copyDirectiory(String sourceDir, String targetDir)
        throws IOException {
        File targetDirFile = new File(targetDir);
        if (!targetDirFile.exists()) {
            targetDirFile.mkdirs();
        }
        File[] file = new File(sourceDir).listFiles();
        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    File sourceFile = file[i];
                    File targetFile = new File(targetDirFile.getAbsolutePath()
                            + File.separator + file[i].getName());
                    copyFile(sourceFile, targetFile);
                }
                if (file[i].isDirectory()) {
                    String dir1 = sourceDir + "/" + file[i].getName();
                    String dir2 = targetDir + "/" + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }
        }
    }

    /**
     * @descrption 获取文件的后缀名，也即类型，如demo.txt,则返回 ".txt"
     * @author haihu
     * @create 2015年7月2日下午5:06:02
     * @version 1.0
     * @param file
     * @return
     */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        return prefix;
    }

    public static String getFileSuffix(String fileName) {
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        return prefix;
    }

    /**
     * @author: ycli5
     * @createTime: 2014年12月12日 下午4:24:36
     * @description: 复制文件
     * @param sourceFile
     *            原文件
     * @param targetFile
     *            目标文件
     * @throws IOException
     *             void
     */
    public static void copyFile(File sourceFile, File targetFile)
        throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] b = new byte[BUF_SIZE * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 
     * @author: ycli5
     * @createTime: 2014年7月24日 上午11:19:55
     * @description: 删除文件夹下所有文件
     * @param path
     *            文件夹路径 绝对路径
     * @return boolean
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        file.delete();
        return flag;
    }

    /**
     * @author: ycli5
     * @createTime: 2014年12月15日 上午10:58:56
     * @description: 文件打包
     * @param out
     *            zip流
     * @param f
     *            文件
     * @param base
     *            基础名称
     * @throws Exception
     *             void
     */
    public static void zipFile(ZipOutputStream out, File f, String base)
        throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zipFile(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b = -1;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    /**
     * @descrption 解压打包文件
     * @author haihu
     * @create 2015年7月2日上午9:29:27
     * @version 1.0
     * @param unZipfileName
     * @throws IOException
     */
    public static String extractZip(String zipfileName) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = null;
        String unzipfileName = "";
        InputStream inputStream = null;
        // 这里需要注意，GBK只是在windows机器上测试使用，在linux上是不需要这个设置，直接默认使用UTF-8
        ZipFile zipFile = new ZipFile(zipfileName, "GBK");
        try {
            // ZipFile zipFile = new ZipFile(unZipfileName);
            for (Enumeration entries = zipFile.getEntries(); entries
                    .hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                file = new File(BASE_PATH + TEMPLATE_PATH, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                    unzipfileName = file.getPath();
                } else {
                    // 如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    inputStream = zipFile.getInputStream(entry);
                    byte[] buf = new byte[BUF_SIZE];
                    int readedBytes = 0;
                    fileOutputStream = new FileOutputStream(file);
                    while ((readedBytes = inputStream.read(buf)) > 0) {
                        fileOutputStream.write(buf, 0, readedBytes);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                }
            }
            zipFile.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return unzipfileName;
    }

    /**
     * 
     * @author: ycli5
     * @createTime: 2014年7月25日 下午3:42:54
     * @description: 删除文件
     * @param filepath
     *            文件路径 void
     */
    public static void delFile(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 
     * @author: haihu
     * @createTime: 2015年7月15日 下午3:42:54
     * @description: 删除文件夾以及其下的所有文件及文件夾
     * @param filepath
     *            文件路径 void
     */
    public static void delFolder(String folderPath) throws IOException {
        File f = new File(folderPath);// 定义文件路径
        try {
            if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
                if (f.listFiles().length > 0) {// 若目录下没有文件则直接删除
                    // 若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            delFolder(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                        }
                        delFile[j].delete();
                    }
                }
                f.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @author: haihu
     * @createTime: 2015年8月15日 下午3:42:54
     * @description: 清空制定文件夾
     * @param filepath
     *            文件路径 void
     */
    public static void clearFolder(String dir) {
        File delfolder = new File(dir);
        File oldFile[] = delfolder.listFiles();
        try {
            for (int i = 0; i < oldFile.length; i++) {
                if (oldFile[i].isDirectory()) {
                    clearFolder(dir + File.separator + oldFile[i].getName()
                            + File.separator); // 递归清空子文件夹
                }
                oldFile[i].delete();
            }
        } catch (Exception e) {
            System.out.println("清空文件夹操作出错!");
            e.printStackTrace();
        }
    }

    /**
     * @descrption 获取excel数据
     * @author haihu
     * @create 2015年7月2日下午3:00:35
     * @version 1.0
     * @return
     */
    public static List<ExcelData> getExcelData(String xlsName) {
        logger.info("[getExcelData] |　", "获取excel数据");
        List<ExcelData> result = null;
        try {
            result = ReadExcel.readExcelData(new File(xlsName));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[getExcelData] |　", "读取excel数据异常");
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        // String fileName = "测试";
        // //extractZip("F:"+File.separator+fileName);
        // String xls = "F:"+File.separator+fileName+File.separator+"胡海.xls";
        //
        // List<List<Object>> result = ReadExcel.readExcel(new File(xls));
        // System.out.println(result.toString());
        File file = new File("F:" + File.separator + "aio" + File.separator
                + "template");
        clearFolder("F:" + File.separator + "aio" + File.separator + "template"
                + File.separator);
    }
}
