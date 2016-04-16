package com.iflytek.engine;

public class IFA_ERR {

    /* jni error */
    public final static int  FA_JNI_E_InvArg                     =  1001;
    public final static int  FA_JNI_E_NullPtr                    =  1002;
    public final static int  FA_JNI_E_FindLib                    =  1003;
    public final static int  FA_JNI_E_GetMethod                  =  1004;
    
    /* Generic error defines */
    public final static int  IFV_SUCCESS                          =  0;
    public final static int  IFV_DONE                             =  1;
    public final static int  IFV_ERROR_FAIL                       = -1;
    public final static int  IFV_ERROR_EXCEPTION                  = -2;

    /* Common errors */
    public final static int  IFV_ERROR_GENERAL                    = 20000; /* 0x4E20 Generic Error */
    public final static int  IFV_ERROR_OUT_OF_MEMORY              = 20001; /* 0x4E21 */
    public final static int  IFV_ERROR_FILE_NOT_FOUND             = 20002; /* 0x4E22 */
    public final static int  IFV_ERROR_NOT_SUPPORT                = 20003; /* 0x4E23 */
    public final static int  IFV_ERROR_NOT_IMPLEMENT              = 20004; /* 0x4E24 */
    public final static int  IFV_ERROR_ACCESS                     = 20005; /* 0x4E25 */
    public final static int  IFV_ERROR_INVALID_PARA               = 20006; /* 0x4E26 */
    public final static int  IFV_ERROR_INVALID_PARA_VALUE         = 20007; /* 0x4E27 */
    public final static int  IFV_ERROR_INVALID_HANDLE             = 20008; /* 0x4E28 */
    public final static int  IFV_ERROR_INVALID_DATA               = 20009; /* 0x4E29 */ /* 无效资源 */
    public final static int  IFV_ERROR_NO_LICENSE                 = 20010; /* 0x4E2A */
    public final static int  IFV_ERROR_NOT_INIT                   = 20011; /* 0x4E2B */
    public final static int  IFV_ERROR_NULL_HANDLE                = 20012; /* 0x4E2C */
    public final static int  IFV_ERROR_OVERFLOW                   = 20013; /* 0x4E2D */
    public final static int  IFV_ERROR_TIME_OUT                   = 20014; /* 0x4E2E */
    public final static int  IFV_ERROR_OPEN_FILE                  = 20015; /* 0x4E2F */
    public final static int  IFV_ERROR_NOT_FOUND                  = 20016; /* 0x4E30 */
    public final static int  IFV_ERROR_NO_ENOUGH_BUFFER           = 20017; /* 0x4E31 */
    public final static int  IFV_ERROR_NO_DATA                    = 20018; /* 0x4E32 */
    public final static int  IFV_ERROR_NEED_MORE_DATA             = 20019; /* 0x4E33 */
    public final static int  IFV_ERROR_RES_MISSING                = 20020; /* 0x4E34 */
    public final static int  IFV_ERROR_SKIPPED                    = 20021; /* 0x4E35 */
    public final static int  IFV_ERROR_ALREADY_EXIST              = 20022; /* 0x4E36 */
    public final static int  IFV_ERROR_LOAD_MODULE                = 20023; /* 0x4E37 */
    public final static int  IFV_ERROR_BUSY                       = 20024; /* 0x4E38 */
    public final static int  IFV_ERROR_INVALID_CONFIG             = 20025; /* 0x4E39 */
    public final static int  IFV_ERROR_VERSION_CHECK              = 20026; /* 0x4E3A */
    public final static int  IFV_ERROR_CANCELED                   = 20027; /* 0x4E3B */
    public final static int  IFV_ERROR_INVALID_MEDIA_TYPE         = 20028; /* 0x4E3C */
    public final static int  IFV_ERROR_NULL_PTR                   = 20029; /* 0x4E3D */
    public final static int  IFV_ERROR_NOT_BEGIN                  = 20030; /* 0x4E3E */
    public final static int  IFV_ERROR_NO_TASK                    = 20031; /* 0x4E3F */
    public final static int  IFV_ERROR_NOT_AUDIO_END              = 20032; /* 0x4E40 */
    public final static int  IFV_ERROR_INVALID_CALL               = 20033; /* 0x4E41 */
    public final static int  IFV_ERROR_GET_RESULT                 = 20034; /* 0x4E42 */
    public final static int  IFV_ERROR_NO_ENOUGH_IMAGE            = 20035; /* 0x4E43 */
    public final static int  IFV_ERROR_ENOUGH_IMAGE               = 20036; /* 0x4E44 */
    public final static int  IFV_ERROR_NO_MASK_ENOUGH             = 20037; 
    public final static int  IFV_ERRPR_NO_USE_MASK                = 20038;
    public final static int  IFV_ERRPR_VALID_MASK_DATA            = 20039;

    // face
    public final static int  IFV_ERROR_NOT_FACE_IMAGE             = 20200; /* 0x4EE8 */ /* 无人脸 */
    public final static int  IFV_ERROR_FACE_IMAGE_FULL_LEFT       = 20201; /* 0x4EE9 */ /* 向左 */
    public final static int  IFV_ERROR_FACE_IMAGE_FULL_RIGHT      = 20202; /* 0x4EEA */ /* 向右 */
    public final static int  IFV_ERROR_IMAGE_CLOCKWISE_WHIRL      = 20203; /* 0x4EEB */ /* 顺时针旋转 */
    public final static int  IFV_ERROR_IMAGE_COUNTET_CLOCKWISE_WHIRL      = 20204; /* 0x4EEC */ /* 逆时针旋转 */
    public final static int  IFV_ERROR_VALID_IMAGE_SIZE                   = 20205; /* 0x4EED */ 
    public final static int  IFV_ERROR_ILLUMINATION                       = 20206; /* 0x4EEE */ /* 光照异常 */
    public final static int  IFV_ERROR_FACE_OCCULTATION                   = 20207; /* 0x4EEF */ /* 人脸被遮挡 */
    public final static int  IFV_ERROR_FACE_INVALID_MODEL                 = 20208; /* 0x4EF0 */ /* 非法模型数据 */
    public final static int  IFV_ERROR_FACE_UPDATE_NOENOUGH               = 20209; /* 0x4EF1 */ /* 更新无法进行 */

    // fusion
    public final static int  IFV_ERROR_FUSION_INVALID_INPUT_TYPE    = 20300; /* 0x4F4C */ /* 输入数据类型非法 */
    public final static int  IFV_ERROR_FUSION_NO_ENOUGH_DATA        = 20301; /* 0x4F4D */
    public final static int  IFV_ERROR_FUSION_ENOUGH_DATA           = 20302; /* 0x4F4E */
}
