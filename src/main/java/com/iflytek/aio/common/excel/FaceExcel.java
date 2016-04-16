package com.iflytek.aio.common.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.iflytek.aio.entity.Face;

/**
 * <pre>
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：FaceExcel
 * Description：人脸信息excel制作
 * Entity Author: ycli5
 * Finished：2015年4月27日 上午11:52:14
 * ****************************************************************************
 * </pre>
 */
public class FaceExcel {
    /**
     * @author: haihu
     * @createTime: 2015年4月27日 上午11:53:36
     * @description:制作excel入口
     * @param faceList
     *            人脸信息集合
     * @param zos
     *            压缩流 void
     * @throws IOException
     */
//    public static void exportFaceList(List<Face> faceList, ZipOutputStream zos)
//        throws IOException {
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet createSheet = wb.createSheet("人脸信息列表");
//        createSheet.setDefaultColumnWidth(30);
//        // 标题制作
//        createHeader(headerStyle(wb), createSheet);
//
//        // 内容制作
//        contentSheet(createSheet, faceList, zos, contentStyle(wb));
//
//        // FileOutputStream fOut = new FileOutputStream("F:/zip/人脸信息.xls");
//        // wb.write(fOut);
//        // fOut.flush();
//        // fOut.close();
//        zos.putNextEntry(new ZipEntry("人脸信息列表"
//                + DateUtil.date2Str(new Date(), "yyyyMMdd")
//                + Constant.DefaultValue.EXCEL_SUFFIX));
//        wb.write(zos);
//    }

    /**
     * @author: haihu
     * @createTime: 2015年4月27日 上午11:53:36
     * @description:制作excel模板
     * @param zos
     *            压缩流 void
     * @throws IOException
     */
    public static void exportTemplate(FileOutputStream fOut) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet createSheet = wb.createSheet("与会人员信息列表");
        createSheet.setDefaultColumnWidth(30);
        // 标题制作
        createHeader(headerStyle(wb), createSheet);

         wb.write(fOut);
         fOut.flush();
         fOut.close();
    }

    /**
     * @author: haihu
     * @createTime: 2015年4月27日 上午11:53:36
     * @description:制作excel入口
     * @param faceList
     *            人脸信息集合
     * @param zos
     *            压缩流 void
     * @throws IOException
     */
    public static void exportErrorList(List<Face> faceList, FileOutputStream fOut)
        throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet createSheet = wb.createSheet("错误信息列表");
        createSheet.setDefaultColumnWidth(30);
        // 标题制作
        createHeader(headerStyle(wb), createSheet);

        // 内容制作
        contentSheet(createSheet, faceList, contentStyle(wb));

        wb.write(fOut);
        fOut.flush();
        fOut.close();
    }

    /**
     * @author: haihu
     * @createTime: 2015年47月15日 下午2:11:55
     * @description: 生成头样式
     * @param createSheet
     *            sheet
     * @param headerStyle
     *            样式 void
     */
    private static void createHeader(CellStyle headerStyle,
            HSSFSheet createSheet) {
        HSSFRow row = createSheet.createRow(0);
        getCell(row, 0, "人员编号", headerStyle);
        getCell(row, 1, "姓名", headerStyle);
        getCell(row, 2, "性别", headerStyle);
        getCell(row, 3, "职务", headerStyle);
        getCell(row, 4, "单位", headerStyle);
        getCell(row, 5, "手机号码", headerStyle);
    }

    /**
     * @author: haihu
     * @createTime: 2015年47月15日 下午2:11:55
     * @description: excel内容生成
     * @param createSheet
     *            sheet
     * @param userModelList
     *            数据集合
     * @param zos
     *            压缩流
     * @param contentStyle
     *            样式 void
     * @throws IOException
     */
    private static void contentSheet(HSSFSheet createSheet,
            List<Face> faceList, CellStyle contentStyle)
        throws IOException {
        int count = 0;
        for (Face face : faceList) {
            count++;
            HSSFRow row = createSheet.createRow(count);
            handleBasicData(face, row, contentStyle);
        }
    }

    /**
     * @author: haihu
     * @createTime: 2015年47月15日 下午2:11:55
     * @description: 处理基本数据信息
     * @param userModel
     *            用户模型信息
     * @param row
     *            行
     * @param contentStyle
     *            样式 void
     */
    private static void handleBasicData(Face face, HSSFRow row,
            CellStyle contentStyle) {
        getCell(row, 0, face.getInputId(), contentStyle);
        getCell(row, 1, face.getName(), contentStyle);
        getCell(row, 2, face.getGender(), contentStyle);
        getCell(row, 3, face.getJob(), contentStyle);
        getCell(row, 4, face.getDepartment(), contentStyle);
        getCell(row, 5, face.getMobile(), contentStyle);
    }

    /**
     * @author: haihu
     * @createTime: 2015年47月15日 下午2:11:55
     * @description: 制作表格
     * @param row
     *            行
     * @param cellNum
     *            单元格号
     * @param cellValue
     *            单元格值
     * @param style
     *            单元格样式 void
     */
    private static void getCell(HSSFRow row, int cellNum, String cellValue,
            CellStyle style) {

        HSSFCell createCell = row.createCell(cellNum);
        createCell.setCellValue(cellValue);
        createCell.setCellStyle(style);
    }

    /**
     * @author: haihu
     * @createTime: 2015年47月15日 下午2:11:55
     * @description:标题样式
     * @param wb
     *            excel
     * @return CellStyle
     */
    private static CellStyle headerStyle(HSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置边框颜色
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    /**
     * @author: haihu
     * @createTime: 2015年47月15日 下午2:11:55
     * @description:内容样式
     * @param wb
     *            excel
     * @return CellStyle
     */
    private static CellStyle contentStyle(HSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置边框颜色
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
    
    public static void main(String[] args) {

    }
}
