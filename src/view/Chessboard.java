package view;


import chessComponent.*;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;


/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent{


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);
        scoreOfBlack = 0;
        scoreOfRed = 0;
        initAllChessOnBoard();
    }
    // chessGame 用于记录所有的当前游戏的行棋步骤，执行initAllChessOnBoard时会初始化并且记录初始棋盘状态。
    public ArrayList<String> chessGame;
    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4 + 2;
    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;

    protected int scoreOfBlack;

    protected int scoreOfRed;

    public void setScoreOfBlack(int scoreOfBlack) {
        this.scoreOfBlack = scoreOfBlack;
    }

    public void setScoreOfRed(int scoreOfRed) {
        this.scoreOfRed = scoreOfRed;
    }

    public void addScoreByEat(SquareComponent chess) {
        if (chess.getChessColor() == ChessColor.BLACK) {
            scoreOfRed += chess.getScore();
        } else {
            scoreOfBlack += chess.getScore();
        }
    }

    public SquareComponent[][] getSquareComponents() {
        return squareComponents;
    }

    public int getScoreOfBlack() {
        return scoreOfBlack;
    }

    public int getScoreOfRed() {
        return scoreOfRed;
    }
    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }
    private final int[] deadNumOfBlack = new int[7];
    private final int[] deadNumOfRed = new int[7];

    // 更新死掉的棋子并且绘制
    private void updateDeadChess(ChessColor color, int index) {
        if (index == 0) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new GeneralChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new GeneralChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
        else if (index == 1) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new AdvisorChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new AdvisorChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
        else if (index == 2) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new MinisterChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new MinisterChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
        else if (index == 3) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new ChariotChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new ChariotChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
        else if (index == 4) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new HorseChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new HorseChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
        else if (index == 5) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new CannonChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new CannonChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
        else if (index == 6) {
            SquareComponent deadChess;
            if (color == ChessColor.RED){
                deadChess = new SoldierChessComponent(new ChessboardPoint(index,0), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfRed[index]), 10,16);
                    }
                };
            } else {
                deadChess = new SoldierChessComponent(new ChessboardPoint(7 - index,5), color, clickController, CHESS_SIZE) {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Themes.deadChessLabelColor);
                        g.fillOval(5,5,15,15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("黑体", Font.BOLD, 10));
                        g.drawString(String.valueOf(deadNumOfBlack[index]), 10,16);
                    }
                };
            }
            deadChess.setRevealed(true);
            putChessOnBoard(deadChess);
            deadChess.repaint();
        }
    }
    private void upDateDeadChessByKilling(SquareComponent chess2) {
        ChessComponent deadChess;
        if (chess2 instanceof GeneralChessComponent) {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[0] += 1;
                deadChess = new GeneralChessComponent(new ChessboardPoint(0, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[0]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[0] += 1;
                deadChess = new GeneralChessComponent(new ChessboardPoint(7, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[0]), 10,16);
                        }
                    }
                };
            }
        } else if (chess2 instanceof AdvisorChessComponent) {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[1] += 1;
                deadChess = new AdvisorChessComponent(new ChessboardPoint(0+1, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[1]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[1] += 1;
                deadChess = new AdvisorChessComponent(new ChessboardPoint(7-1, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[1]), 10,16);
                        }
                    }
                };
            }
        } else if (chess2 instanceof MinisterChessComponent) {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[2] += 1;
                deadChess = new MinisterChessComponent(new ChessboardPoint(0+2, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[2]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[2] += 1;
                deadChess = new MinisterChessComponent(new ChessboardPoint(7-2, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[2]), 10,16);
                        }
                    }
                };
            }
        } else if (chess2 instanceof ChariotChessComponent) {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[3] += 1;
                deadChess = new ChariotChessComponent(new ChessboardPoint(0+3, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[3]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[3] += 1;
                deadChess = new ChariotChessComponent(new ChessboardPoint(7-3, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[3]), 10,16);
                        }
                    }
                };
            }
        } else if (chess2 instanceof HorseChessComponent) {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[4] += 1;
                deadChess = new HorseChessComponent(new ChessboardPoint(0 + 4, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[4]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[4] += 1;
                deadChess = new HorseChessComponent(new ChessboardPoint(7 - 4, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[4]), 10,16);
                        }
                    }
                };
            }
        } else if (chess2 instanceof CannonChessComponent) {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[5] += 1;
                deadChess = new CannonChessComponent(new ChessboardPoint(0 + 5, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[5]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[5] += 1;
                deadChess = new CannonChessComponent(new ChessboardPoint(7 - 5, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[5]), 10,16);
                        }
                    }
                };
            }
        } else {
            if (chess2.getChessColor() == ChessColor.RED) {
                deadNumOfRed[6] += 1;
                deadChess = new SoldierChessComponent(new ChessboardPoint(0 + 6, 0), ChessColor.RED, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfRed[6]), 10,16);
                        }
                    }
                };
            } else {
                deadNumOfBlack[6] += 1;
                deadChess = new SoldierChessComponent(new ChessboardPoint(7 - 6, 5), ChessColor.BLACK, clickController, CHESS_SIZE){
                    @Override
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        if (getChessboardPoint().getY()==0 || getChessboardPoint().getY()==5) {
                            g.setColor(Themes.deadChessLabelColor);
                            g.fillOval(5,5,15,15);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("黑体", Font.BOLD, 10));
                            g.drawString(String.valueOf(deadNumOfBlack[6]), 10,16);
                        }
                    }
                };
            }
        }
        deadChess.setRevealed(true);
        putChessOnBoard(deadChess);
        deadChess.repaint();
    }

    /**
     * 交换chess1 chess2的位置
     * @param chess1 吃棋动作发起方
     * @param chess2 被吃的目标
     */
    public void eatChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            addScoreByEat(chess2);
            upDateDeadChessByKilling(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
        System.out.printf("Black's score: %d\n", scoreOfBlack);
        System.out.printf("Red's score: %d\n", scoreOfRed);
    }
    public int[] indexShuffle(int[] index_i) {
        Random rand = new Random();
        int[] index = Arrays.copyOf(index_i, index_i.length);
        for (int i = index.length; i > 0; i--) {
            int randInt = rand.nextInt(i);
            int temp = index[randInt];
            index[randInt] = index[i-1];
            index[i-1] = temp;
        }
        return index;
    }

    public int[] arange(int size) {
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = i;
        }
        return res;
    }


    public void repaintAll() {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                squareComponents[i][j].repaint();
            }
        }
        this.repaint();
    }
    //FIXME:   Initialize chessboard.
    public void initAllChessOnBoard() {
        for (int i = 0; i < 7; i++) {
            deadNumOfRed[i] = 0;
            deadNumOfBlack[i] = 0;
        }
        scoreOfBlack = 0;
        scoreOfRed = 0;
        currentColor = ChessColor.BLACK;
        int[] elements_index = arange(4*8);
        int[] random_index = indexShuffle(elements_index);
        int counter = 0;
        int n_chess4one = 4*8/2;
        int n_soldiers = 5;
        int n_chariots = 2;
        int n_cannons = 2;
        int n_horses = 2;
        int n_elephants = 2;
        int n_scholars = 2;
        int n_general = 1;
        for (int index : random_index) {
            int i = index / 4;
            int j = 1 + index % 4;
            ChessColor color;
            if (counter < n_chess4one) {
                color = ChessColor.RED;
            } else {
                color = ChessColor.BLACK;
            }
            SquareComponent squareComponent;
            if (counter % n_chess4one < n_soldiers) {
                squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter % n_chess4one - n_soldiers < n_chariots) {
                squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter % n_chess4one - n_soldiers - n_chariots < n_cannons) {
                squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter % n_chess4one - n_cannons - n_chariots - n_soldiers < n_elephants) {
                squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter % n_chess4one - n_cannons - n_chariots - n_soldiers - n_elephants < n_horses) {
                squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter % n_chess4one - n_cannons - n_chariots - n_soldiers - n_elephants - n_horses < n_scholars) {
                squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else {
                squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            }
            squareComponent.setVisible(true);
            putChessOnBoard(squareComponent);
            squareComponent.repaint();
            counter += 1;
        }
        for (int i = 0; i < ROW_SIZE; i++) {
            SquareComponent squareComponent = new EmptySlotComponent(new ChessboardPoint(i, 0), clickController, CHESS_SIZE);
            squareComponent.setVisible(true);
            putChessOnBoard(squareComponent);
            squareComponent.repaint();
            SquareComponent squareComponent1 = new EmptySlotComponent(new ChessboardPoint(i, 5), clickController, CHESS_SIZE);
            squareComponent1.setVisible(true);
            putChessOnBoard(squareComponent1);
            squareComponent1.repaint();
        }
        clickController.sendMyCB();
        chessGame = new ArrayList<>();
        chessGame.add(saveChessBoard2Str());
        System.out.printf("Game is initialized to: %s", chessGame.get(0));
    }

    /**
     * 绘制棋盘格子
     * @param g 棋盘
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(this.getWidth() / 6, 0, this.getWidth() * 2 / 3, this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    public void readGameFromFile(String gameName) {
        //将数组从文件中读取出来
        BufferedReader bufferedReader;
        //为保存的数组分配空间
        chessGame = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    new FileInputStream(String.format("gamedata\\%s.txt",gameName)));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            //按行读取
            while((line = bufferedReader.readLine() )!= null && line.length()!=0){
                //将按行读取的字符串按空格分割，得到一个string数组
                //String[] strings = line.split("\\t");
                //依次转换为int类型存入到分配好空间的数组中
                chessGame.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadChessBoardFromStr(chessGame.get(chessGame.size()-1));
        clickController.sendMyCB();
    }

    public void saveGame2File(String gameName) {
        FileWriter out;
        File file = new File(String.format("gamedata\\%s.txt",gameName));
        try {
            out = new FileWriter(file);
            for (String s : chessGame) {
                out.write(s + "\n");
            }
            out.write("\r\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadChessBoardFromStr(String str) {
        if (str.length()!=163) {
            JOptionPane.showMessageDialog(null,"Wrong chessboard.\nError code: 102",
                    "Error", JOptionPane.WARNING_MESSAGE); initAllChessOnBoard(); updateFrameLabel(); return;
        }
        if (str.charAt(0)=='B') {setCurrentColor(ChessColor.BLACK);}
        else if (str.charAt(0)=='R') {setCurrentColor(ChessColor.RED);}
        else {JOptionPane.showMessageDialog(null,"Missing chess player.\nError code: 104",
                "Error", JOptionPane.WARNING_MESSAGE); initAllChessOnBoard(); updateFrameLabel(); return;}
        setScoreOfBlack(Integer.parseInt(str.substring(1,3)));
        setScoreOfRed(Integer.parseInt(str.substring(3,5)));
        for (int i = 0; i < ROW_SIZE*COL_SIZE; i++) {
            ChessColor color;
            String substr;
            int j = i/COL_SIZE;
            int k = i%COL_SIZE;
            substr = str.substring(5+3*i,5+3*i+3);
            boolean isRev;
            isRev = !Objects.equals(substr.charAt(2), '0');
            if (substr.charAt(0)=='B') {color = ChessColor.BLACK;}
            else if (substr.charAt(0)=='R') {color = ChessColor.RED;}
            else if (substr.charAt(0)=='N') {color = ChessColor.NONE;}
            else {
                JOptionPane.showMessageDialog(null,"Wrong chess piece.\nError code: 103",
                        "Error", JOptionPane.WARNING_MESSAGE); initAllChessOnBoard(); updateFrameLabel(); return;
            }
            SquareComponent squareComponent;
            switch (substr.charAt(1)) {
                case 'c' -> squareComponent = new ChariotChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 'm' -> squareComponent = new HorseChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 'x' -> squareComponent = new MinisterChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 's' -> squareComponent = new AdvisorChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 'j' -> squareComponent = new GeneralChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 'z' -> squareComponent = new SoldierChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 'p' -> squareComponent = new CannonChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                case 'e' -> squareComponent = new EmptySlotComponent(new ChessboardPoint(j,k), clickController, CHESS_SIZE);
                default -> {
                    JOptionPane.showMessageDialog(null,"Wrong chess piece.\nError code: 103",
                            "Error", JOptionPane.WARNING_MESSAGE); initAllChessOnBoard(); updateFrameLabel(); return;
                }
            }
            squareComponent.setRevealed(isRev);
            squareComponent.setVisible(true);
            putChessOnBoard(squareComponent);
            squareComponent.repaint();
        }
        for (int i = 0; i < 7; i++) {
            deadNumOfBlack[i] = Integer.parseInt(str.substring(str.length()-14+i,str.length()-13+i));
            deadNumOfRed[i] = Integer.parseInt(str.substring(str.length()-7+i,str.length()-6+i));
            if (deadNumOfBlack[i] != 0) {
                updateDeadChess(ChessColor.BLACK, i);
            }
            if (deadNumOfRed[i] != 0) {
                updateDeadChess(ChessColor.RED, i);
            }
        }
        updateFrameLabel();
    }
    public void updateFrameLabel() {
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", this.getCurrentColor().getName()));
        ChessGameFrame.getRedScoreLabel().setText(String.format("RED's SCORE: %d", this.getScoreOfRed()));
        ChessGameFrame.getBlackScoreLabel().setText(String.format("BLACK's SCORE: %d", this.getScoreOfBlack()));
    }
    public String saveChessBoard2Str() {
        StringBuilder str = new StringBuilder();
        str.append(getCurrentColor().toString().charAt(0));
        if (getScoreOfBlack()>=10) {
            str.append(String.format("%d", getScoreOfBlack()));
        } else {
            str.append(String.format("0%d", getScoreOfBlack()));}
        if (getScoreOfRed()>=10) {
            str.append(String.format("%d", getScoreOfRed()));
        } else {
            str.append(String.format("0%d", getScoreOfRed()));}

        for (SquareComponent[] squareComponent : squareComponents) {
            for (SquareComponent component : squareComponent) {
                int isRevealed10;
                if (component.isRevealed()) {
                    isRevealed10 = 1;
                } else {
                    isRevealed10 = 0;
                }
                str.append(String.format("%s%s%d", component.getChessColor().toString().charAt(0),
                        component.getCode(), isRevealed10));
            }
        }
        for (int num: deadNumOfBlack) {
            str.append(num);
        }
        for (int num: deadNumOfRed) {
            str.append(num);
        }
        return str.toString();
    }
    public void undo1step() {
        if (chessGame.size()>1) {
            chessGame.remove(chessGame.size()-1);
            loadChessBoardFromStr(chessGame.get(chessGame.size()-1));
            clickController.sendMyCB();
        } else {
            JOptionPane.showMessageDialog(
                    null, "Cannot undo on a initial state.","Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
