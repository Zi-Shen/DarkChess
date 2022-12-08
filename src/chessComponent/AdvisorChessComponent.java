package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

public class AdvisorChessComponent extends ChessComponent {
    public AdvisorChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "仕";
        } else {
            name = "士";
        }
        setPriorityLevel();
        setScore();
        setCode();
    }
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 4;
    }
    @Override
    public void setScore() {this.score = 10;}
    @Override
    public void setCode() {
        code = "s";
    }
}

