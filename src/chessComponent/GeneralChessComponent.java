package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

public class GeneralChessComponent extends ChessComponent {
    public GeneralChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "帅";
        } else {
            name = "将";
        }
        setPriorityLevel();
        setScore();
        setCode();
    }
    @Override
    public void setCode() {
        code = "j";
    }
    @Override
    public void setScore() {this.score = 30;}
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 5;
    }
}

