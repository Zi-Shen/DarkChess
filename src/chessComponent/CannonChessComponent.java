package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

public class CannonChessComponent extends ChessComponent {
    public CannonChessComponent(ChessboardPoint chessboardPoint, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "炮";
        } else {
            name = "砲";
        }
        setPriorityLevel();
        setScore();
        setCode();
    }
    @Override
    public void setCode() {
        code = "p";
    }
    @Override
    public void setScore() {this.score = 5;}
    @Override
    public void setPriorityLevel() {
        this.priorityLevel = 1;
    }
    @Override
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        if (destinationChess.getChessboardPoint().getX()==this.getChessboardPoint().getX()) {
            int counter = 0;
            for (int y = Math.min(this.getChessboardPoint().getY(),destinationChess.getChessboardPoint().getY());
                 y <= Math.max(this.getChessboardPoint().getY(),destinationChess.getChessboardPoint().getY()); y++) {
                if (!(chessboard[this.getChessboardPoint().getX()][y] instanceof EmptySlotComponent)) {
                    counter += 1;
                }
            }
            if (destinationChess instanceof EmptySlotComponent) {
                return false;
            } else {
                return counter == 3;
            }
        } else if (destinationChess.getChessboardPoint().getY()==this.getChessboardPoint().getY()) {
            int counter = 0;
            for (int x = Math.min(this.getChessboardPoint().getX(),destinationChess.getChessboardPoint().getX());
                 x <= Math.max(this.getChessboardPoint().getX(),destinationChess.getChessboardPoint().getX()); x++) {
                if (!(chessboard[x][this.getChessboardPoint().getY()] instanceof EmptySlotComponent)) {
                    counter += 1;
                }
            }
            if (destinationChess instanceof EmptySlotComponent) {
                return false;
            } else {
                return counter == 3;
            }
        }
        return false;
    }

}

