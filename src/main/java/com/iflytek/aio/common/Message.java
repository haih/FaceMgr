package com.iflytek.aio.common;

import com.iflytek.aio.common.Constant.MessageMapping;

/**
 * @desc: 系统反馈给页面的信息
 * @author: dhzheng
 * @createTime: 2014-7-1 下午08:17:06
 * @version: 2.0
 */
public enum Message {

    /**
     * 配置文件中的成功
     */
    SUCCESS(MessageMapping.SUCCESS, 10001),

    /**
     * 配置文件中的空
     */
    EMPTY(MessageMapping.EMPTY, 10002),

    /**
     * 配置文件中的失败
     */
    FAILURE(MessageMapping.FAILURE, 10003),

    /**
     * 配置文件中的关联
     */
    REFERENCE(MessageMapping.REFERENCE, 10004),

    /**
     * 配置文件中的已经存在
     */
    EXIST(MessageMapping.EXIST, 10005),

    /**
     * 配置文件中的父节点已经删除
     */
    PARENTDEL(MessageMapping.PARENTDEL, 10006),

    /**
     * 配置文件中的自身已经被删除
     */
    SELFDEL(MessageMapping.SELFDEL, 10007),

    /**
     * 配置文件中的非法参数
     */
    ILLEGAL_PARAMETER(MessageMapping.ILLEGAL_PARAMETER, 10008),

    /**
     * 同步失败
     */
    SYNCHRONIZE_FAIL(MessageMapping.SYNCHRONIZE_FAIL, 10009),

    /**
     * 词集内容中有重复词
     */
    EXISTTERM(MessageMapping.EXISTTERM, 10010),

    /**
     * 词集内容中有非法字符
     */
    ILLEGAL_STR(MessageMapping.ILLEGAL_STR, 10011),

    /**
     * 文件格式错误
     */
    FILE_FORMAT(MessageMapping.FILE_FORMAT, 10012),

    /**
     * 文件内容错误
     */
    FILE_CONTENT(MessageMapping.FILE_CONTENT, 10013),

    /**
     * 测试出错
     */
    TEST_FAILED(MessageMapping.TEST_FAILED, 10014),

    /**
     * 编译出错
     */
    COMPILE_FAILED(MessageMapping.COMPILE_FAILED, 10015),

    /**
     * 无测试结果
     */
    NULL_TEST_RESULT(MessageMapping.NULL_TEST_RESULT, 10016),

    /**
     * 引用的模板已被删除
     */
    CITED_TEMPLATE_DEL(MessageMapping.CITED_TEMPLATE_DEL, 10017),

    /**
     * 发布上线出错
     */
    PUBLISH_FAILED(MessageMapping.PUBLISH_FAILED, 10018),

    /**
     * 编码重复
     */
    CODEEXIST(MessageMapping.CODEEXIST, 10019),
    /**
     * 类型错误
     */
    TYPEERROR(MessageMapping.TYPEERROR, 10020),
    
    /**
    * 上传文件超过个数
    */
    OVERFLOW(MessageMapping.OVERFLOW, 10022);
    
    

    /**
     * messageId
     */
    private String key;

    /**
     * messageType
     */
    private int type;

    /**
     * 构造方法
     * 
     * @param key
     * @param type
     */
    private Message(String key, int type) {
        this.key = key;
        this.type = type;
    }

    /**
     * 根据 key 获取 type
     * 
     * @author: dhzheng
     * @createTime: 2014-7-1 下午08:19:08
     * @param key
     * @return int
     */
    public static int getType(String key) {

        for (Message message : Message.values()) {
            if (message.getKey() == key) {
                return message.getType();
            }
        }
        return -1;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.key;
    }

}
