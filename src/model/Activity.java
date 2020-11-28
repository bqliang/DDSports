package model;

import java.io.Serializable;
import java.sql.Time;

/**
 * @author bqliang
 */

public class Activity implements Serializable {
    private int id;
    private String name;
    private String sponsor;
    private Time time;
    private String place;
    private int recruit;

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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
}
