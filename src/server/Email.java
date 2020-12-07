package server;

import model.Transfer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * @author bqliang
 */

public class Email {

    static final String smtp = "smtp.office365.com";
    static final String username = "ddsports2020@outlook.com";
    static List<String> allLines;
    static String password;

    static {
        try {
            allLines = Files.readAllLines(Paths.get("D:/Code/Private Info.txt"));
            password = allLines.get(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Transfer send(String to) throws MessagingException {

        String code = getCode();

        Properties props = new Properties();
        // SMTP主机名
        props.put("mail.smtp.host", smtp);
        // 主机端口号
        props.put("mail.smtp.port", "587");
        // 用户认证
        props.put("mail.smtp.auth", "true");
        // 启用TLS加密
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // 设置debug模式便于调试:
        //session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ddsports2020@outlook.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("滴滴运动验证码登录", "UTF-8");
        message.setText("您好！验证码为："+code, "UTF-8");
        Transport.send(message);

        Transfer transfer = new Transfer();
        transfer.setCode(code);
        return transfer;
    }

    static private String getCode(){
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for (int i=0; i < 6 ; i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
