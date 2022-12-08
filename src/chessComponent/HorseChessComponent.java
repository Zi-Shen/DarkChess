package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

/**
 * 表示黑红车
 */
public class HorseChessComponent extends ChessComponent {
    public HorseChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "馬";
        } else {
            name = "傌";
        }
        setPriorityLevel();
        setScore();
        setCode();
    }
    @Override
    public void setCode() {
        code = "m";
    }
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 1;
    }
    @Override
    public void setScore() {this.score = 5;}
}

