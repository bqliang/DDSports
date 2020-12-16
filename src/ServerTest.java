import model.Activity;
import model.User;
import server.DbConnect;
import server.DbHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ServerTest {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        int row = -2;
        Statement statement = DbConnect.getStat();
//        ResultSet rs = statement.executeQuery("SELECT count(name) FROM admin WHERE name = 'xuyan89798'");
//        boolean x = rs.next();
//        System.out.println("adssarsdfds     "+x);
//        if (x){
//            row = rs.getInt("count(name)");
//            System.out.println(row);
//        }

    }
}
