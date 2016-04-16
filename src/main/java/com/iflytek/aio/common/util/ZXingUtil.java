package com.iflytek.aio.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.iflytek.aio.common.Constant;

/**
 * 
 * @author admin
 *
 * @create 2015年8月3日 上午10:51:15
 *
 * @version 1.0
 * 
 * @description 二维码生成工具类
 *
 */
public class ZXingUtil {

    public static String encode(String contents, String folderPath) {
        HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        
        //指定纠错级别(L--7%,M--15%,Q--25%,H--30%)  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");

        BitMatrix matrix = null;

        try {
            matrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.QR_CODE, 300, 300, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        String qrcodeName = UUID.randomUUID() + Constant.DefaultValue.IMG_SUFFIX_PNG;
        File file = new File(folderPath + File.separator + qrcodeName);
        try {
            MatrixToImageWriter.writeToPath(matrix,
                    Constant.DefaultValue.PNG_TYPE, file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qrcodeName;
    }

    public static void main(String[] args) {
    }
}
