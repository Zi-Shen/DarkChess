package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Themes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 这个类是一个抽象类，主要表示8*4棋盘上每个格子的棋子情况。
 * 有两个子类：
 * 1. EmptySlotComponent: 空棋子
 * 2. ChessComponent: 表示非空棋子
 */
public abstract class SquareComponent extends JComponent{
    protected SquareComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        Point location = new Point(chessboardPoint.getY() * size + 3, chessboardPoint.getX() * size + 3);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.isRevealed = false;
        setPriorityLevel();
        setScore();
        setCode();
    }
    protected static int spacingLength;
    public static Font CHESS_FONT = new Font("宋体", Font.BOLD, 36);
    protected String code;

    public String getCode() {
        return code;
    }
    public void setCode() {
        this.code = "e";
    }

    /**
     * chessboardPoint: 表示8*4棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0)等等
     * chessColor: 表示这个棋子的颜色，有红色，黑色，无色三种
     * isRevealed: 表示是否翻转
     * selected: 表示这个棋子是否被选中
     */
    private ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;
    protected boolean isRevealed;
    private boolean selected;

    /**
     * handle click event
     */
    private final ClickController clickController;

    protected int priorityLevel;
    protected int score;

    public int getScore() {return score;}

    public void setScore() {this.score = 0;}

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel() {
        this.priorityLevel = -1;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean reveal) {
        isRevealed = reveal;
    }

    public static void setSpacingLength(int spacingLength) {
        SquareComponent.spacingLength = spacingLength;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(SquareComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this.getChessboardPoint());
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 1)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false。
     */
    //todo: Override this method for Cannon
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent targetChess = chessboard[destination.getX()][destination.getY()];
        int thisX = this.getChessboardPoint().getX();
        int thisY = this.getChessboardPoint().getY();
        int destX = destination.getX();
        int destY = destination.getY();
        if (!targetChess.isRevealed && !(targetChess instanceof EmptySlotComponent)) {return false;}
        return (Math.abs(thisX-destX)+Math.abs(thisY-destY))==1 &&
                targetChess.getPriorityLevel() <= this.priorityLevel;
    }


    @Override
    public void paintComponent(Graphics g) {
        if (getChessboardPoint().getY()!=0 && getChessboardPoint().getY()!=5) {
            System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            g.setColor(Themes.chessBoardColor);
            g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        }
    }
}
