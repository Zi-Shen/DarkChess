package view;

import controller.ClickController;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    //private GameController gameController;
    private static JLabel statusLabel;
    private static JLabel redScoreLabel;
    private static JLabel blackScoreLabel;
    public static boolean isOnline;
    private final Chessboard chessboard;
    public ChessGameFrame(int width, int height) {
        setTitle("Dark Chess - 2022 CS109 Project"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addHelloPage();

        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE) {
            // 设置棋格颜色
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(ThemesColor.chessLatticeColor);
                super.paintComponent(g);
            }
        };

        addChessboard();
        addLabel();
        addSaveButton();
        addLoadButton();
        addRestartButton();
        addThemeButton();
        addUndoButton();
        addExitReminder();
    }

    private void addHelloPage() {
        String[] options = {"Local Play","LAN Game"};
        int choice = JOptionPane.showOptionDialog(
                this,"Welcome!\nPlease choose game mode:",
                "Welcome!", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                options,options[0]
        );
        if (choice == 0) {
            isOnline = false;
        } else if (choice == 1) {
            isOnline = true;
            ClickController.ip = askWindow("ip");
            ClickController.serverPort = Integer.parseInt(askWindow("server port"));
            ClickController.targetPort = Integer.parseInt(askWindow("target port"));
        }

    }
    private String askWindow(String something) {
        String input = JOptionPane.showInputDialog(
                null, String.format("Please input %s",something), String.format("Input %s",something),
                JOptionPane.QUESTION_MESSAGE
        );
        if (input.length()==0) {
            askWindow(something);
        }
        return input;
    }

    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        //gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        addScoreLabel();
        addWhosTurnLabel();
    }

    public static JLabel getRedScoreLabel() {
        return redScoreLabel;
    }

    public static JLabel getBlackScoreLabel() { return blackScoreLabel; }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addWhosTurnLabel() {
        statusLabel = new JLabel("BLACK's TURN");
        statusLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10 - 40);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private final ArrayList<JButton> buttonList = new ArrayList<>();

    private void addScoreLabel() {
        redScoreLabel = new JLabel("RED's SCORE: 0");
        blackScoreLabel = new JLabel("BLACK's SCORE: 0");
        redScoreLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 20);
        blackScoreLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 40);
        redScoreLabel.setSize(200, 30);
        blackScoreLabel.setSize(200, 30);
        redScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        blackScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        add(redScoreLabel);
        add(blackScoreLabel);
    }
    private void addExitReminder() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener (new WindowAdapter()
        {
            @Override
            public void windowClosing ( WindowEvent e )
            {
                String[] options = {"Yes", "No"};
                int userChoose =  JOptionPane.showOptionDialog(null,"Want to quit?",
                        "Reminder",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                        null,options,options[1]);
                if (userChoose == 0)
                {
                    System.exit (0);
                }
            }
        });
    }

    private void addSaveButton() {
        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener((e) -> {
            System.out.println("Click save button");
            String name = JOptionPane.showInputDialog(null,
                    "Please enter the game name:","Save Game",JOptionPane.WARNING_MESSAGE);
            if (name.length()==0) {
                JOptionPane.showMessageDialog(null,
                        "Game name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                chessboard.saveGame2File(name);
            }
        });
        saveButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 160 + 80 * 0);
        saveButton.setSize(150, 30);
        saveButton.setFont(new Font("Rockwell", Font.BOLD, 16));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.DARK_GRAY);
        add(saveButton);
        buttonList.add(saveButton);
    }

    private void addLoadButton() {
        JButton loadButton = new JButton("Load Game");
        loadButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 160 + 80 * 1);
        loadButton.setSize(150, 30);
        loadButton.setFont(new Font("Rockwell", Font.BOLD, 16));
        loadButton.setForeground(Color.DARK_GRAY);
        loadButton.setBackground(Color.WHITE);
        add(loadButton);
        buttonList.add(loadButton);

        loadButton.addActionListener(e -> {
            System.out.println("Click load");
            String[] options = getFileListFromPath("gamedata\\");
            String option =  (String)JOptionPane.showInputDialog(null,"Please choose a game","Load Game",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            chessboard.readGameFromFile(option);
            ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
            ChessGameFrame.getRedScoreLabel().setText(String.format("RED's SCORE: %d", chessboard.getScoreOfRed()));
            ChessGameFrame.getBlackScoreLabel().setText(String.format("BLACK's SCORE: %d", chessboard.getScoreOfBlack()));
        });
    }

    private void addRestartButton() {
        JButton restartButton = new JButton("Restart");
        restartButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 160 + 80 * 2);
        restartButton.setSize(150, 30);
        restartButton.setFont(new Font("Rockwell", Font.BOLD, 16));
        restartButton.setBackground(Color.WHITE);
        restartButton.setForeground(Color.DARK_GRAY);
        add(restartButton);
        buttonList.add(restartButton);

        restartButton.addActionListener(e -> {
            System.out.println("Click restart");
            chessboard.initAllChessOnBoard();
            chessboard.updateFrameLabel();
        });
    }

    private void addUndoButton() {
        JButton undoButton = new JButton("Undo 1 step");
        undoButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 160 + 80 * 3);
        undoButton.setSize(150, 30);
        undoButton.setFont(new Font("Rockwell", Font.BOLD, 16));
        undoButton.setForeground(Color.DARK_GRAY);
        undoButton.setBackground(Color.WHITE);
        add(undoButton);
        buttonList.add(undoButton);
        undoButton.addActionListener((e) -> chessboard.undo1step());
    }

    private void addThemeButton() {
        JButton themeButton = new JButton("Theme Color");
        themeButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 160 + 80 * 4);
        themeButton.setSize(150, 30);
        themeButton.setFont(new Font("Rockwell", Font.BOLD, 16));
        themeButton.setForeground(Color.DARK_GRAY);
        themeButton.setBackground(Color.WHITE);
        add(themeButton);
        buttonList.add(themeButton);
        themeButton.addActionListener((e) -> askAndSetTheme());
    }

    public static String[] getFileListFromPath(String path) {
        // 创建file
        File dir = new File(path);
        // 获取当前目录下的文件列表
        File[] files = dir.listFiles();
        // 判断是否有隐藏文件，
        String[] validNames;
        ArrayList<String> validNameList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                // 如果数组里面是文件
                String fileName = file.getName();
                if (file.isFile() && Objects.equals("txt", fileName.substring(fileName.length() - 3))) {//则输出文件名
                    validNameList.add(fileName.substring(0, fileName.length()-4));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null,"The directory is empty!","Error", JOptionPane.ERROR_MESSAGE);
        }
        int counter = 0;
        validNames = new String[validNameList.size()];
        for (String name: validNameList) {
            validNames[counter] = name;
            counter += 1;
        }
        return validNames;
    }

    public void askAndSetTheme() {
        String[] options = ThemesColor.avalableTheme();
        String userChoose = (String) JOptionPane.showInputDialog(this,"Choose a theme to change:","Change Theme",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        for (String option : options) {
            if (option.equals(userChoose)) {
                ThemesColor.setThemeColor(option);
                if (ThemesColor.brightOrDark) {
                    this.getContentPane().setBackground(Color.WHITE);
                    getStatusLabel().setForeground(Color.BLACK);
                    getBlackScoreLabel().setForeground(Color.BLACK);
                    getRedScoreLabel().setForeground(Color.BLACK);
                    for (JButton button : buttonList) {
                        button.setBackground(Color.WHITE);
                        button.setForeground(Color.DARK_GRAY);
                    }
                } else {
                    this.getContentPane().setBackground(Color.BLACK);
                    getStatusLabel().setForeground(Color.WHITE);
                    getRedScoreLabel().setForeground(Color.WHITE);
                    getBlackScoreLabel().setForeground(Color.WHITE);
                    for (JButton button : buttonList) {
                        button.setBackground(Color.DARK_GRAY);
                        button.setForeground(Color.WHITE);
                    }
                }
            }
        }
        chessboard.repaintAll();
    }
}