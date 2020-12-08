package client;

import model.Admin;
import model.User;

public class Logined {
    static private User user;
    static private Admin admin;

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
}
