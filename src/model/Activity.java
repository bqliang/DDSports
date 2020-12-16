package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author bqliang
 */

public class Activity implements Serializable {
    private int id;
    private String name;
    private String sponsor;
    private Timestamp time;
    private float duration;
    private String place;
    private String postscript;
    private int recruit;
    private int join;
    private String jUsers;
    private int checkIn;
    private String cUsers;
    private String status;

    /**
     * 用于发起活动的构造方法
     * @param name 活动名
     * @param sponsor 发起人
     * @param time 开始时间
     * @param duration 持续时间（小时）
     * @param place 地点
     * @param postscript 备注
     * @param recruit 招募人数
     */
    public Activity(String name, String sponsor, Timestamp time, float duration, String place, String postscript, int recruit) {
        this.name = name;
        this.sponsor = sponsor;
        this.time = time;
        this.duration = duration;
        this.place = place;
        this.postscript = postscript;
        this.recruit = recruit;
    }

    /**
     * 完全构造方法
     * @param id 活动ID
     * @param name 活动名
     * @param sponsor 发起人
     * @param time 开始时间
     * @param duration 持续时间（小时）
     * @param place 地点
     * @param postscript 备注
     * @param recruit 招募人数
     * @param join 报名人数
     * @param jUsers 报名人员
     * @param checkIn 打卡人数
     * @param cUsers 打卡人员
     * @param status 活动状态
     */
    public Activity(int id, String name, String sponsor, Timestamp time, float duration, String place, String postscript, int recruit, int join, String jUsers, int checkIn, String cUsers, String status) {
        this.id = id;
        this.name = name;
        this.sponsor = sponsor;
        this.time = time;
        this.duration = duration;
        this.place = place;
        this.postscript = postscript;
        this.recruit = recruit;
        this.join = join;
        this.jUsers = jUsers;
        this.checkIn = checkIn;
        this.cUsers = cUsers;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public int getRecruit() {
        return recruit;
    }

    public void setRecruit(int recruit) {
        this.recruit = recruit;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public String getjUsers() {
        return jUsers;
    }

    public void setjUsers(String jUsers) {
        this.jUsers = jUsers;
    }

    public int getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(int checkIn) {
        this.checkIn = checkIn;
    }

    public String getcUsers() {
        return cUsers;
    }

    public void setcUsers(String cUsers) {
        this.cUsers = cUsers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
