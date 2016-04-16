package com.iflytek.engine;


/***
 * must do not modify anywhere
 * @author jjzhuang
 * 2015/07/13
 */

public class IFA {

    public long         mInst =  0 ;
    public int          mModelSize=  0;
    public byte[]       mModel;
    public String       mResult;
    
    
    public final static int     IFA_RESULT_NOT_BEGIN = 0;
    public final static int     IFA_RESULT_COMPUTING = 1;
    public final static int     IFA_RESULT_COMPLETE = 2;
    public final static int     IFA_RESULT_TIMEOUT = 3;
    public final static int     IFA_RESULT_STOP = 4;
    //private static String JniIFA_PATH = "F:\\runtime\\IFA\\bin\\ifa.dll";
    
    
    static{
        String str = System.getProperty("java.library.path");
        System.out.println("java.library.path = "+str);
       
        System.loadLibrary("ifa_jni");
//        int err = 0;
//        err = IFA.JniIFAInitialize(JniIFA_PATH, null);
//        System.out.println(err);
    }
    
    
    public native static  int  JniIFAInitialize(String engine, byte[] reserved);
    
    public native static  int  JniIFAUninitialize();
    
    public native  int  JniIFACreateInst(String params);
    
    public native  int  JniIFASessionBegin(String channel, String params, byte[] model_data, int[] model_size,  int model_cnt);
    
    public native  int  JniIFAImageWrite(String img_type, byte[] data, int len_bytes, int[] status);
    
    public native  int  JniIFAGetResult(int timeout_ms, int[] status);
    
    public native  int  JniIFASessionEnd();
    
    public native  int  JniIFADestroyInst();
    
    public native  int  JniIFASetParam(String param, String value);
    
    public native  int  JniIFAGetParam(String param, byte[] value, int[] len);
    
    
    
}
