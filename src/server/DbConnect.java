package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author bqliang 数据库连接
 */

public class DbConnect {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Statement statement;

    static public Statement getStat() throws SQLException, IOException, ClassNotFoundException {
        if (statement == null){
            init();
        }
        return statement;
    }

    static private void init() throws ClassNotFoundException, SQLException, IOException {

        List<String> allLines = Files.readAllLines(Paths.get("D:/Code/Private Info.txt"));
        String address = allLines.get(0);
        String port = allLines.get(1);
        String user = allLines.get(2);
        String pw = allLines.get(3);
        String url = "jdbc:mysql://" + address +":" + port + "/ddsports?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(url,user,pw);
        statement = conn.createStatement();
    }
}
