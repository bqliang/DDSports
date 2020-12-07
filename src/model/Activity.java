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
    private Timestamp startTime;
    private Timestamp endTime;
    private String place;
    private int recruit;
    private int join;
    private int checkIn;
    private String jusers;
    private String cusers;

    public Activity(int id, String name, String sponsor, Timestamp startTime, Timestamp endTime, String place, int recruit, int join, int checkIn, String jusers, String cusers) {
        this.id = id;
        this.name = name;
        this.sponsor = sponsor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.recruit = recruit;
        this.join = join;
        this.checkIn = checkIn;
        this.jusers = jusers;
        this.cusers = cusers;
    }

    public Activity(String name, String sponsor, Timestamp startTime, Timestamp endTime, String place, int recruit) {
        this.name = name;
        this.sponsor = sponsor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.recruit = recruit;
        this.join = join;
        this.checkIn = checkIn;
        this.jusers = jusers;
        this.cusers = cusers;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public int getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(int checkIn) {
        this.checkIn = checkIn;
    }

    public String getJusers() {
        return jusers;
    }

    public void setJusers(String jusers) {
        this.jusers = jusers;
    }

    public String getCusers() {
        return cusers;
    }

    public void setCusers(String cusers) {
        this.cusers = cusers;
    }
}
