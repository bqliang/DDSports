import model.Agreement;
import model.Transfer;
import model.User;
import server.Email;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.sql.SQLException;

/**
 * @author bqliang
 */

public class Test implements Agreement {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        try {
            Email.send("iwaslbq@gmail.com","120500");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
