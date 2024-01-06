package src.FesChallenge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class StatusPanel extends JPanel implements ActionListener {
    //biến game để gọi restart method 
    private Game game;
    //scorelabel thay đổi khi scoreChangeLable thay đổi 
    private JLabel scoreLabel;
    //scoreChangeLabel xuất hiện khi match 
    private JLabel scoreChangeLabel;
    //nút restart 
    private JButton restartButton;
    //nút thoát 
    private JButton quitButton;
    //nhãn goal
    private JLabel goalLabel;
    
    private Timer timer;
    private JLabel timeLabel;
    private int second, minute;
    private String ddSecond, ddMinute;
    private DecimalFormat decForm = new DecimalFormat("00");
    private MatchBoard matchBoard;
    private GameThread gameThread;

    //tạo label và button cho game 
    public StatusPanel(Game game, int panelHeight) {
        this.game = game;
        setPreferredSize(new Dimension(100, panelHeight));
        setBackground(Color.darkGray);
        Font mainFont = new Font("Arial", 0, 40);
        Font scoreChangeFont = new Font("Arial", 0, 60);

    
        //tạo panel điểm phần top của panel 
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.darkGray);
        topPanel.setPreferredSize(new Dimension(100,100));
        JLabel scoreTextLabel = new JLabel("Score");
        scoreTextLabel.setForeground(Color.WHITE);
        scoreTextLabel.setFont(mainFont);
        scoreLabel = new JLabel("0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(mainFont);
        topPanel.add(scoreTextLabel);
        topPanel.add(scoreLabel);

    
        //tạo panel điểm phần middle của panel 
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.darkGray);
        middlePanel.setPreferredSize(new Dimension(100, 300));
        scoreChangeLabel = new JLabel("");
        scoreChangeLabel.setForeground(Color.WHITE);
        scoreChangeLabel.setFont(scoreChangeFont);
        middlePanel.add(scoreChangeLabel);

        // Set up the Goal panels/labels
        JPanel middle2Panel = new JPanel();
        middle2Panel.setBackground(Color.darkGray);
        middle2Panel.setPreferredSize(new Dimension(100,100));
        JLabel goalTextLabel = new JLabel("Goal");
        goalTextLabel.setForeground(Color.WHITE);
        goalTextLabel.setFont(mainFont);
        goalLabel = new JLabel("10");
        goalLabel.setForeground(Color.WHITE);
        goalLabel.setFont(mainFont);
        middle2Panel.add(goalTextLabel);
        middle2Panel.add(goalLabel);
        
        // tạo panel đếm ngược thời gian
        JPanel timePanel = new JPanel();
        timePanel.setBackground(Color.PINK);
        timePanel.setPreferredSize(new Dimension(100, 100));
        JLabel timeTextLabel = new JLabel("Time");
        timeTextLabel.setForeground(Color.WHITE);
        timeTextLabel.setFont(mainFont);
        timeLabel = new JLabel("02:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(mainFont);
        timePanel.add(timeTextLabel);
        timePanel.add(timeLabel);
        second = 0;
        minute = 2;
        countdownTimer();
        timer.start();


        //tạo button 
        restartButton = new JButton("Restart");
        restartButton.addActionListener(this);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

    
        //add mọi function vô panel 
        add(topPanel);
        add(middlePanel);
        add(middle2Panel);
        add(timePanel);
        add(restartButton);
        add(quitButton);
    }


    //update điểm cộng dựa vào new score 
    public void updateScore(int newScore, int scoreDelta) {
        scoreLabel.setText(""+newScore);
        if(scoreDelta > 0)
            scoreChangeLabel.setText("+"+scoreDelta);
        else {
            scoreChangeLabel.setText("");
        }
    }

    // Hàm đếm ngược thời gian. Khi thời gian kết thúc, màn hình thông báo you lose
    public void countdownTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second--;
                ddSecond = decForm.format(second);
                ddMinute = decForm.format(minute);
                timeLabel.setText(ddMinute + ":" + ddSecond);

                if (second == -1) {
                    second = 59;
                    minute--;
                    ddSecond = decForm.format(second);
                    ddMinute = decForm.format(minute);
                    timeLabel.setText(ddMinute + ":" + ddSecond);
                }

                if (minute == 0 && second == 0) {
                    timer.stop();
                    gameThread.stopGame();
                    LoseScene loseFrame = new LoseScene(matchBoard, gameThread);
                }
            }
        });
    }
    
    //khi nhấn quit or restart thì thực hiện chức năng, nếu quit thì thoát, nếu restart thì bắt đầu lại 
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == restartButton) {
            game.restart();
            //reset thời gian
            second = 0;
            minute = 2;
            timeLabel.setText("02:00");
            timer.start();
        } else if(e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}
