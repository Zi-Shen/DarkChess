package view;

import chessComponent.SquareComponent;

import java.awt.*;
import java.util.Objects;

public class Themes {
    public static Color chessPieceColor = Color.ORANGE;
    public static Color chessOuterLine = Color.DARK_GRAY;
    public static Color chessInnerLine = Color.LIGHT_GRAY;
    public static Color chessBoardColor = new Color(250, 220, 190);
    public static boolean brightOrDark = true;
    public static Color chessLatticeColor = Color.BLACK;
    public static Color redChessColor = Color.RED;
    public static Color blackChessColor = Color.BLACK;
    public static Color deadChessLabelColor = Color.RED;

    public static void setTheme(String themeName) {
        if (Objects.equals(themeName, avalableTheme()[0])) {
            chessPieceColor = Color.ORANGE;
            chessOuterLine = Color.DARK_GRAY;
            chessInnerLine = Color.LIGHT_GRAY;
            chessBoardColor = new Color(250, 220, 190);
            brightOrDark = true;
            chessLatticeColor = Color.BLACK;
            SquareComponent.CHESS_FONT = new Font("宋体", Font.BOLD, 36);
            blackChessColor = new Color(0, 0, 0, 255);
            redChessColor = Color.RED;
            deadChessLabelColor = Color.RED;
        } else if (Objects.equals(themeName, avalableTheme()[1])) {
            chessPieceColor = new Color(135, 47, 145, 255);
            chessOuterLine = Color.GREEN;
            chessInnerLine = Color.MAGENTA;
            chessBoardColor = new Color(99, 49, 146);
            brightOrDark = false;
            chessLatticeColor = new Color(0, 255, 239, 179);
            SquareComponent.CHESS_FONT = new Font("幼圆", Font.BOLD,36);
            blackChessColor = new Color(0, 113, 211, 255);
            redChessColor = Color.RED;
            deadChessLabelColor = new Color(102, 38, 138, 255);
        } else if (Objects.equals(themeName, avalableTheme()[2])) {
            chessPieceColor = new Color(154, 0, 0, 255);
            chessOuterLine = new Color(0, 0, 0, 255);
            chessInnerLine = new Color(246, 105, 0, 255);
            chessBoardColor = new Color(133, 55, 0, 255);
            chessLatticeColor = new Color(255, 218, 0, 199);
            brightOrDark = false;
            SquareComponent.CHESS_FONT = new Font("楷体", Font.BOLD,36);
            blackChessColor = new Color(0, 0, 0, 255);
            redChessColor = Color.RED;
            deadChessLabelColor = new Color(108, 12, 12, 255);
        }  else if (Objects.equals(themeName, avalableTheme()[3])) {
            chessPieceColor = new Color(0, 0, 0, 255);
            chessOuterLine = new Color(104, 217, 39, 187);
            chessInnerLine = new Color(80, 110, 38, 255);
            chessBoardColor = new Color(0, 0, 0, 255);
            chessLatticeColor = new Color(33, 182, 28, 199);
            brightOrDark = false;
            SquareComponent.CHESS_FONT = new Font("等线", Font.PLAIN, 36);
            blackChessColor = new Color(255, 255, 255, 118);
            redChessColor = new Color(115, 25, 25);
            deadChessLabelColor = new Color(21, 189, 21, 123);
        } else {
            System.out.println("The theme name is not valid. Please use avalableTheme() to get the avalable themes.");
        }
    }

    public static String[] avalableTheme() {
        return new String[]{"classic", "neon", "fire", "eye-friendly"};
    }
}
