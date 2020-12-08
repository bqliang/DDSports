package client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bqliang
 */

public class Tools {

    static Pattern emailPattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    static Pattern idCardPattern = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
    static Pattern accountPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{4,15}$");
    static Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$");

    public static Boolean isEmail(String email){
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isIdCard(String idCard){
        Matcher matcher = idCardPattern.matcher(idCard);
        return matcher.matches();
    }

    public static Boolean isAccount(String account){
        Matcher matcher = accountPattern.matcher(account);
        return matcher.matches();
    }

    // 必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-10之间
    public static Boolean isPassword(String password){
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }
}
