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
        Transfer feedback = new Transfer();
        int join = activity.getJoin() + 1;
        String jUsers = activity.getjUsers() + user.getName() + " ";
        int affectedRow = stat.executeUpdate(String.format("UPDATE activity SET join = %d, jusers = '%s' WHERE id = %d",
                join,jUsers, activity.getId()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
            activity.setJoin(join);
            activity.setjUsers(jUsers);
            feedback.setActivity(activity);
        }else {
            feedback.setResult(JOIN_FAIL);
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
        Transfer feedback = new Transfer();
        int checkIn = activity.getCheckIn() + 1;
        String cusers = activity.getcUsers() + user.getName() + " ";
        int affectedRow = stat.executeUpdate(String.format("UPDATE activity set checkin = %d, cusers = '%s' WHERE id = %d",
                checkIn,cusers,activity.getId()));
        if (affectedRow == 1){
            activity.setCheckIn(checkIn);
            activity.setcUsers(cusers);
            feedback.setActivity(activity);
            feedback.setResult(SUCCESS);
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
                            " ",
                            rs.getString("realname"),
                            rs.getString("idcard"),
                            rs.getString("gender"),
                            rs.getString("contact"),
                            rs.getString("certificate"),
                            rs.getString("email")
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


}
