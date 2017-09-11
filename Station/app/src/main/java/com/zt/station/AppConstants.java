package com.zt.station;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/7/19
 * Description: AppConstants
 */

public class AppConstants {
    public static final String AROUTER_SIGN = "/feature/sign";
    public static final String AROUTER_ROADS = "/feature/roads";
    public static final String AROUTER_MY_WALLET = "/feature/my_wallet";
    public static final String AROUTER_MAIN = "/feature/main";
    public static final String AROUTER_SETTING = "/feature/setting";
    public static final String AROUTER_MY_WALLET_BALACE = "/feature/my_wallet_balance";
    public static final String AROUTER_TRIP = "/feature/trip";
    public static final String AROUTER_SET_TRIP = "/feature/set_trip";
    public static final String AROUTER_DRIVER_VERIFICATION = "/feature/driver_verification";
    public static final String AROUTER_DRIVER_FINISH_TRIP = "/feature/driver_finish_trip";
    public static final String AROUTER_MAIN_NEW= "/feature/main_new";
    public static final String AROUTER_MAP_PASS = "/feature/map_new";
    public static final String AROUTER_BUS_LINE= "/feature/bus_line";
    public static final String AROUTER_MAP_DRIVER= "/feature/map_driver";

    public static final String AROUTER_ACCOUNT_SECURITY= "/feature/account_security";
    public static final String AROUTER_CHANGE_PHONE= "/feature/change_phone";
    public static final String AROUTER_EMERGENCY_PEOPLE= "/feature/emergency_people";
    public static final String AROUTER_POLICY= "/feature/policy";
    public static final String AROUTER_PRIVATE_SETTING= "/feature/private_setting";
    public static final String SEARCH_HISTORY= "search_history";

    public static final int SIGN_PASSENGER_REQUEST_CODE = 100;
    public static final int SIGN_PASSENGER_RESPONSE_CODE = 101;
    public static final int SIGN_DRIVER_REQUEST_CODE = 102;
    public static final int SIGN_DRIVER_RESPONSE_CODE = 103;
    public static final int SIGN_CHANGE_INFO_REQUEST_CODE = 104;
    public static final int SIGN_CHANGE_INFO_DRIVER_RESPONSE_CODE = 105;
    public static final int SELECT_START_REQUEST_CODE = 130;
    public static final int SELECT_STOP_RESPONSE_CODE = 131;

    public static final int PASS_SELECT_STOP = 106;
    public static final int PASS_PUBLISH_TRIP = 107;
    public static final int PASS_WAITE = 108;
    public static final int PASS_HAS_MATCHED = 109;
    public static final int PASS_GET_THE_CAR = 111;
    public static final int PASS_CHANGE_MATCHED_CAR = 112;
    public static final int PASS_HAS_PAY= 113;
    public static final int PASS_FINISH= 114;
    public static final int PASS_READY= 115;
    public static final int PASS_LEAVE_STOP= 116;
    public static final int PASS_CAR_IN_AND_CAR_OUT_REMINDER= 117;
    public static final int SELECT_PIC = 118;
    public static final int TAKE_PHOTO = 119;
    public static final int ACTIVITY_RESULT_CROP_PICTURE = 120;
    public static final int DRIVER_START = 121;
    public static final int DRIVER_REACH_STOP = 122;
    public static final int DRIVER_TRIPING = 123;
    public static final int DRIVER_REMINDER = 124;
    public static final int DRIVER_FINISH = 126;
    public static final int DRIVER_READY = 127;
    public static final int DRIVER_SELECT_ROADS = 128;
    public static final int DRIVER_SELECT_STOP = 129;
    public static final int BUS_LINE_STATION_REQUEST = 130;
    public static final int MAP_REQUEST = 131;

    public static final String IS_LOGIN = "is_login";//标识登录状态
    public static final String SIGN_PHONE = "phone";
    public static final String SIGN_ROLE = "role";
    public static final String DRIVER = "driver";//标识司机角色
    public static final String PASSENGER = "passenger";//标识乘客角色

    public static final String NICK_NAME = "nick_name";
    public static final String SEX = "sex";
    public static final String BIRTH = "birth";
    public static final String COMPANY = "company";
    public static final String INDUSTRY = "industry";
    public static final String POSITION = "position";
    public static final String SIGNATURE = "signature";
    public static final String HEAD_URL = "head_url";
    public static final String USER_TOKEN = "user_token";

    public static final String START_STATION = "start_station";
    public static final String END_STATION = "end_station";
    public static final String MAP_TITLE = "title";
    public static final String MAP_PUBLISH_START = "start_stop";
    public static final String MAP_PUBLISH_END = "end_stop";
    public static final String MAP_ROUTES_CATEGORY = "routes_category";
    public static final String BUS_LINE = "bus_line";

    //Routes Log Settings
    public static final int ROUTES_START_STATION_INDEX = 0;
    public static final int ROUTES_END_STATION_INDEX = 99999;
    public static final String ROUTES_STATION_REPLACE_VALUE = "(公交站)";
}
