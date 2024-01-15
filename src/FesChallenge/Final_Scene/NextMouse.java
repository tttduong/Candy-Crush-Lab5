package src.FesChallenge.Final_Scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import src.FesChallenge.Body_Scence.MainBoard;
import src.FesChallenge.Controls.MatchPanel;
import src.FesChallenge.Body_Scence.StatusPanel;
import src.Sound.SoundMusic;

public class NextMouse extends JPanel implements MouseListener, MouseMotionListener {
    // private NextScene gameWinFrame;
    JFrame frame;
    private MatchPanel matchPanel;
    private StatusPanel statusPanel;
    private Point mousePos = new Point(-1, -1);
    private Rectangle area,area2;
    private int play,exit,state;
    private BufferedImage backgroundImage,playImage,exitImage;
    SoundMusic sound = new SoundMusic("",1000);

    public NextMouse(Rectangle area, Rectangle area2, JFrame jFrame) {
        // this.gameWinFrame = gameWinFrame;
        addMouseListener(this);
        addMouseMotionListener(this);
        this.frame=jFrame;
        this.area=area;
        this.area2=area2;
        state=1;
        play = 2;
        exit=3;
        sound.playSound("src/Sound/Candy Crush Saga New Soundtrack - Main menu 2.wav");
        
        // Load the background image
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/src/res/win-NoMouse.png"));
            playImage=ImageIO.read(getClass().getResourceAsStream("/src/res/win-MouseOnPlayAgain.png"));
            exitImage=ImageIO.read(getClass().getResourceAsStream("/src/res/win-MouseOnExit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (area.contains(e.getPoint())) {
            handleMouseEvent(e);          
        } else if (area2.contains(e.getPoint())) {
            System.exit(0);
        }
    }
    public void handleMouseEvent(MouseEvent e) {
        frame.dispose();              
        startNewGame();
    }
    private void startNewGame() {
        try {
            new MainBoard();
            sound.stopMusic();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        if (area.contains(point)) {
            state=play;
            repaint();
        } else if (area2.contains(point)) {
            state=exit;
            repaint();
        } else{
            state=1;
            repaint();
        }
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
    }
}
