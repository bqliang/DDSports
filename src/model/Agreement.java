package model;

/**
 * @author bqliang
 */

public interface Agreement {

    /**
     * 用户相关操作码
     */
    int  REGISTER = 1001;
    int USER_LOGIN = 1002;
    int EDIT_PROFILE = 1003;
    int AUTHENTICATE = 1004;
    int INITIATE = 1005;
    int CHANGE_INFO = 1006;
    int SEARCH = 1007;
    int JOIN = 1008;
    int CHECK_IN = 1009;
    int EXIT = 1010;
    int REPORT = 1011;


    /**
     * 管理员相关操作码
     */
    int ADMIN_LOGIN = 2001;
    int VIEW_USER = 2002;
    int DELETE_USER = 2003;
    int DELETE_ACTIVITY = 2004;
    int REVIEW_CERTIFICATE = 2005;
    int REVIEW_REPORT = 2006;


    /**
     * 公共操作码
     */
    int VIEW_ACTIVITY = 3001;
    int VIEW_ACTIVITIES = 3002;


    int SUCCESS = 4001;
    /**
     * 错误码
     */
    int NAME_ALREADY_EXISTS = 4002;
    int PASSWORD_ERROR = 4003;
    int ACCOUNT_NOT_EXIST = 4004;
    int CREATE_ACTIVITY_FAIL = 4005;
    int JOIN_FAIL = 4006;
    int CHECKIN_FAIL = 4007;
    int VIEW_ACTIVITY_FAIL = 4008;
    int VIEW_USER_FAIL = 4009;

}
