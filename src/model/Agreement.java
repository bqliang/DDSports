package model;

/**
 * @author bqliang
 */

public interface Agreement {

    /**
     * 用户相关操作码
     */
    int REGISTER = 1001;
    int USER_LOGIN = 1002;
    int EDIT_PROFILE = 1003;
    int AUTHENTICATE = 1004;
    int CREATE_ACTIVITY = 1005;
    int CHANGE_INFO = 1006;
    int SEARCH = 1007;
    int JOIN = 1008;
    int CHECK_IN = 1009;
    int EXIT_ACTIVITY = 1010;
    int REPORT = 1011;
    int SEND_CODE = 1012;
    int LOGIN_BY_EMAIL = 1013;
    int RETRIEVE_PASSWORD = 1014;
    int RESET_PASSWORD = 1015;

    /**
     * 管理员相关操作码
     */
    int ADMIN_LOGIN = 2001;
    int VIEW_USER = 2002;
    int DELETE_USER = 2003;
    int REVIEW_CERTIFICATE = 2005;
    int REVIEW_REPORT = 2006;
    int VIEW_CERTIFICATE_USERS = 2007;
    int FILTER_USERS = 2008;
    int SET_CERTIFICATE_STATUS = 2009;
    int ADMIN_UPDATE_USER_PROFILE =2010;


    /**
     * 公共操作码
     */
    int VIEW_ACTIVITY = 3001;
    int VIEW_ACTIVITIES = 3002;
    int FILTER_ACTIVITIES = 3003;
    int DELETE_ACTIVITY = 3004;


    int SUCCESS = 4001;
    /**
     * 错误码
     */
    int NAME_ALREADY_EXISTS = 4002;
    int PASSWORD_ERROR = 4003;
    int ACCOUNT_NOT_EXIST = 4004;
    int CREATE_ACTIVITY_FAIL = 4005;
    int JOIN_FAIL = 4006;
    int CHECK_IN_FAIL = 4007;
    int VIEW_ACTIVITY_FAIL = 4008;
    int VIEW_USER_FAIL = 4009;
    int REVIEW_CERTIFICATE_FAIL = 4010;
    int EMAIL_NOT_EXISTS = 4011;
    int EMAIL_ALREADY_EXISTS = 4012;
    int RETRIEVE_PASSWORD_FAIL = 4013;
    int EDIT_PROFILE_FAIL = 4014;
    int APPLY_AUTHENTICATE_FAIL = 4015;
    int DELETE_ACTIVITY_FAIL = 4016;
    int ALREADY_JOINED = 4017;
    int EXIT_ACTIVITY_FAIL = 4018;
    int NOT_YET_JOIN = 4019;
    int ALREADY_CHECK_IN = 4020;
    int SET_CERTIFICATE_STATUS_FAIL = 4021;
    int ADMIN_UPDATE_USER_PROFILE_FAIL =4022;
    int DELETE_USER_FAIL = 4023;
}
