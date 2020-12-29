import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
//        InetAddress inetAddr = InetAddress.getLocalHost();
//
//        Socket server = new Socket(inetAddr, 22000);
//
//
//        DataInputStream dis = new DataInputStream(server.getInputStream());
//        DataOutputStream dos = new DataOutputStream(server.getOutputStream());
//
//        Scanner sc = new Scanner(System.in);

        ChessGame ch = new ChessGame();

//
//        Thread th = new Thread(() -> {
//            while (true) {
//                String ret;
//                try {
//                    ret = dis.readUTF();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        th.start();
//
//        while (!ch.isStalemate()) {
//            String give = sc.nextLine();
//            dos.writeUTF(give);
//        }
//
    }
}
