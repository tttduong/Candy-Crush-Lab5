package src;//package MatchGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

        //tạo button 
        restartButton = new JButton("Restart");
        restartButton.addActionListener(this);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

    
        //add mọi function vô panel 
        add(topPanel);
        add(middlePanel);
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

    
    //khi nhấn quit or restart thì thực hiện chức năng, nếu quit thì thoát, nếu restart thì bắt đầu lại 
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == restartButton) {
            game.restart();
        } else if(e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}
