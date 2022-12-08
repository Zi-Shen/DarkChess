package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

public class SoldierChessComponent extends ChessComponent {
    public SoldierChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "兵";
        } else {
            name = "卒";
        }
        setPriorityLevel();
        setScore();
        setCode();
    }
    @Override
    public void setCode() {
        code = "z";
    }
    @Override
    public void setScore() {this.score = 1;}
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 0;
    }
    @Override
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent targetChess = chessboard[destination.getX()][destination.getY()];
        int thisX = this.getChessboardPoint().getX();
        int thisY = this.getChessboardPoint().getY();
        int destX = destination.getX();
        int destY = destination.getY();
        if (!targetChess.isRevealed && !(targetChess instanceof EmptySlotComponent)) {return false;}
        boolean positionIsOK = (Math.abs(thisX - destX) + Math.abs(thisY - destY)) == 1;
        if (targetChess instanceof GeneralChessComponent) {
            return positionIsOK;
        }
        return positionIsOK && targetChess.getPriorityLevel() <= this.priorityLevel;
    }
}
