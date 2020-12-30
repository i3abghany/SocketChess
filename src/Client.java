import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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

        Socket server = new Socket(inetAddr, 22000);

        Client.ois = new ObjectInputStream(server.getInputStream());

        String myColor = Client.ois.readUTF();
        System.out.println("I was assigned the color: " + myColor);


        ch = new ChessGame(myColor);

        Client.oos = new ObjectOutputStream(server.getOutputStream());
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
