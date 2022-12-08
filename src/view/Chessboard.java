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
        //saveGame("data");
        //readGame("data");
    }
    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;
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
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     * @param chess1
     * @param chess2
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            addScoreByEat(chess2);
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
        scoreOfBlack = 0;
        scoreOfRed = 0;
        currentColor = ChessColor.BLACK;
        int[] elements_index = arange(ROW_SIZE*COL_SIZE);
        int[] random_index = indexShuffle(elements_index);
        int counter = 0;
        int n_chess4one = ROW_SIZE*COL_SIZE/2;
        int n_soldiers = 5;
        int n_chariots = 2;
        int n_cannons = 2;
        int n_horses = 2;
        int n_elephants = 2;
        int n_scholars = 2;
        int n_general = 1;
        for (int index : random_index) {
            int i = index / COL_SIZE;
            int j = index % COL_SIZE;
            ChessColor color;
            if (counter < n_chess4one) {
                color = ChessColor.RED;
            } else {
                color = ChessColor.BLACK;
            }
            SquareComponent squareComponent;
            if (counter%n_chess4one < n_soldiers) {
                squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter%n_chess4one - n_soldiers < n_chariots){
                squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter%n_chess4one - n_soldiers - n_chariots < n_cannons) {
                squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter%n_chess4one - n_cannons - n_chariots - n_soldiers < n_elephants) {
                squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter%n_chess4one - n_cannons - n_chariots - n_soldiers - n_elephants < n_horses) {
                squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else if (counter%n_chess4one - n_cannons - n_chariots - n_soldiers - n_elephants - n_horses < n_scholars) {
                squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            } else {
                squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), color, clickController, CHESS_SIZE);
            }
            squareComponent.setVisible(true);
            putChessOnBoard(squareComponent);
            squareComponent.repaint();
            counter += 1;
        }
        clickController.sendMyCB();
    }

    /**
     * 绘制棋盘格子
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    public void readGameFromFile(String gameName) {
        //将数组从文件中读取出来
        BufferedReader bufferedReader = null;
        //为保存的数组分配空间
        String[][] cbData = new String[ROW_SIZE][COL_SIZE];;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(String.format("gamedata\\%s.txt",gameName)));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            int i=0;
            //按行读取
            while((line = bufferedReader.readLine() )!= null){
                //将按行读取的字符串按空格分割，得到一个string数组
                String[] strings = line.split("\\t");
                //依次转换为int类型存入到分配好空间的数组中
                if (i>2) {
                    System.arraycopy(strings, 0, cbData[i-3], 0, strings.length);
                } else if (i==0) {
                    if (Objects.equals(strings[0], "BLACK")) {setCurrentColor(ChessColor.BLACK);}
                    else if (Objects.equals(strings[0], "RED")) {setCurrentColor(ChessColor.RED);}
                } else if (i==1) {setScoreOfBlack(Integer.parseInt(strings[0]));
                } else if (i==2) {setScoreOfRed(Integer.parseInt(strings[0]));}
                //行数加1
                i++;
            }
            for (int j = 0; j < ROW_SIZE; j++) {
                for (int k = 0; k < COL_SIZE; k++) {
                    ChessColor color;
                    if (Objects.equals(cbData[j][k].charAt(0), 'B')) {
                        color = ChessColor.BLACK;
                    } else{
                        color = ChessColor.RED;
                    }
                    boolean isRev;
                    isRev = !Objects.equals(cbData[j][k].charAt(2), '0');
                    SquareComponent squareComponent = new EmptySlotComponent(new ChessboardPoint(j,k), clickController, CHESS_SIZE);
                    switch (cbData[j][k].charAt(1)) {
                        case 'c' -> {
                            squareComponent = new ChariotChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                        case 'm' -> {
                            squareComponent = new HorseChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                        case 'x' -> {
                            squareComponent = new MinisterChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                        case 's' -> {
                            squareComponent = new AdvisorChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                        case 'j' -> {
                            squareComponent = new GeneralChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                        case 'z' -> {
                            squareComponent = new SoldierChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                        case 'p' -> {
                            squareComponent = new CannonChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                            squareComponent.setRevealed(isRev);
                        }
                    }
                    squareComponent.setVisible(true);
                    putChessOnBoard(squareComponent);
                    squareComponent.repaint();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        updateFrameLabel();
        clickController.sendMyCB();
    }

    public void saveGame2File(String gameName) {
        FileWriter out;
        File file = new File(String.format("gamedata\\%s.txt",gameName));
        String[][] cbCodes = new String[ROW_SIZE][COL_SIZE];
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                int isRevealed10;
                if (squareComponents[i][j].isRevealed()) {isRevealed10=1;}
                else {isRevealed10=0;}
                cbCodes[i][j] = String.format("%s%s%d",squareComponents[i][j].getChessColor().toString().charAt(0),
                        squareComponents[i][j].getCode(),isRevealed10);
            }
        }
        try {
            out = new FileWriter(file);
            out.write(currentColor.toString()+"\n");
            out.write(String.format("%d\n%d\n",getScoreOfBlack(),getScoreOfRed()));
            for (String[] cbCodeRow : cbCodes) {
                for (String s : cbCodeRow) {
                    out.write(s + "\t");
                }
                out.write("\r\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readGameFromStr(String str) {
        if (str.charAt(0)=='B') {setCurrentColor(ChessColor.BLACK);}
        else {setCurrentColor(ChessColor.RED);}
        setScoreOfBlack(Integer.parseInt(str.substring(1,3)));
        setScoreOfRed(Integer.parseInt(str.substring(3,5)));
        String substr;
        for (int i = 0; i < ROW_SIZE*COL_SIZE; i++) {
            ChessColor color;
            int j = i/COL_SIZE;
            int k = i%COL_SIZE;
            substr = str.substring(5+3*i,5+3*i+3);
            boolean isRev;
            isRev = !Objects.equals(substr.charAt(2), '0');
            SquareComponent squareComponent = new EmptySlotComponent(new ChessboardPoint(j,k), clickController, CHESS_SIZE);
            if (substr.charAt(0)=='B') {color = ChessColor.BLACK;}
            else {color = ChessColor.RED;}
            switch (substr.charAt(1)) {
                case 'c' -> {
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
                case 'm' -> {
                    squareComponent = new HorseChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
                case 'x' -> {
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
                case 's' -> {
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
                case 'j' -> {
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
                case 'z' -> {
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
                case 'p' -> {
                    squareComponent = new CannonChessComponent(new ChessboardPoint(j, k), color, clickController, CHESS_SIZE);
                    squareComponent.setRevealed(isRev);
                }
            }
            squareComponent.setVisible(true);
            putChessOnBoard(squareComponent);
            squareComponent.repaint();
        }
        updateFrameLabel();
    }
    public void updateFrameLabel() {
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", this.getCurrentColor().getName()));
        ChessGameFrame.getRedScoreLabel().setText(String.format("RED's SCORE: %d", this.getScoreOfRed()));
        ChessGameFrame.getBlackScoreLabel().setText(String.format("BLACK's SCORE: %d", this.getScoreOfBlack()));
    }
    public String saveGame2Str() {
        StringBuilder str = new StringBuilder(new String());
        str.append(getCurrentColor().toString().substring(0, 1));
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
        return str.toString();
    }
}
