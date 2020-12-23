package model;

import java.io.Serializable;

/**
 * @author bqliang
 */

public class User implements Serializable {
    private int id;
    private String name;
    private String pw;
    private String gender;
    private String contact;
    private String email;
    private String realName;
    private String idCard;
    private String certificate;

    public User(){
    }


    /**
     * 查看用
     * @param id
     */
    public User(int id) {
        this.id = id;
    }


    /**
     * 用于设置认证状态
     * @param id
     * @param certificate
     */
    public User(int id, String certificate) {
        this.id = id;
        this.certificate = certificate;
    }

    /**
     * 登录用
     * @param name
     * @param pw
     */
    public User(String name, String pw) {
        this.name = name;
        this.pw = pw;
    }

    /**
     * 注册用
     * @param name
     * @param pw
     * @param gender
     * @param contact
     * @param email
     */
    public User(String name, String pw, String gender, String contact, String email) {
        this.name = name;
        this.pw = pw;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
    }

    /**
     * 无密码构造方法
     * @param id
     * @param name
     * @param gender
     * @param contact
     * @param email
     * @param realName
     * @param idCard
     * @param certificate
     */
    public User(int id, String name, String gender, String contact, String email, String realName, String idCard, String certificate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.realName = realName;
        this.idCard = idCard;
        this.certificate = certificate;
    }

    /**
     * 完全构造方法
     * @param id
     * @param name
     * @param pw
     * @param gender
     * @param contact
     * @param email
     * @param realName
     * @param idCard
     * @param certificate
     */
    public User(int id, String name, String pw, String gender, String contact, String email, String realName, String idCard, String certificate) {
        this.id = id;
        this.name = name;
        this.pw = pw;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.realName = realName;
        this.idCard = idCard;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
