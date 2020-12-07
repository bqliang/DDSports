package model;

import java.io.Serializable;
import java.util.List;

/**
 * @author bqliang
 */

public class Transfer implements Serializable {
    private int command;
    private int result;
    private User user;
    private Admin admin;
    private Activity activity;
    private List<User> userList;
    private List<Activity> activityList;

    private String code;
    private Boolean ifPassCertification;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIfPassCertification() {
        return ifPassCertification;
    }

    public void setIfPassCertification(Boolean ifPassCertification) {
        this.ifPassCertification = ifPassCertification;
    }
}
