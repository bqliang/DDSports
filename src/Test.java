import client.Logined;
import model.Agreement;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author bqliang
 */

public class Test implements Agreement {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        String sql = "SELECT * FROM activity WHERE status = '未开始' AND (sponsor = '%s' OR jusers LIKE '%s')";
        System.out.println(String.format(sql, "name", "%" + "name" + "%"));
    }
}
