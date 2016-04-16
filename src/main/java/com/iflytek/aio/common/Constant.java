/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common;

/**
 * <pre>
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：Constant
 * Description：定义公共的常量
 * Entity Author: ycli5
 * Finished：2015年5月4日 下午4:25:14
 * ****************************************************************************
 * </pre>
 */
public class Constant {
	
	// SESSION KEY
	public static final String SESSION_KEY = "aio_session_key";
	
	// 当前的orgcode在session中的key
	public static final String CURRENT_ORGCODE_KEY = "aio_session_prensent_org_key";
	
	// 系统上下文
	public static final String CONTEXT_PATH = "aio.context.path";
	
	// 文件基础路径
    public static final String BASE_PATH = "aio.base.path";
    
    // 上传文件路径
    public static final String UPLOAD_PATH = "aio.upload.path";
    
    // 与会人员信息模板存放路径
    public static final String TEMPLATE_PATH = "aio.template.path";
    
    // 手机注册url前缀
    public static final String MOBILEREG_URI_PREFIX ="aio.mobilereg.prefix";
    
    // 手机注册url后缀
    public static final String MOBILEREG_URI_SUFFIX ="aio.mobilereg.suffix";

    // 人脸引擎存放路径
    public static final String JniIFA_PATH = "jni_ifa_path";
    
    // 人脸检测引擎门限值
    public static final String FACE_THRESHOLD = "face_threshold";
	/**
	 * <pre>
	 * *****************************************************************************
	 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：DictConfig
	 * Description：数据库字典配置信息
	 * Entity Author: ycli5
	 * Finished：2015年4月21日 下午2:44:12
	 * ****************************************************************************
	 * </pre>
	 */
	public interface DictConfig {
		/**
		 * Android code
		 */
		public static final String DICT_APPPLATFORM_ANDROID_CODE = "android";

		/**
		 * IOS code
		 */
		public static final String DICT_APPPLATFORM_IOS_CODE = "ios";

		/**
		 * 数据字典数据状态
		 */
		public static final String DICT_DATA_STATUS = "1";

		/**
		 * 数据字典应用能力 type key
		 */
		public static final String DICT_APPABLE_KEY = "appable";

		/**
		 * 数据字典应用平台 type key
		 */
		public static final String DICT_APPPLATFORM_KEY = "appPlatform";

		/**
		 * 数据字典用户模型状态 type key
		 */
		public static final String DICT_USERMODELSTATUS_KEY = "userModelStatus";

		/**
		 * 数据字典用户模型注册 人脸类型 code
		 */
		public static final String DICT_USERMODEL_APPABLE_FACE_CODE = "face";

		/**
		 * 数据字典用户模型注册 声纹类型 code
		 */
		public static final String DICT_USERMODEL_APPABLE_VOICE_CODE = "voice";

	}

	/**
	 * <pre>
	 * *****************************************************************************
	 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：DefaultValue
	 * Description：默认值设置
	 * Entity Author: ycli5
	 * Finished：2015年4月21日 上午10:39:47
	 * ****************************************************************************
	 * </pre>
	 */
	public interface DefaultValue {
		/**
		 * 人脸库分页条数
		 */
		public static final int SHARING_PAGER_SIZE = 10;

	    /**
         * 主题库分页条数
         */
        public static final int SUBJECT_PAGER_SIZE = 10;
      /**
       * 图片类型名
       */
      public static final String PNG_TYPE = "png";
		/**
		 * 图片后缀名
		 */
		public static final String IMG_SUFFIX = ".jpg";
        /**
         * 图片后缀名
         */
        public static final String IMG_SUFFIX_PNG = ".png";		
		/**
		 * 音频后缀名
		 */
		public static final String VOICE_SUFFIX = ".pcm";

		/**
		 * 压缩包后缀名
		 */
		public static final String RAR_SUFFIX = ".rar";

		/**
		 * excel 后缀名
		 */
		public static final String EXCEL_SUFFIX = ".xls";
		
		/**
		 * utf-8编码
		 */
		public static final String UTF8 = "UTF-8";
		/**
		 * 界面显示加密狗数据和已经使用的数据分隔符
		 */
		public static final String USERINFO_STAT_SUFFIX = "/";

	    /**
         * 数据已删除
         */
        public static final String IS_DELETE = "1";
        
        /**
         * 数据未删除
         */
        public static final String NOT_DELETE = "0";
        
        /**
         * 删除操作
         */
        public static final String ACTION_DELETE = "-1";
        
        /**
         * 插入操作
         */
        public static final String ACTION_INSERT = "1";
        
        /**
         * 编辑操作
         */
        public static final String ACTION_EDIT = "0";
        
        /**
         * 来源用户添加
         */
        public static final String FROM_USER = "0";
        
        /**
         * 从共享库添加
         */
        public static final String FROM_SHARING = "1";
	}

	/**
	 * <pre>
	 * *****************************************************************************
	 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：DeleteStatus
	 * Description：删除状态
	 * Entity Author: ycli5
	 * Finished：2015年4月14日 上午9:58:16
	 * ****************************************************************************
	 * </pre>
	 */
	public interface Status {

		/**
		 * 应用信息删除状态
		 */
		public static final String APPINFO_ISDELETE = "1";

		/**
		 * 应用信息未删除状态
		 */
		public static final String APPINFO_ISNOTDELETE = "0";

		/**
		 * 用户模型 注册状态
		 */
		public static final String USERMODEL_REGESIT_STATUS_KEY = "0";

		/**
		 * 用户模型 更新状态
		 */
		public static final String USERMODEL_UPDATE_STATUS_KEY = "1";

		/**
		 * 用户模型 注销状态
		 */
		public static final String USERMODEL_LOGOUT_STATUS_KEY = "2";

		/**
		 * 用户模型 删除状态
		 */
		public static final String USERMODEL_DELETE_STATUS_KEY = "3";

		/**
		 * 用户注册类型 web
		 */
		public static final String USERMODEL_SOURCETYPE_WEB_KEY = "0";

		/**
		 * 用户注册类型 app
		 */
		public static final String USERMODEL_SOURCETYPE_APP_KEY = "1";
	}

	/**
	 * <pre>
	 * *****************************************************************************
	 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：FontValidate
	 * Description：前台校验 后台再次校验
	 * Entity Author: ycli5
	 * Finished：2015年4月9日 上午11:19:47
	 * ****************************************************************************
	 * </pre>
	 */
	public interface FontValidate {

		/**
		 * 应用名称最大长度
		 */
		public static final int APPNAME_MAX_SIZE = 30;

	}

	/**
	 * <pre>
	 * *****************************************************************************
	 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：FilePath
	 * Description：文件路径
	 * Entity Author: ycli5
	 * Finished：2015年4月9日 上午11:19:59
	 * ****************************************************************************
	 * </pre>
	 */
	public interface FilePath {

		/**
		 * 配置文件路径
		 */
		public static final String CONFIGFILE_PATH = "/config/config.properties";

		/**
		 * update路径
		 */
		public static final String SDK_UPDATE_PATH = "/sdk/update";

	}

	/**
	 * <pre>
	 * *****************************************************************************
	 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：ConfigProperties
	 * Description：配置信息的key
	 * Entity Author: ycli5
	 * Finished：2015年4月9日 上午11:19:55
	 * ****************************************************************************
	 * </pre>
	 */
	public interface ConfigProperties {
		/**
		 * 模型管理允许下载的数量
		 */
		public static final String BIOMETRIC_USERMODEL_DOWNLOAD_SIZE = "biometric.usermodel.download.size";
	}
	   /**
     * @desc: service 消息映射
     * @author: dhzheng
     * @createTime: 2014-6-21 下午12:48:31
     * @version: 1.0
     */
    public interface MessageMapping {

        public static final String LOGIN_FAILURE_STRING = "idauth.login.error";
        /**
         * 配置文件中的成功
         */
        public static final String SUCCESS = "ism.operation.message.success";
        /**
         * 配置文件中的空
         */
        public static final String EMPTY = "ism.operation.message.empty";
        /**
         * 配置文件中的失败
         */
        public static final String FAILURE = "ism.operation.message.failure";
        /**
         * 配置文件中的关联
         */
        public static final String REFERENCE = "ism.operation.message.reference";
        /**
         * 配置文件中的已经存在
         */
        public static final String EXIST = "ism.operation.message.exist";
        /**
         * 配置文件中的父节点已经删除
         */
        public static final String PARENTDEL = "ism.operation.message.parentDel";
        /**
         * 配置文件中的自身已经被删除
         */
        public static final String SELFDEL = "ism.operation.message.selfDel";
        /**
         * 非法参数
         */
        public static final String ILLEGAL_PARAMETER = "ism.operation.message.illegal_parameter";
        /**
         * 同步失败
         */
        public static final String SYNCHRONIZE_FAIL = "ism.operation.message.synchronize_fail";
        /**
         * 词集内容中有重复词
         */
        public static final String EXISTTERM = "ism.operation.message.existTerm";
        /**
         * 词集内容中有非法字符
         */
        public static final String ILLEGAL_STR = "ism.operation.message.illegal_str";
        /**
         * 所选内容中可能有被删除的元素
         */
        public static final String SOMEONE_DEL = "ism.operation.message.someoneDel";
        /**
         * 所选择的数据过多
         */
        public static final String TOO_MUCH_SELECT = "ism.operation.message.tooMuch";
        /**
         * 文件格式提示信息
         */
        public static final String FILE_FORMAT = "ism.operation.message.fileFormat";
        /**
         * 文件内容错误
         */
        public static final String FILE_CONTENT = "ism.operation.message.fileContent";

        /**
         * 上传测试集超过个数限制
         */
        public static final String OVERFLOW = "ism.operation.message.fileOverflow";

        /**
         * 测试集内容为空
         */
        public static final String COLLECTION_NULL = "ism.operation.message.collectionNull";

        /**
         * 编译出错
         */
        public static final String COMPILE_FAILED = "ism.operation.message.compileFailed";

        /**
         * 解析测试出错
         */
        public static final String TEST_FAILED = "ism.operation.message.testFailed";

        /**
         * 无测试结果
         */
        public static final String NULL_TEST_RESULT = "ism.operation.message.nullTestResult";

        /**
         * 引用的模板已被删除
         */
        public static final String CITED_TEMPLATE_DEL = "ism.operation.message.citedTemplateDel";
        /**
         * 发布上线出错
         */
        public static final String PUBLISH_FAILED = "ism.operation.message.publishFailed";
        /**
         * 系统配置同类型下的code值相同
         */
        public static final String CODEEXIST = "ism.operation.message.codeExist";
        /**
         * 系统配置下的类型错误
         */
        public static final String TYPEERROR = "ism.operation.message.typeerror";
        /**
         * 句式为空
         */
        public static final String SEMCHECK_NULL = "ism.operation.message.sentencecheck.str.null";
        /**
         * 单个片段超长
         */
        public static final String SEMCHECK_SEGMENT_OVERLENGTH = "ism.operation.message.sentencecheck.segment.overlength";
        /**
         * 总长度超长
         */
        public static final String SEMCHECK_TOTAL_OVERLENGTH = "ism.operation.message.sentencecheck.total.overlength";
        /**
         * 括号内为空
         */
        public static final String SEMCHECK_BRACKETS_INSIDENULL = "ism.operation.message.sentencecheck.brackets.insidenull";
        /**
         * |位置不正确
         */
        public static final String SEMCHECK_VERTICALBAR_ERRORPOSITION = "ism.operation.message.sentencecheck.verticalbar.errorposition";
        /**
         * 不能连续使用|
         */
        public static final String SEMCHECK_VERTICALBAR_CONSTRUE = "ism.operation.message.sentencecheck.verticalbar.construe";
    }
    /**
     * @desc: 配置文件的名称
     * @author: binyang4
     * @createTime: 2014-8-23 上午11:00:32
     * @version: 2.0
     */
    public interface ConfigFileName {
        /**
         * config.properties
         */
        public static final String CONFIG_FILE = "/config.properties";
        /**
         * errorcode.properties
         */
        public static final String ERROR_CODE_FILE = "/errorcode.properties";
        /**
         * service.properties
         */
        public static final String SERVICE_FILE = "service.properties";
        /**
         * sql.properties
         */
        public static final String SQL_FILE = "sql.properties";
        /**
         * isc.properties
         */
        public static final String ISC_FILE = "/isc.properties";
        /**
         * SRCC.properties
         */
        public static final String SRCC_FILE = "/SRCC.properties";
        /**
         * message.properties
         */
        public static final String MESSAGE_FILE = "message.properties";

        /**
         * resteasy.properties
         */
        public static final String RESTEASY_FILE = "resteasy.properties";
    }
}
