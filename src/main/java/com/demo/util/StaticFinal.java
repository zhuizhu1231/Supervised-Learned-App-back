package com.demo.util;

public class StaticFinal {
    //user中性别显示情况
    public static final int SEX_MAN = 0;
    public static final int SEX_WOMAN = 1;

    //user中目标显示情况
    public static final int HIDE = 0;
    public static final int SHOW = 1;

    //user中登录情况
    public static final int ONLINE = 1;
    public static final int OFFLINE = 0;

    //message中消息是否被提示过
    public static final int PROMPT_TRUE = 1;
    public static final int PROMPT_FALSE = 0;

    //message中消息是否被阅读过
    public static final int READ_TRUE = 1;
    public static final int READ_FALSE = 0;

    //schedule中事件是否完成
    public static final int DONE = 1;
    public static final int NOT_DONE = 0;

    //schedule中category设置日程的紧要重要程度
    public static final int CATEGORY_IMPORT_EMERGE = 1;
    public static final int CATEGORY_IMPORT_NO_EMERGE = 2;
    public static final int CATEGORY_NO_IMPORT_EMERGE = 3;
    public static final int CATEGORY_NO_IMPORT_NO_EMERGE = 4;

    //schedule中的property为闹钟 是否提醒
    public static final int CLOCK_SET = 1;
    public static final int CLOCK_NO_SET = 0;


    //USEr in Room的is_create字段 后期可改成管理员标志
    public static final int ROOM_IS_CREATE_TRUE = 1;
    public static final int ROOM_IS_CREATE_FALSE = 0;
    //response中
//code是操作是否成功，isSuccess是返回是否为空
//    请求码状态
    public static final int CODE_REQUEST_SUCCESS = 200;
    public static final int CODE_UPDATE_NOT = 304;
    public static final int CODE_REQUEST_FAIL = 400;
    public static final int CODE_NEED_PERMISSION = 401;
    public static final int CODE_SERVER_ERROR = 500;

//    200请求已成功，请求所希望的响应头或数据体将随此响应返回。
//    304无需更新
//    400（请求无效），
//    401（需要权限），
//    500（服务器错误）

}
