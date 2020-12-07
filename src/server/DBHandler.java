package server;

import model.Activity;
import model.Agreement;
import model.Transfer;
import model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DBHandler implements Agreement {

    static public Statement stat;

    static {
        try {
            stat = DBConnect.getStat();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册
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
        user.setId(emailRs.getInt("id"));
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
                feedback.setResult(PASSWORD_ERROR);
            }
        }else {
            feedback.setResult(ACCOUNT_NOT_EXIST);
        }
        return feedback;
    }


    /**
     * 用户邮箱登录
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
     * 用户申请认证
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer authenticate (User user) throws SQLException {
        Transfer feedback = new Transfer();
        stat.executeUpdate(String.format("UPDATE user SET realname = '%s', idcard = '%s', certificate = '审核中' WHERE id = %d",
                user.getRealName(), user.getIdCard(), user.getId())
        );
        feedback.setResult(SUCCESS);
        feedback.setUser(user);
        return feedback;
    }


    /**
     * 用户发起活动
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer createActivity(User user, Activity activity) throws SQLException {
        Transfer feedback = new Transfer();
        String sql = String.format("INSERT INTO activity (name,sponsor,starttime,endtime,place,recruit) VALUES ('%s','%s','%s','%s','%s',%d)",
                activity.getName(), user.getName(), activity.getStartTime(), activity.getEndTime(), activity.getPlace(), activity.getRecruit());
        int affectedRow = stat.executeUpdate(sql);
        if (affectedRow == 1){
            // 获取id
            ResultSet rs = stat.executeQuery(String.format("SELECT id FROM activity where name = '%s' AND sponsor = '%s' AND starttime ='%s' AND endtime = '%s'",
                    activity.getName(), activity.getSponsor(), activity.getStartTime(), activity.getEndTime()));
            rs.next();
            int id = rs.getInt("id");
            String setStatus = "CREATE EVENT set%sStatus%d\n" +
                    "    ON SCHEDULE AT '%s'\n" +
                    "    DO\n" +
                    "      UPDATE activity SET status = '%s' WHERE id = %d;";
            stat.execute(String.format(setStatus,
                    "Start", id, activity.getStartTime(), "已开始"), id);
            stat.execute(String.format(setStatus,
                    "End", id, activity.getEndTime(), "已结束"), id);
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
        String jUsers = activity.getJusers() + user.getName() + " ";
        int affectedRow = stat.executeUpdate(String.format("UPDATE activity SET join = %d, jusers = '%s' WHERE id = %d",
                join,jUsers, activity.getId()));
        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
            activity.setJoin(join);
            activity.setJusers(jUsers);
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
        String cusers = activity.getCusers() + user.getName() + " ";
        int affectedRow = stat.executeUpdate(String.format("UPDATE activity set checkin = %d, cusers = '%s' WHERE id = %d",
                checkIn,cusers,activity.getId()));
        if (affectedRow == 1){
            activity.setCheckIn(checkIn);
            activity.setCusers(cusers);
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
                            rs.getTimestamp("starttime"),
                            rs.getTimestamp("endtime"),
                            rs.getString("place"),
                            rs.getInt("recruit"),
                            rs.getInt("join"),
                            rs.getInt("checkin"),
                            rs.getString("jusers"),
                            rs.getString("cusers")
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
                            rs.getTimestamp("starttime"),
                            rs.getTimestamp("endtime"),
                            rs.getString("place"),
                            rs.getInt("recruit"),
                            rs.getInt("join"),
                            rs.getInt("checkin"),
                            rs.getString("jusers"),
                            rs.getString("cusers")
                    )
            );
        }
        feedback.setResult(SUCCESS);
        feedback.setActivityList(activities);
        return feedback;
    }




}
