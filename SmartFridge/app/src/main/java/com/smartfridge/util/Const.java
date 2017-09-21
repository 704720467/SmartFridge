package com.smartfridge.util;

/**
 * Created by zp on 2017/6/15.
 */

public class Const {
    public static final String TIMEOUT = "timeout";
    public static final String LOG_DIVISION = "^|^";
    public static final String LOCALTION_CITY = "localtionCity";// 所在城市
    public static final String KEY_LIFE_CYCLE_ID = "lifecycleId";//客户端本次启动次生命周期Id
    public static final String PUSH_TYPE_NATIVEALERT = "nativeAlert";// 弹窗（app带图片背景，只要紧急通知，不要直播）+系统消息（纯文本）
    public static final int STATE_ING = 0;
    public static final int STATE_SUCESS = 1;
    public static final int STATE_FILED = 2;
    public static final int STATE_WAIT = 3;
    public static final int STATE_STOP = 4;
    public static final int STATE_REPORT_FILED = 5;
    public static final int INVALID_STATE = -1;// 无效的状态
    public static final String BEFORE_TAG_POINT_KEY = "before_tag_point_key";//标记点前时间
    public static final String AFTER_TAG_POINT_KEY = "after_tag_point_key";//标记点后时间

    public static final int DOWN_FILE_TYPE_JPG = 1;// 下载的内容为jpg
    public static final int DOWN_FILE_TYPE_FONT = 2;// 下载的内容为字体
    public static final int DOWN_FILE_TYPE_VIDEO_AD = 3;// 下载的内容为字体
    public static final String BUG_TAGS_APPKEY_BETA = "cd887cc87d1e663b7776d87b507e3fc2";// 测试
    public static final String BUG_TAGS_APPKEY_LIVE = "9b2391e062851ba334b80ad01a10e971";// 线上
    public static final int REQUEST_PICK_VIDEO = 10;// 相册选择视频


    public static final int REFRESH = 0;
    public static final int LOAD_MORE = 1;

    public static final String ISALLOW_4G = "isallow4g";
    public static final String NOT_FIRST = "not_first";

    /**
     * token到期时间不足 多少 时刷新
     * 暂定5分钟
     */
    public static final int TOKEN_REFRESH_REMAIN_TIME = 300;

    public static final String KEY_LAST_TEAM = "key_last_team";
    public static final String APK_DOWNING = "apkdowning";// 是否正在下载apk
    public static final String CHACK_VERSION = "chackversion";// 检测版本

}
