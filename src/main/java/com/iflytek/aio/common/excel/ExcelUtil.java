package com.iflytek.aio.common.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

public  class ExcelUtil {

    public static void setCell(HSSFRow row, int cellNum, String cellValue,
            CellStyle style) {
        HSSFCell createCell = row.createCell(cellNum);
        createCell.setCellValue(cellValue);
        createCell.setCellStyle(style);
    }
    
    public static void setCell(HSSFRow row, int cellNum, int cellValue,
            CellStyle style) {
        HSSFCell createCell = row.createCell(cellNum);
        createCell.setCellValue(cellValue);
        createCell.setCellStyle(style);
    }

    public static CellStyle headerStyle(HSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return style;
    }

    public static CellStyle contentStyle(HSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return style;
    }
}
