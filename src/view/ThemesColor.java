package view;

import java.awt.*;
import java.util.Objects;

public class ThemesColor {
    public static Color chessPieceColor = Color.ORANGE;
    public static Color chessOuterLine = Color.DARK_GRAY;
    public static Color chessInnerLine = Color.LIGHT_GRAY;
    public static Color chessBoardColor = new Color(250, 220, 190);
    public static boolean brightOrDark = true;
    public static Color chessLatticeColor = Color.BLACK;

    public static void setThemeColor(String themeName) {
        if (Objects.equals(themeName, "classic")) {
            chessPieceColor = Color.ORANGE;
            chessOuterLine = Color.DARK_GRAY;
            chessInnerLine = Color.LIGHT_GRAY;
            chessBoardColor = new Color(250, 220, 190);
            brightOrDark = true;
            chessLatticeColor = Color.BLACK;
        } else if (Objects.equals(themeName, "neon")) {
            chessPieceColor = new Color(135, 47, 145, 255);
            chessOuterLine = Color.GREEN;
            chessInnerLine = Color.MAGENTA;
            chessBoardColor = new Color(99, 49, 146);
            brightOrDark = false;
            chessLatticeColor = new Color(0, 255, 239, 179);
        } else if (Objects.equals(themeName, "fire")) {
            chessPieceColor = new Color(154, 0, 0, 255);
            chessOuterLine = new Color(0, 0, 0, 255);
            chessInnerLine = new Color(246, 105, 0, 255);
            chessBoardColor = new Color(133, 55, 0, 255);
            chessLatticeColor = new Color(255, 218, 0, 178);
            brightOrDark = false;
        } else {
            System.out.println("The theme name is not valid. Please use avalableTheme() to get the avalable themes.");
        }
    }

    public static String[] avalableTheme() {
        return new String[]{"classic", "neon", "fire"};
    }
}
