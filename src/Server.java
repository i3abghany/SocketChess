import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() throws IOException {
        ServerSocket server = new ServerSocket(22000);
        Socket client1 = server.accept();
        Socket client2 = server.accept();

        DataInputStream readClient1 = new DataInputStream(client1.getInputStream());
        DataOutputStream writeClient1 = new DataOutputStream(client1.getOutputStream());

        DataInputStream readClient2 = new DataInputStream(client2.getInputStream());
        DataOutputStream writeClient2 = new DataOutputStream(client2.getOutputStream());

        Thread th1 = new Thread(() -> {
            while(true) {
                try {
                    writeClient1.writeUTF(readClient2.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread th2 = new Thread(() -> {
            while(true) {
                try {
                    writeClient2.writeUTF(readClient1.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        th1.start(); th2.start();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
