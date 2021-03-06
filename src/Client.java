import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;

    public static ChessGame ch;
    public static void moveDone(Move mv) throws IOException {
        oos.writeObject(mv);
        oos.flush();
    }

     public static void main(String[] args) throws IOException {
         InetAddress inetAddr = InetAddress.getLocalHost();

         Socket server;

         try {
             server = new Socket(inetAddr, 22000);
         } catch (ConnectException e) {
             System.out.println("Could not connect to the server.");
             return;
         }

         Client.ois = new ObjectInputStream(server.getInputStream());

         String myColor = Client.ois.readUTF();
         System.out.println("I was assigned the color: " + myColor);


         ch = new ChessGame(myColor);

         try {
             Client.oos = new ObjectOutputStream(server.getOutputStream());
         } catch (SocketException e) {
            System.out.println("Connection was reset by the peer.");
            System.exit(0);
         }

         new Thread(new Runnable() {
             private final ObjectInputStream readStream;
             private final ChessGame game;
             {
                 this.readStream = ois;
                 this.game = ch;
             }
             @Override
             public void run() {
                 while (true) {
                     try {
                         if (server.isConnected()) {
                             try {
                                 Move mv = (Move) readStream.readObject();
                                 game.executeMove(mv);
                             } catch (EOFException e) {
                                 return;
                             }
                         }
                     } catch (IOException | ClassNotFoundException e) {
                         return;
                     }
                 }
             }
         }).start();
     }
}
