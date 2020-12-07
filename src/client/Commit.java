package client;

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

    static private Transfer send;
    static private Transfer feedback;
    static private Socket socket;
    static private ObjectOutputStream oos;
    static private ObjectInputStream ois;

    public static void set(Transfer send){
        Commit.send = send;
    }

    public static Transfer start() throws IOException, ClassNotFoundException {
        init();
        oos.writeObject(send);
        oos.flush();
        feedback = (Transfer) ois.readObject();
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
