import model.Agreement;
import server.DBConnect;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author bqliang
 */

public class Test implements Agreement {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {

        DBConnect.getStat().execute(String.format(
                "INSERT INTO test (time) VALUES ('%s')",
                new Timestamp(System.currentTimeMillis())));
    }
}
