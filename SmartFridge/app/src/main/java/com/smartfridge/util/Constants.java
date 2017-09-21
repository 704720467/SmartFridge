package com.smartfridge.util;

/**
 * Created by zp on 2017/6/15.
 */

public class Constants {
    public static final String USER_SP_KEY = "user";
    public static final String TOKEN_SP_KEY = "token";
    public static final String REFRESH_TOKEN_SP_KEY = "refreshtoken";
    public static final String TOKEN_EXPIRES_KEY = "token_expires";
    public static final String USER_ID_KEY = "user_id_key";
    public static final String TOKEN_SAVE_TIME_KEY = "token_save_time_key";
    public static final String LOCALTION_LONG = "localtionLongitude";// 经度121.0018
    public static final String LOCALTION_LAT = "localtionLatitude";// 纬度121.0018
    public static final String LOCALTION_REFRESH = "localtionRefresh";// 位置信息刷新标记
    public static final String IS_NEW_USER = "isNewUser";//是否是新安装,或者新用户,用来区分使用新老用户的获取DeviceID策略
    public static final String REEE_JJ_FOLDER = "reeejj";// 项目文件夹
    public static final String DOWNLOAD_FOLDER = "reeejj/download";// 下载文件夹

    public static final String USERAGREEMENT = "http://public.reee.cn/jj/static/agreement.html";// 用户协议地址

    // ----线上----
    //    public static final String HTTPS_URL = "https://app2.reee.cn";// 线上;
    //    public static final String HTTP_URL = "http://app2.reee.cn";// 线上;
    //    public static final String BASE_GAIN_AVATAR_URL = "http://img.reee.cn/";// 线上;
    //    // 日志提交接口
    //    public static final String LOG_URL = "https://log.reee.cn/reportlog/android";// 线上
    //    public static boolean DEBUG = false;
    //    // wk 3.0 需求：测试为http 发布为https 故设此标记 发布需关闭
    //    public static final boolean IsDebug = false;

    // ----测试----[101.200.2.156]
    public static final String HTTP_URL = "http://jj.reeesystem.com";
    public static final String HTTPS_URL = "https://jj.reeesystem.com";
    public static final String BASE_GAIN_AVATAR_URL = "http://img.reeesystem.com/";
    public static final String SHARE_URL = "http://h5.reeesystem.com/";
    public static final String LOG_URL = "https://log.reeesystem.com/debug/reportlog/android";// 测试，日志提交接口
    public static boolean DEBUG = true;
    // wk 3.0 需求：测试为http 发布为https 故设此标记 发布需关闭
    public static final boolean IsDebug = false;

    // ----su----[101.200.2.156]
    //    public static final String HTTP_URL = "http://192.168.1.88:3004";
    //    public static final String HTTPS_URL = "https://jj.reeesystem.com";
    //    public static final String BASE_GAIN_AVATAR_URL = "http://img.reeesystem.com/";
    //    public static final String SHARE_URL = "http://h5.reeesystem.com/";
    //    public static final String LOG_URL = "https://log.reeesystem.com/debug/reportlog/android";// 测试，日志提交接口
    //    public static boolean DEBUG = true;
    //    // wk 3.0 需求：测试为http 发布为https 故设此标记 发布需关闭
    //    public static final boolean IsDebug = true;


    public static final String NETWORK_REQUEST_VERSION = "1.0.0";// 接口访问版本
    public static final String USER_AGENT = "Android/sport-1.0.6";// 视频播放器中的user/agent
    public static final String VIDEO_THUMB_RATIO = "?imageView2/0/w/320";// 七牛所使用的图片裁剪地址拼接
    public static final String VIDEO_THUMB_RATIO_800 = "?imageView2/0/w/800";// 七牛所使用的图片裁剪地址拼接
    public static final String ABOUT_US = "http://weibo.com/u/5562998358";
    public static final String OS_VERSION = "1.0.0";// 系统版本号
}
