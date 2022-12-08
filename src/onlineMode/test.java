package onlineMode;


import view.Chessboard;

public class test {
    public static void main(String[] args) {
        Chessboard cb1 = new Chessboard(4, 8);
        Chessboard cb2 = new Chessboard(4, 8);
        System.out.printf("cb1: %s\n",cb1.saveGame2Str());
        cb2.readGameFromFile("残局1");
        System.out.printf("cb2: %s\n",cb2.saveGame2Str());
        OnlinePlayMode omode = new OnlinePlayMode("10.25.2.7", 3444,3444,cb1);
        omode.send(cb2.saveGame2Str());
        OnlinePlayMode.sleep(100);
        System.out.printf("after cb1: %s\n",cb1.saveGame2Str());
        System.out.printf("after cb2: %s\n",cb2.saveGame2Str());
    }
}
