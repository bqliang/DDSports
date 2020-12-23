package server;

import model.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bqliang
 */

public class DbHandler implements Agreement {

    static public Statement stat;

    static {
        try {
            stat = DbConnect.getStat();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用户注册
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer register(User user) throws SQLException {
        Transfer feedback = new Transfer();
        // 判断name是否已存在
        ResultSet nameRs = stat.executeQuery(String.format("SELECT * FROM user WHERE name = '%s'",user.getName()));
        if (nameRs.next()){
            feedback.setResult(NAME_ALREADY_EXISTS);
            return feedback;
        }

        // 判断邮箱此前是否已注册
        ResultSet emailRs = stat.executeQuery(String.format("SELECT * FROM user WHERE email = '%s'",user.getEmail()));
        if (emailRs.next()){
            feedback.setResult(EMAIL_ALREADY_EXISTS);
            return feedback;
        }

        // 注册插入数据
        stat.executeUpdate(String.format("INSERT INTO user (name,password,gender,contact,email) VALUES ('%s','%s','%s','%s','%s')",
                user.getName(),user.getPw(),user.getGender(),user.getContact(),user.getEmail()));
        // 获取id并返回
        ResultSet idRs = stat.executeQuery(String.format("SELECT id FROM user WHERE name = '%s'",user.getName()));
        idRs.next();
        user.setId(idRs.getInt("id"));
        user.setCertificate("未审核");
        feedback.setResult(SUCCESS);
        feedback.setUser(user);
        return feedback;
    }


    /**
     * 用户密码登录
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer userLogin(User user) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM user WHERE name = '%s'",user.getName()));
        if (rs.next()){
            String pw = rs.getString("password");
            if (user.getPw().equals(pw)){
                user.setId(rs.getInt("id"));
                user.setGender(rs.getString("gender"));
                user.setContact(rs.getString("contact"));
                user.setRealName(rs.getString("realname"));
                user.setIdCard(rs.getString("idcard"));
                user.setCertificate(rs.getString("certificate"));
                user.setEmail(rs.getString("email"));
                feedback.setResult(SUCCESS);
                feedback.setUser(user);
            }else {
                // 用户存在，但密码不正确
                feedback.setResult(PASSWORD_ERROR);
            }
        }else {
            // 用户不存在
            feedback.setResult(ACCOUNT_NOT_EXIST);
        }
        return feedback;
    }


    /**
     * 管理员登录
     * @param admin
     * @return
     * @throws SQLException
     */
    public static Transfer adminLogin(Admin admin) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM admin WHERE name = '%s'",admin.getName()));
        if (rs.next()){
            String pw = rs.getString("password");
            if (admin.getPw().equals(pw)){
                admin.setId(rs.getInt("id"));
                feedback.setResult(SUCCESS);
                feedback.setAdmin(admin);
            }else {
                feedback.setResult(PASSWORD_ERROR);
            }
        }else {
            feedback.setResult(ACCOUNT_NOT_EXIST);
        }
        return feedback;
    }


    /**
     * 发送验证码
     * @param user
     * @return
     * @throws SQLException
     * @throws MessagingException
     */
    public static Transfer sendLoginCode(User user) throws SQLException, MessagingException {
        Transfer transfer = new Transfer();
        // 发送验证码前先判断该邮箱是否存在
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM user WHERE email = '%s'",user.getEmail()));
        if (rs.next()){
            transfer = Email.send(user.getEmail());
            transfer.setResult(SUCCESS);
        }else {
            transfer.setResult(EMAIL_NOT_EXISTS);
        }
        return transfer;
    }


    /**
     * 邮箱登录成功，返回数据
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer loginByCode(User user) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM user WHERE email = '%s'",user.getEmail()));
        rs.next();
        feedback.setUser(
                new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("email"),
                        rs.getString("realname"),
                        rs.getString("idcard"),
                        rs.getString("certificate")
                        )
        );
        feedback.setResult(SUCCESS);
        return feedback;
    }


    /**
     * 用户申请认证
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer authenticate (User user) throws SQLException {
        Transfer feedback = new Transfer();
        System.out.println(String.format("UPDATE user SET realname = '%s', idcard = '%s', certificate = '审核中' WHERE id = %d", user.getRealName(), user.getIdCard(), user.getId()));
        int affectedRow = stat.executeUpdate(String.format("UPDATE user SET realname = '%s', idcard = '%s', certificate = '审核中' WHERE id = %d", user.getRealName(), user.getIdCard(), user.getId()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(APPLY_AUTHENTICATE_FAIL);
        }
        return feedback;
    }


    /**
     * 用户发起活动
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer createActivity(Activity activity) throws SQLException {
        Transfer feedback = new Transfer();
        String sql = "INSERT INTO activity (name,sponsor,time,duration,place,postscript,recruit) VALUES ('%s','%s','%s',%.1f,'%s','%s',%d)";
        int affectedRow = stat.executeUpdate(String.format(sql,
                activity.getName(), activity.getSponsor(), activity.getTime(), activity.getDuration(), activity.getPlace(), activity.getPostscript(), activity.getRecruit()));
        if (affectedRow == 1){
            // 获取id
            ResultSet rs = stat.executeQuery(String.format("SELECT * FROM activity WHERE name = '%s' AND sponsor = '%s' AND time ='%s' AND place = '%s';",
                    activity.getName(), activity.getSponsor(), activity.getTime(), activity.getPlace()));
            rs.next();
            int id = rs.getInt("id");
            rs.next();
            System.out.println(id);
            String setStatus = "CREATE EVENT set%sStatus%d\n" +
                    "    ON SCHEDULE AT '%s'\n" +
                    "    DO\n" +
                    "      UPDATE activity SET status = '%s' WHERE id = %d;";
            stat.execute(String.format(setStatus, "Start", id, activity.getTime(), "已开始", id));
            stat.execute(String.format(setStatus, "End", id, new Timestamp((long) (activity.getTime().getTime() + (activity.getDuration() * 3600000))), "已结束", id));
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(CREATE_ACTIVITY_FAIL);
        }
        return feedback;
    }


    /**
     * 用户加入活动
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer join(User user, Activity activity) throws SQLException {
        int id = activity.getId();
        Transfer feedback = new Transfer();
        // 先获取最新的数据
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM activity WHERE id = %d", id));
        rs.next();
        String jUsers = rs.getString("jusers");
        if (!jUsers.contains(user.getName())){
            int affectedRow = stat.executeUpdate(String.format("UPDATE activity SET `join` = `join`+1, jusers = '%s' WHERE id = %d",
                    rs.getString("jusers") + user.getName() + " ", id));
            if (affectedRow == 1){
                feedback.setResult(SUCCESS);
            }else {
                feedback.setResult(JOIN_FAIL);
            }
        }else {
            feedback.setResult(ALREADY_JOINED);
        }
        return feedback;
    }


    /**
     * 用户打卡
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer checkIn(User user, Activity activity) throws SQLException {
        int id = activity.getId();
        String cusers;
        String userName = user.getName();
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT cusers FROM activity WHERE id = %d", id));
        rs.next();
        cusers = rs.getString("cusers");
        if (!cusers.contains(userName)){
            cusers = cusers + userName + " ";
            int affectedRow = stat.executeUpdate(String.format("UPDATE activity SET checkin = checkin+1, cusers = '%s' WHERE id = %d",
                    cusers, id));
            if (affectedRow == 1){
                feedback.setResult(SUCCESS);
            }else {
                feedback.setResult(CHECK_IN_FAIL);
            }
        }else {
            feedback.setResult(CHECK_IN_FAIL);
        }
        return feedback;
    }


    /**
     * 浏览活动
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer viewActivity(Activity activity) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM activity WHERE id = %d",activity.getId()));
        if (rs.next()){
            feedback.setActivity(
                    new Activity(
                            activity.getId(),
                            rs.getString("name"),
                            rs.getString("sponsor"),
                            rs.getTimestamp("time"),
                            rs.getFloat("duration"),
                            rs.getString("place"),
                            rs.getString("postscript"),
                            rs.getInt("recruit"),
                            rs.getInt("join"),
                            rs.getString("jusers"),
                            rs.getInt("checkin"),
                            rs.getString("cusers"),
                            rs.getString("status")
                    )
            );
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(VIEW_ACTIVITY_FAIL);
        }
        return feedback;
    }


    /**
     * 查看用户资料
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer viewUser(User user) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT * FROM user WHERE id = %d",user.getId()));
        if (rs.next()){
            feedback.setUser(
                    new User(
                            user.getId(),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("gender"),
                            rs.getString("contact"),
                            rs.getString("email"),
                            rs.getString("realname"),
                            rs.getString("idcard"),
                            rs.getString("certificate")
                    )
            );
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(VIEW_USER_FAIL);
        }
        return feedback;
    }


    /**
     * 浏览申请认证的用户
     * @return
     * @throws SQLException
     */
    public static Transfer viewCertificateUsers() throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery("SELECT * FROM user WHERE certificate = '审核中'");
        List<User> users = new ArrayList<User>();
        while (rs.next()){
            users.add(
                    new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            " ",
                            rs.getString("realname"),
                            rs.getString("idcard"),
                            rs.getString("gender"),
                            rs.getString("contact"),
                            rs.getString("certificate"),
                            rs.getString("email")
                    )
            );
        }
        feedback.setUserList(users);
        return  feedback;
    }


    /**
     * 浏览所有活动
     * @return
     * @throws SQLException
     */
    public static Transfer viewActivities() throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery("SELECT * FROM activity");
        List<Activity> activities = new ArrayList<Activity>();
        while (rs.next()){
            activities.add(
                    new Activity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("sponsor"),
                            rs.getTimestamp("time"),
                            rs.getFloat("duration"),
                            rs.getString("place"),
                            rs.getString("postscript"),
                            rs.getInt("recruit"),
                            rs.getInt("join"),
                            rs.getString("jusers"),
                            rs.getInt("checkin"),
                            rs.getString("cusers"),
                            rs.getString("status")
                    )
            );
        }
        feedback.setResult(SUCCESS);
        feedback.setActivityList(activities);
        return feedback;
    }


    /**
     * 用户筛选活动
     * @param sql
     * @return
     * @throws SQLException
     */
    public static Transfer filterActivities(String sql) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(sql);
        List<Activity> activities = new ArrayList<Activity>();
        while (rs.next()){
            activities.add(
                    new Activity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("sponsor"),
                            rs.getTimestamp("time"),
                            rs.getFloat("duration"),
                            rs.getString("place"),
                            rs.getString("postscript"),
                            rs.getInt("recruit"),
                            rs.getInt("join"),
                            rs.getString("jusers"),
                            rs.getInt("checkin"),
                            rs.getString("cusers"),
                            rs.getString("status")
                    )
            );
        }
        feedback.setResult(SUCCESS);
        feedback.setActivityList(activities);
        return feedback;
    }


    /**
     * 用户通过验证码修改密码
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer retrievePassword(User user) throws SQLException {
        Transfer feedback = new Transfer();
        int affectedRow = stat.executeUpdate(String.format("UPDATE user SET password = '%s' WHERE email = '%s'", user.getPw(), user.getEmail()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(RETRIEVE_PASSWORD_FAIL);
        }
        return feedback;
    }


    /**
     * 用户通过原密码修改密码
     * @param transfer
     * @return
     */
    public static Transfer resetPwByPw(Transfer transfer) throws SQLException {
        User user = transfer.getUser();
        Transfer feedback = new Transfer();
        String sql = "UPDATE user SET password = '%s' WHERE id = %d AND password = '%s'";
        int affectedRow = stat.executeUpdate(String.format(sql, transfer.getNewPassword(), user.getId(), user.getPw()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(PASSWORD_ERROR);
        }
        return feedback;
    }


    /**
     * 用户修改资料
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer editProfile(User user) throws SQLException {
        Transfer feedback = new Transfer();
        String sql = "UPDATE user SET gender = '%s', contact = '%s', email = '%s' WHERE id = %d";
        System.out.println(String.format(sql, user.getGender(), user.getContact(), user.getEmail(), user.getId()));
        int affectedRow = stat.executeUpdate(String.format(sql, user.getGender(), user.getContact(), user.getEmail(), user.getId()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(EDIT_PROFILE_FAIL);
        }
        return feedback;
    }


    /**
     * 删除活动
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer deleteActivity(Activity activity) throws SQLException {
        Transfer feedback = new Transfer();
        int affectedRow = stat.executeUpdate(String.format("DELETE FROM activity WHERE id = %d", activity.getId()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(DELETE_ACTIVITY_FAIL);
        }
        return feedback;
    }


    /**
     * 用户退出已报名的活动
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer exitActivity(User user, Activity activity) throws SQLException {
        int id = activity.getId();
        String userName = user.getName();
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(String.format("SELECT jusers FROM activity WHERE id = %d", id));
        rs.next();
        String jusers = rs.getString("jusers");
        if (jusers.contains(userName)){
            jusers = jusers.replace(userName + " ", "");
            int affectedRow = stat.executeUpdate(String.format("UPDATE activity SET `join` = `join`-1, jusers = '%s' WHERE id = %d",
                    jusers, id));
            if (affectedRow == 1){
                feedback.setResult(SUCCESS);
            }else {
                feedback.setResult(EXIT_ACTIVITY_FAIL);
            }
        }else {
            feedback.setResult(NOT_YET_JOIN);
        }
        return feedback;
    }


    /**
     * 筛选用户
     * @param sql
     * @return
     * @throws SQLException
     */
    public static Transfer filterUsers(String sql) throws SQLException {
        Transfer feedback = new Transfer();
        ResultSet rs = stat.executeQuery(sql);
        List<User> users = new ArrayList<User>();
        while (rs.next()){
            users.add(
                    new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("gender"),
                            rs.getString("contact"),
                            rs.getString("email"),
                            rs.getString("realname"),
                            rs.getString("idcard"),
                            rs.getString("certificate")
                    )
            );
        }
        feedback.setResult(SUCCESS);
        feedback.setUserList(users);
        return feedback;
    }


    /**
     * 管理员设置用户的认证状态
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer setCertificateStatus(User user) throws SQLException {
        Transfer feedback = new Transfer();
        String sql = String.format("UPDATE user SET certificate = '%s' WHERE id = %d",
                user.getCertificate(), user.getId());
        int affectedRow = stat.executeUpdate(sql);
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(SET_CERTIFICATE_STATUS_FAIL);
        }
        return feedback;
    }


    /**
     * 管理员修改用户资料
     * @param user
     * @return
     */
    public static Transfer adminUpdateUserProfile(User user){
        Transfer feedback = new Transfer();
        int affectedRow = -1;
        String sql = String.format("UPDATE user SET name = '%s', password = '%s', gender = '%s', contact = '%s', email = '%s', realname = '%s', idcard = '%s' WHERE id = %d",
                user.getName(), user.getPw(), user.getGender(), user.getContact(), user.getEmail(), user.getRealName(), user.getIdCard(), user.getId());
        try {
            affectedRow = stat.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(ADMIN_UPDATE_USER_PROFILE_FAIL);
        }
        return feedback;
    }

    /**
     * 管理员删除用户
     * @param user
     * @return
     */
    public static Transfer deleteUser(User user){
        Transfer feedback = new Transfer();
        int affectedRow = -1;
        String sql = String.format("DELETE FROM user WHERE id = %d", user.getId());
        try {
            affectedRow = stat.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(DELETE_USER_FAIL);
        }
        return feedback;
    }

}
