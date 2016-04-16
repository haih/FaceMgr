/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common;

/**
 * @desc: 系统常量定义
 * @author: cheney
 * @createTime: 2014-10-29
 * @version: 1.0
 */
public class Constants {

	/**
	 * app list session
	 */
	public static final String SESSION_APP_KEY = "session_app_key";

	/**
	 * 开发者角色
	 */
	public static final String PCLOUD_APP_DEVELOP = "app_develop";

	/**
	 * 运营角色
	 */
	public static final String PCLOUD_APP_OPERATE = "app_operate";

	/**
	 * *************************************************************************
	 * **** Copyrights @ 2014，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：SdkType Description：sdk信息 Entity Author: ycli5
	 * Finished：2014年12月15日 上午10:31:47
	 * ******************************************
	 * **********************************
	 */
	public interface SdkType {
		/**
		 * sdk路径
		 */
		public static final String SDKPATH = "pcloud.sdk.path";

		/**
		 * md5串
		 */
		public static final String SEARCH = "pcloud.sdk.search";

		/**
		 * ios version
		 */
		public static final String IOS_VERSION = "pcloud.sdk.ios.version";

		/**
		 * android version
		 */
		public static final String ANDRIOD_VERSION = "pcloud.sdk.android.version";

		/**
		 * java version
		 */
		public static final String JAVA_VERSION = "pcloud.sdk.java.version";

		/**
		 * windows version
		 */
		public static final String WINDOWS_VERSION = "pcloud.sdk.windows.version";

		/**
		 * flash version
		 */
		public static final String FLASH_VERSION = "pcloud.sdk.flash.version";

		/**
		 * linux version
		 */
		public static final String LINUX_VERSION = "pcloud.sdk.linux.version";

		/**
		 * windowsphone version
		 */
		public static final String WP_VERSION = "pcloud.sdk.windowsphone.version";

		/**
		 * ios sdk name
		 */
		public static final String IOS_FILE_NAME = "pcloud.sdk.ios.filename";

		/**
		 * android sdk name
		 */
		public static final String ANDROID_FILE_NAME = "pcloud.sdk.android.filename";

		/**
		 * java sdk name
		 */
		public static final String JAVA_FILE_NAME = "pcloud.sdk.java.filename";

		/**
		 * windows sdk name
		 */
		public static final String WINDOWS_FILE_NAME = "pcloud.sdk.windows.filename";

		/**
		 * flash sdk name
		 */
		public static final String FLASH_FILE_NAME = "pcloud.sdk.flash.filename";

		/**
		 * linux sdk name
		 */
		public static final String LINUX_FILE_NAME = "pcloud.sdk.linux.filename";

		/**
		 * windowsphone sdk name
		 */
		public static final String WP_FILE_NAME = "pcloud.sdk.windowsphone.filename";
	}


	/**
	 * *************************************************************************
	 * **** Copyrights @ 2014，Iflytek Information Co., Ltd All rights reserved.
	 * Filename：DictType Description：数据字典 Entity Author: ycli5
	 * Finished：2015年1月8日 下午6:36:49
	 * *********************************************
	 * *******************************
	 */
	public interface DictType {

		/**
		 * 应用平台
		 */
		public static final String DICT_APPPLATFORM = "appPlatform";

		/**
		 * 应用能力
		 */
		public static final String DICT_APPABLE = "appable";

		/**
		 * 语种
		 */
		public static final String DICT_LANGUAGE = "language";

		/**
		 * 音库
		 */
		public static final String DICT_SOUNDBANK = "soundbank";
	}

	/**
	 * @desc: 能力的Code
	 * @author: yhsu
	 * @createTime: 2015年1月20日 上午10:14:46
	 * @version: 2.0
	 */
	public interface AppableCode {
		/**
		 * 识别
		 */
		public static final String ATS_CODE = "ats";

		/**
		 * 合成
		 */
		public static final String TTS_CODE = "tts";

		/**
		 * 声纹
		 */
		public static final String IVP_CODE = "ivp";

		/**
		 * 语义
		 */
		public static final String ISS_CODE = "iss";
	}

	/**
	 * @desc: 能力的默认语种或音库
	 * @author: yhsu
	 * @createTime: 2015年1月20日 上午10:27:45
	 * @version: 2.0
	 */
	public interface DefaultLanorSound {
		/**
		 * 合成的默认音库
		 */
		public static final String TTS_DEFAULT = "tts.default.lanorsound";

		/**
		 * 识别的默认语种
		 */
		public static final String ATS_DEFAULT = "ats.default.lanorsound";
		
		/**
		 * 默认并发路数
		 */
		public static final String APPABLE_DEFAULT_CONCURRENT_NUM="appable.default.concurrentnum";
		
		/**
		 * 默认总次数
		 */
		public static final String APPABLE_DEFAULT_MAX_USE_NUM="appable.default.maxusenum";
	}
}
