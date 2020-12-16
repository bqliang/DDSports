package client;

import model.Admin;
import model.User;

import java.util.List;

/**
 * @author bqliang
 */
public class Logined {
    static private User user;
    static private Admin admin;
    static private List<Integer> idList;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Logined.user = user;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        Logined.admin = admin;
    }

    public static List<Integer> getIdList() {
        return idList;
    }

    public static void setIdList(List<Integer> idList) {
        Logined.idList = idList;
    }
}
