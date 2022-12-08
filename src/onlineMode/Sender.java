package onlineMode;
import view.Chessboard;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;
import java.util.Scanner;

public class Sender implements Runnable {
    private DatagramSocket client;
    private int targetPort;
    public Sender(DatagramSocket client, String ip, int targetPort) {
        this.client = client;
        this.targetPort = targetPort;
        this.ip = ip;
    }
    public String str="";

    private String ip;

    public void run() {
        try {
            byte[] buf = str.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length,
                    InetAddress.getByName(ip), targetPort);
            client.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
