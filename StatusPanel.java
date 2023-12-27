package MatchGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusPanel extends JPanel implements ActionListener {
    private Game game;
    private JLabel scoreLabel;
    private JLabel scoreChangeLabel;

    public StatusPanel(Game game, int panelHeight) {
        this.game = game;
        setPreferredSize(new Dimension(100, panelHeight));
        setBackground(Color.darkGray);
        Font mainFont = new Font("Arial", 0, 40);
        Font scoreChangeFont = new Font("Arial", 0, 60);
    
        //Thiết lập bảng điểm
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
    
        //Thiết lập bảng cập nhật điểm
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.darkGray);
        middlePanel.setPreferredSize(new Dimension(100, 300));
        scoreChangeLabel = new JLabel("");
        scoreChangeLabel.setForeground(Color.WHITE);
        scoreChangeLabel.setFont(scoreChangeFont);
        middlePanel.add(scoreChangeLabel);
    }
}