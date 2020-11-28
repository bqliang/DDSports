package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bqliang
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9960);
        System.out.println("--- 服务器启动 ---");

        while (true){
            Socket s = ss.accept();
            ServerThread serverThread = new ServerThread(s);
            new Thread(serverThread).start();
        }

    }
}
