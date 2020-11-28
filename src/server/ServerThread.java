package server;

import model.Agreement;
import model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author bqliang
 */

public class ServerThread implements Runnable, Agreement {

    private Socket s;
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
            int command = ois.readInt();
            switch (command){

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
