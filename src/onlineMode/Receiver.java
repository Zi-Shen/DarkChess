package onlineMode;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import view.Chessboard;
public class Receiver implements Runnable {
    private DatagramSocket server;
    public Receiver(DatagramSocket server) {
        this.server = server;
    }
    public String str="";
    //public String next_str="";
    public void run() {
        try {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while (true) {
                server.receive(packet);
                str = new String(packet.getData(), 0, packet.getLength());
                action();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void action() {
        System.out.printf("received: %s\n",str);
    }

}

