import model.Agreement;
import model.Transfer;
import model.User;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.sql.SQLException;

/**
 * @author bqliang
 */

public class Test implements Agreement {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        User user = new User();
        user.setId(2);
        user.setRealName("梁斌强");
        user.setIdCard("44098");
        Transfer transfer = new Transfer();
        transfer.setCommand(AUTHENTICATE);
        transfer.setUser(user);
        Commit.set(transfer);
        Commit.start();
    }
}
