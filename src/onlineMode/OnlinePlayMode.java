package onlineMode;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Objects;

import view.Chessboard;

public class OnlinePlayMode {
    public final String ip;
    public final int targetPort;
    public final int serverPort;
    public Chessboard chessboard;
    public Thread thread_sed;
    public Thread thread_rec;
    public OnlinePlayMode (String ip1, int targetPort1, int serverPort, Chessboard chessboard) {
        this.ip = ip1;
        this.targetPort = targetPort1;
        this.serverPort = serverPort;
        this.chessboard = chessboard;

        initialSenderAndReceiver();
        this.startReceive();
    }

    public Sender sender;
    public Receiver receiver;

    public void send(String str){
        sender.str = str;
        sender.run();
    }

    public void initialSenderAndReceiver() {
        try {
            DatagramSocket socket = new DatagramSocket(this.serverPort);
            this.sender = new Sender(socket, this.ip, this.targetPort);
            this.receiver = new Receiver(socket){
                @Override
                public void action() {
                    super.action();
                    String old_str = chessboard.saveChessBoard2Str();
                    if (str.length()!=0 && !Objects.equals(old_str, str)) {
                        chessboard.loadChessBoardFromStr(str);
                        chessboard.chessGame.add(str);
                        if (chessboard.getScoreOfBlack()>=60||chessboard.getScoreOfRed()>=60){
                            chessboard.clickController.gameOverAndAsk();
                        }
                    }
                }
            };
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public void startReceive() {
        thread_rec = new Thread(receiver,"Receiver");
        thread_rec.start();
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {}
    }
}
