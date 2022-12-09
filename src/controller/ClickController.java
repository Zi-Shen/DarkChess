package controller;


import chessComponent.CannonChessComponent;
import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import model.ChessboardPoint;
import onlineMode.OnlinePlayMode;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;


public class ClickController {
    private final Chessboard chessboard;
    //测试
    private final OnlinePlayMode omode;
    private SquareComponent first;
    public static String ip;
    public static int targetPort;
    public static int serverPort;


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
        omode = new OnlinePlayMode(ip, targetPort, serverPort, chessboard);
    }

    public void onClick(ChessboardPoint cbPoint) {
        //判断第一次点击
        SquareComponent squareComponent = chessboard.getSquareComponents()[cbPoint.getX()][cbPoint.getY()];
        if (first == null) {
            if (handleFirst(cbPoint)) {
                squareComponent.setSelected(true);
                first = squareComponent;
                first.repaint();
            }
        } else {
            if (first == squareComponent) { // 再次点击取消选取
                squareComponent.setSelected(false);
                SquareComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(cbPoint)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, squareComponent);
                chessboard.clickController.swapPlayer();
                sendMyCB();
                chessboard.chessGame.add(chessboard.saveChessBoard2Str());
                first.setSelected(false);
                first = null;
                if (chessboard.getScoreOfBlack() >= 60 || chessboard.getScoreOfRed() >= 60) {
                    gameOverAndAsk();
                }
            }
        }
    }


    /**
     * @param point 目标选取的棋子的坐标
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessboardPoint point) {
        SquareComponent squareComponent = chessboard.getSquareComponents()[point.getX()][point.getY()];
        if (!squareComponent.isRevealed()) {
//            loadYourCB();
            squareComponent = chessboard.getSquareComponents()[point.getX()][point.getY()];
            squareComponent.setRevealed(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            sendMyCB();
            chessboard.chessGame.add(chessboard.saveChessBoard2Str());

            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param cbpoint first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessboardPoint cbpoint) {
//        loadYourCB();
        SquareComponent squareComponent = chessboard.getSquareComponents()[cbpoint.getX()][cbpoint.getY()];
        if (squareComponent instanceof EmptySlotComponent) {
            return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
        }
        // 颜色不一样 且 canMoveTo；
        if (squareComponent.isRevealed()) {
            return squareComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
        } else if (first instanceof CannonChessComponent) {
            return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
        }
        return false;
    }

    public void sendMyCB() {
        if (ChessGameFrame.isOnline) {
            String str = chessboard.saveChessBoard2Str();
            omode.send(str);
        }
    }

    public void swapPlayer() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
        ChessGameFrame.getRedScoreLabel().setText(String.format("RED's SCORE: %d", chessboard.getScoreOfRed()));
        ChessGameFrame.getBlackScoreLabel().setText(String.format("BLACK's SCORE: %d", chessboard.getScoreOfBlack()));
    }

    public void gameOverAndAsk() {
        String[] options = {"Play Again","Exit Game"};
        if (chessboard.getScoreOfBlack() >= 60) {
            int userChoose =  JOptionPane.showOptionDialog(null,"BLACK WON!",
                    "GAME OVER",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                    null,options,options[0]);
            System.out.println("BLACK WIN!");
            if (userChoose == 0) {
                chessboard.initAllChessOnBoard();
                ChessGameFrame.getRedScoreLabel().setText("RED's SCORE: 0");
                ChessGameFrame.getBlackScoreLabel().setText("BLACK's SCORE: 0");
            } else {System.exit(0);}
        } else if (chessboard.getScoreOfRed() >= 60) {
            //JOptionPane.showMessageDialog(null, "RED WIN!", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
            int userChoose =  JOptionPane.showOptionDialog(null,"RED WON!",
                    "GAME OVER",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                    null,options,options[0]);		//选择对话框*/
            // JOptionPane.showMessageDialog(null, "BLACK WIN!", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
            System.out.println("RED WIN!");
            if (userChoose == 0) {
                chessboard.initAllChessOnBoard();
                ChessGameFrame.getRedScoreLabel().setText("RED's SCORE: 0");
                ChessGameFrame.getBlackScoreLabel().setText("BLACK's SCORE: 0");
            } else {{System.exit(0);}}
            System.out.println("RED WIN!");
        }
    }

}
