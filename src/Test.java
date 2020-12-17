import client.Logined;
import model.Agreement;
import ui.EditProfile;
import ui.Login;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author bqliang
 */

public class Test implements Agreement {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        int a = 1;
        int b = a;
        System.out.println(a);
        System.out.println(b);
        b = 2;
        System.out.println(a);
        System.out.println(b);
    }
}
