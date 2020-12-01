package server;

import model.Agreement;
import model.Transfer;
import model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String sql = "UPDATE user SET realname = '%s', idcard = '%s', certificate = '审核中' WHERE id = '%d'";
        int affectedRow = stat.executeUpdate(String.format(sql,realName,idCard,id));
        Transfer transfer = new Transfer();
        transfer.setResult(SUCCESS);
        transfer.setUser(user);
        return transfer;
    }


}
