import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {


    public Server() throws IOException {
        ObjectInputStream ois1;
        ObjectOutputStream oos1;

        ObjectInputStream ois2;
        ObjectOutputStream oos2;

        System.out.println("The server has started! Waiting for clients.");

        ServerSocket server = new ServerSocket(22000);

        Socket client1 = server.accept();
        InputStream is1 = client1.getInputStream();
        OutputStream os1 = client1.getOutputStream();


        oos1 = new ObjectOutputStream(os1);

        oos1.writeUTF("white");
        oos1.flush();
        System.out.println("Connected to client one! Assigned the color White.");


        Socket client2 = server.accept();

        InputStream is2 = client2.getInputStream();
        OutputStream os2 = client2.getOutputStream();

        oos2 = new ObjectOutputStream(os2);

        oos2.writeUTF("black");
        oos2.flush();
        System.out.println("Connected to client two! Assigned the color Black.");

        try {
            ois1 = new ObjectInputStream(is1);
        } catch (SocketException e) {
            System.out.println("Connection was reset by a client.");
            client1.close();
            return;
        }
        try {
            ois2 = new ObjectInputStream(is2);
        } catch (SocketException e) {
            System.out.println("Connection was reset by a client.");
            client2.close();
            return;
        }

        AtomicBoolean run1 = new AtomicBoolean(true);
        Thread th1 = new Thread(() -> {
            while(run1.get()) {
                try {
                    Move client2Move = (Move) ois2.readObject();
                    oos1.writeObject(client2Move);
                    oos1.flush();
                } catch (IOException | ClassNotFoundException e) {
                    run1.set(false);
                    try {
                        client1.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        AtomicBoolean run2 = new AtomicBoolean(true);
        Thread th2 = new Thread(() -> {
            while(run2.get()) {
                try {
                    try {
                        Move client1Move = (Move) ois1.readObject();
                        oos2.writeObject(client1Move);
                    } catch (SocketException e) {
                        System.out.println("Connection was reset by a client.");
                        return;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    run2.set(false);
                    try {
                        client2.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        th1.start();
        th2.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Server();
    }
}
