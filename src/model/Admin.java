package model;

import java.io.Serializable;

/**
 * @author bqliang
 */

public class Admin implements Serializable {
    private int id;
    private String pw;
    private String name;

    public Admin(String name,String pw) {
        this.pw = pw;
        this.name = name;
    }

    public Admin(int id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
