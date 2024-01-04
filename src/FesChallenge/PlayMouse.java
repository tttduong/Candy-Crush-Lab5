package src.FesChallenge;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PlayMouse extends JPanel implements MouseListener, MouseMotionListener {
    private BufferedImage backgroundImage, playImage, exitImage, settingImage;
    private JFrame frame;
    private boolean isClicked;
    private Point mousePos = new Point(-1, -1);
    private Rectangle area, area2, area3;
    private int play,exit,state, setting;

    public PlayMouse(Rectangle area, Rectangle area2, Rectangle area3, JFrame jFrame) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.area=area;
        this.frame=jFrame;
        this.area2=area2;
        this.area3 = area3;
        state=1;
        play=2;
        exit=3;
        setting=4;
        // Load the background image
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/src/res/StartScene-noMouse.png"));
            playImage=ImageIO.read(getClass().getResourceAsStream("/src/res/StartScene-MouseOnStart.png"));
            exitImage=ImageIO.read(getClass().getResourceAsStream("/src/res/StartScene-MouseOnExit.png"));
            settingImage=ImageIO.read(getClass().getResourceAsStream("/src/res/StartScene-MouseOnSetting.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e) {
        // Do nothing
    }

    public void mousePressed(MouseEvent e) {
        if (area.contains(e.getPoint())) {
            handleMouseEvent(e);
        } else if (area2.contains(e.getPoint())) {
            System.exit(0);
        } else if(area3.contains(e.getPoint())) {
            clickIntoSetting(e);
        }
    }
    public void handleMouseEvent(MouseEvent e) {
        frame.dispose();
        startNewGame();
    }
    private void startNewGame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new Game();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
    public void clickIntoSetting(MouseEvent e) {
        frame.dispose();
        intoSetting();
    }
    private void intoSetting() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new Game_2();
//            new Game();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }
    public void mouseReleased(MouseEvent e) {
        isClicked = false;
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        if (area.contains(point)) {
            state=play;
            repaint();
        } else if (area2.contains(point)) {
            state=exit;
            repaint();
        } else if(area3.contains(point)){   
            state=setting;
            repaint();
        } else{
            state=1;
            repaint();
        }
    }
    public void mouseDragged(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
       if(state==1) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==play){
            g.drawImage(playImage, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==exit){
            g.drawImage(exitImage, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==setting){
            g.drawImage(settingImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

