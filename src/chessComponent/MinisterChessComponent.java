package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

/**
 * 表示黑红车
 */
public class MinisterChessComponent extends ChessComponent {
    public MinisterChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "相";
        } else {
            name = "象";
        }
        setPriorityLevel();
        setScore();
        setCode();

    }
    @Override
    public void setCode() {
        code = "x";
    }
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 3;
    }
    @Override
    public void setScore() {this.score = 5;}
}

