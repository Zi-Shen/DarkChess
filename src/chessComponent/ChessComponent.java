package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.ThemesColor;

import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent{
    protected String name;// 棋子名字：例如 兵，卒，士等

    protected ChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ThemesColor.chessPieceColor);
        g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
        //绘制棋子边框
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(ThemesColor.chessOuterLine);
        g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
        //绘制棋子填充色
        if (isRevealed) {
            //绘制棋子内径
            g2.setColor(ThemesColor.chessInnerLine);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(spacingLength+5, spacingLength+5, getWidth() - 2 * spacingLength-10, getHeight() - 2 * spacingLength-10);
            //绘制棋子被选中时状态
            if (isSelected()) {
                g2.setColor(ThemesColor.chessPieceColor);
                g2.fillOval(spacingLength-5, spacingLength-5, getWidth() - 2 * spacingLength+10, getHeight() - 2 * spacingLength+10);
                //绘制内径
                g2.setColor(ThemesColor.chessInnerLine);
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
                //绘制棋子外径
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(ThemesColor.chessOuterLine);
                g2.drawOval(spacingLength-5, spacingLength-5, getWidth() - 2 * spacingLength+10, getHeight() - 2 * spacingLength+10);
                // 绘制大一点的文字
                g.setColor(this.getChessColor().getColor());
                Font selectedFont = new Font(CHESS_FONT.getName(), CHESS_FONT.getStyle(), CHESS_FONT.getSize()+6);;
                g.setFont(selectedFont);
                g.drawString(this.name, this.getWidth() / 4 - 3, this.getHeight() * 2 / 3 + 3);
            } else {
                //绘制棋子文字
                g.setColor(this.getChessColor().getColor());
                g.setFont(CHESS_FONT);
                g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);
            }
//            if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
//                g.setColor(Color.RED);
//                g.setFont(new Font("黑体", Font.BOLD, 10));
//                g.drawString(String.valueOf(this.howManyDead), this.getWidth() / 4, this.getHeight() * 2 / 3);
//            }
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
