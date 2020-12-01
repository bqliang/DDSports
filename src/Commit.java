import model.Agreement;
import model.Transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;

/**
 * @author bqliang
 */

public class Commit implements Agreement {

    static private int result;
    static private Transfer send;
    static private Transfer feedback;
    static private Socket socket;
    static private ObjectOutputStream oos;
    static private ObjectInputStream ois;

    static void set(Transfer send){
        Commit.send = send;
    }

    static Transfer start() throws IOException, ClassNotFoundException {
        init();
        oos.writeObject(send);
        oos.flush();
        feedback = (Transfer) ois.readObject();

        result = feedback.getResult();

        switch (send.getCommand()){
            case REGISTER:{
                if (result == SUCCESS){
                    System.out.println("注册成功");
                }else if (result == NAME_ALREADY_EXISTS){
                    System.out.println("name 已存在，请更换");
                }
                break;
            }

            case USER_LOGIN:{
                if (result == SUCCESS){
                    System.out.println("登录成功");
                }else if (result == PASSWORD_ERROR){
                    System.out.println("密码错误");
                }else if (result == ACCOUNT_NOT_EXIST){
                    System.out.println("账号不存在");
                }
                break;
            }

            case AUTHENTICATE:{
                if (result == SUCCESS){
                    System.out.println("已发起申请");
                }
                break;
            }

            default:{

            }
        }

        close();
        return feedback;
    }

    static private void init() throws IOException {
        Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
        socket = new Socket(address.getHostAddress(),9960);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    static private void close() throws IOException {
        oos.close();
        ois.close();
        socket.close();
    }
}
