package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

/**
 * 表示黑红车
 */
public class ChariotChessComponent extends ChessComponent {
    public ChariotChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "俥";
        } else {
            name = "車";
        }
        setPriorityLevel();
        setScore();
        setCode();
    }
    @Override
    public void setCode() {
        code = "c";
    }
    @Override
    public void setScore() {this.score = 5;}
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 2;
    }
}

