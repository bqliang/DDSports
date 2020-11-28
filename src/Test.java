import server.DBConnect;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bqliang
 */

public class Test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        ResultSet rs = DBConnect.getStat().executeQuery("SELECT sponsor FROM activity WHERE id = 1");
        while (rs.next()){
            String str = rs.getString("sponsor");
            System.out.println(str);
        }
    }
}
