import server.DBConnect;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

        ResultSet rs = statement.executeQuery("SELECT * FROM admin WHERE name = 'xuziqi'");
        System.out.println(rs.next());
    }
}
