package server;

import model.Activity;
import model.Agreement;
import model.Transfer;
import model.User;

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
        String name = user.getName();
        String pw = user.getPw();

        String sql = """
                INSERT INTO user (name, password) SELECT
                	'%s',
                	'%s'
                FROM
                	DUAL
                WHERE
                	NOT EXISTS (
                		SELECT
                			name
                		FROM
                			user
                		WHERE
                			name = '%s'
                	);
                """;
        sql = String.format(sql,name,pw,name);
        int affectedRow = stat.executeUpdate(sql);
        Transfer feedback = new Transfer();

        if (affectedRow == 1){
            feedback.setResult(SUCCESS);
        }else {
            feedback.setResult(NAME_ALREADY_EXISTS);
        }
        return feedback;
    }


    /**
     * 用户登录
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer userLogin(User user) throws SQLException {
        int id;
        String name, pw, realName, idCard, gender, contact, certificate;
        User foundUser;
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM user WHERE name = '%s'";
        sql = String.format(sql,user.getName());
        ResultSet rs = stat.executeQuery(sql);

        if (rs.next()){
            pw = rs.getString("password");
            if (user.getPw().equals(pw)){
                id = rs.getInt("id");
                name = rs.getString("name");
                pw = rs.getString("password");
                realName = rs.getString("gender");
                idCard = rs.getString("idcard");
                gender = rs.getString("gender");
                contact = rs.getString("contact");
                certificate = rs.getString("certificate");
                foundUser = new User(id,name,pw,realName,idCard,gender,contact,certificate);
                transfer.setUser(foundUser);
            }else {
                transfer.setResult(PASSWORD_ERROR);
            }
        }else {
            transfer.setResult(ACCOUNT_NOT_EXIST);
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
        int id = user.getId();
        String realName = user.getRealName();
        String idCard = user.getIdCard();
        String sql = "UPDATE user SET realname = '%s', idcard = '%s', certificate = '待审核' WHERE id = '%d'";
        int affectedRow = stat.executeUpdate(String.format(sql,realName,idCard,id));
        Transfer transfer = new Transfer();
        transfer.setResult(SUCCESS);
        transfer.setUser(user);
        return transfer;
    }


    /**
     * 用户发起活动
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer createActivity(User user, Activity activity) throws SQLException {
        Transfer transfer = new Transfer();
        String activityName = activity.getName();
        Timestamp startTime = activity.getStartTime();
        Timestamp endTime = activity.getEndTime();
        String place = activity.getPlace();
        int recruit = activity.getRecruit();
        String sponsor = user.getName();
        String sql = "INSERT INTO activity (name,sponsor,starttime,endtime,place,recruit) VALUES ('%s','%s',%s,'%s','%s','%d')";
        sql = String.format(sql,activityName,sponsor,startTime,endTime,place,recruit);
        int affectedRow = stat.executeUpdate(sql);
        if (affectedRow == 1){
            transfer.setResult(SUCCESS);
        }else {
            transfer.setResult(CREATE_ACTIVITY_FAIL);
        }
        return transfer;
    }


    /**
     * 用户加入活动
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer join(User user, Activity activity) throws SQLException {
        Transfer transfer = new Transfer();
        String name = user.getName();
        int activityId = activity.getId();
        int join = activity.getJoin() + 1;
        String jusers = activity.getJusers() + name + " ";
        String sql = "UPDATE activity SET join = %d, jusers = '%s' WHERE id = '%d'";
        sql = String.format(sql,join,jusers,activityId);
        int affectedRow = stat.executeUpdate(sql);
        if (affectedRow == 1){
            transfer.setResult(SUCCESS);
            activity.setJoin(join);
            activity.setJusers(jusers);
            transfer.setActivity(activity);
        }else {
            transfer.setResult(JOIN_FAIL);
        }
        return transfer;
    }


    /**
     * 用户打卡
     * @param user
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer checkIn(User user, Activity activity) throws SQLException {
        Transfer transfer = new Transfer();
        String name = user.getName();
        int id = activity.getId();
        int checkIn = activity.getCheckIn() + 1;
        String cusers = activity.getCusers() + name + " ";
        String sql  = "UPDATE activity set checkin = '%d', cusers = '%s' WHERE id = '%d'";
        sql = String.format(sql,checkIn,cusers,id);
        int affectedRow = stat.executeUpdate(sql);
        if (affectedRow == 1){
            activity.setCheckIn(checkIn);
            activity.setCusers(cusers);
            transfer.setActivity(activity);
            transfer.setResult(SUCCESS);
        }else {
            transfer.setResult(CHECK_IN_FAIL);
        }
        return transfer;
    }


    /**
     * 浏览活动
     * @param activity
     * @return
     * @throws SQLException
     */
    public static Transfer viewActivity(Activity activity) throws SQLException {
        Transfer transfer = new Transfer();
        int id = activity.getId();
        String sql = "SELECT * FROM activity WHERE id = '%s'";
        sql = String.format(sql,id);
        ResultSet rs = stat.executeQuery(sql);
        if (rs.next()){
            String name = rs.getString("name");
            String sponsor = rs.getString("sponsor");
            Timestamp startTime = rs.getTimestamp("starttime");
            Timestamp endTime = rs.getTimestamp("endtime");
            String place = rs.getString("place");
            int recruit = rs.getInt("recruit");
            int join = rs.getInt("join");
            int checkIn = rs.getInt("checkin");
            String jusers = rs.getString("jusers");
            String cusers = rs.getString("cusers");
            activity = new Activity(id,name,sponsor,startTime,endTime,place,recruit,join,checkIn,jusers,cusers);
            transfer.setActivity(activity);
            transfer.setResult(SUCCESS);
        }else {
            transfer.setResult(VIEW_ACTIVITY_FAIL);
        }
        return transfer;
    }


    /**
     * 查看用户资料
     * @param user
     * @return
     * @throws SQLException
     */
    public static Transfer viewUser(User user) throws SQLException {
        Transfer transfer = new Transfer();
        int id = user.getId();
        String sql = "SELECT * FROM user WHERE id ='%s'";
        sql = String.format(sql,id);
        ResultSet rs = stat.executeQuery(sql);
        if (rs.next()){
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String contact = rs.getString("contact");
            String realName = rs.getString("realname");
            String idCard = rs.getString("idcard");
            String certificate = rs.getString("certificate");
            user = new User(id,name," ",realName,idCard,gender,contact,certificate);
            transfer.setUser(user);
            transfer.setResult(SUCCESS);
        }else {
            transfer.setResult(VIEW_USER_FAIL);
        }
        return transfer;
    }


    /**
     * 浏览申请认证的用户
     * @return
     * @throws SQLException
     */
    public static Transfer viewCertificateUsers() throws SQLException {
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM user WHERE certificate = '待审核'";
        ResultSet rs = stat.executeQuery(sql);
        List<User> users = new ArrayList<User>();
        while (rs.next()){
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    " ",
                    rs.getString("realname"),
                    rs.getString("idcard"),
                    rs.getString("gender"),
                    rs.getString("contact"),
                    rs.getString("certificate")
            );
            users.add(user);
        }
        transfer.setUserList(users);
        return  transfer;
    }


    /**
     * 浏览所有活动
     * @return
     * @throws SQLException
     */
    public static Transfer viewActivities() throws SQLException {
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM activity";
        ResultSet rs = stat.executeQuery(sql);
        List<Activity> activities = new ArrayList<Activity>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String sponsor = rs.getString("sponsor");
            Timestamp startTime = rs.getTimestamp("starttime");
            Timestamp endTime = rs.getTimestamp("endtime");
            String place = rs.getString("place");
            int recruit = rs.getInt("recruit");
            int join = rs.getInt("join");
            int checkIn = rs.getInt("checkin");
            String jusers = rs.getString("jusers");
            String cusers = rs.getString("cusers");
            Activity activity = new Activity(id,name,sponsor,startTime,endTime,place,recruit,join,checkIn,jusers,cusers);
            activities.add(activity);
        }
        transfer.setActivityList(activities);
        return transfer;
    }

}
