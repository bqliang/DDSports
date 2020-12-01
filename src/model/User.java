package model;

import java.io.Serializable;

/**
 * @author bqliang
 */

public class User implements Serializable {
    private int id;
    private String name;
    private String pw;
    private String realName;
    private String idCard;
    private String gender;
    private String contact;
    private String certificate;

    public User(){

    }

    public User(String name, String pw){
        this.name = name;
        this.pw = pw;
    }

    public User(int id, String name, String pw, String realName, String idCard, String gender, String contact, String certificate) {
        this.id = id;
        this.name = name;
        this.pw = pw;
        this.realName = realName;
        this.idCard = idCard;
        this.gender = gender;
        this.contact = contact;
        this.certificate = certificate;
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

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
