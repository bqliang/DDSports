package server;

import model.Agreement;
import model.Transfer;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author bqliang
 */

public class ServerThread implements Runnable, Agreement {

    private Socket s;
    private Transfer feedback;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerThread(Socket socket){
        s = socket;
    }

    @Override
    public void run() {
        try{
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
            Transfer receive = (Transfer) ois.readObject();

            switch (receive.getCommand()){
                case REGISTER:{
                    User user = receive.getUser();
                    feedback = DBHandler.register(user);
                    break;
                }

                case USER_LOGIN:{
                    User user = receive.getUser();
                    feedback = DBHandler.userLogin(user);
                    break;
                }

                case AUTHENTICATE:{
                    feedback = DBHandler.authenticate(receive.getUser());
                    break;
                }

                default:{

                }
            }

            oos.writeObject(feedback);
            oos.flush();
            close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 关闭相关资源
     * @throws IOException
     */
    private void close() throws IOException {
        ois.close();
        oos.close();
        s.close();
    }

}
