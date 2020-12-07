import model.Activity;
import model.User;
import server.DBConnect;
import server.DBHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ServerTest {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        int row = -2;
        Statement statement = DBConnect.getStat();
//        ResultSet rs = statement.executeQuery("SELECT count(name) FROM admin WHERE name = 'xuyan89798'");
//        boolean x = rs.next();
//        System.out.println("adssarsdfds     "+x);
//        if (x){
//            row = rs.getInt("count(name)");
//            System.out.println(row);
//        }

        DBHandler.createActivity(
                new User("bqliang","paw"),
                new Activity("羽毛球",
                        "梁斌强",
                        new Timestamp(System.currentTimeMillis()),
                        new Timestamp(System.currentTimeMillis()),
                        "中山大学南方学院",
                        10
                )
        );
    }
}
