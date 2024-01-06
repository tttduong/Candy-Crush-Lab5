package FesChallenge;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LosePanel extends JPanel implements MouseListener, MouseMotionListener {
    private LoseScene loseFrame;
    private Point mousepos = new Point(-1, -1);
    private Rectangle area, area2;
    private int play, exit, state;
    private BufferedImage backgroundImage, playImage, exitImage;
    private MatchPanel matchPanel;

    public LosePanel(LoseScene loseFrame, Rectangle area, Rectangle area2) {
        this.loseFrame = loseFrame;
        addMouseListener(this);
        addMouseMotionListener(this);
        this.area = area;
        this.area2 = area2;
        state = 1;
        play = 2;
        exit = 3;

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/YouLose-NoMouse.png"));
            playImage = ImageIO.read(getClass().getResourceAsStream("/res/YouLose-MouseOnPlayAgain.png"));
            exitImage = ImageIO.read(getClass().getResourceAsStream("/res/YouLose-MouseOnExit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (area.contains(e.getPoint())) {
            matchPanel.restart();
        } else if (area2.contains(e.getPoint())) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        if (area.contains(point)) {
            state = play;
            repaint();
        } else if (area2.contains(point)) {
            state = exit;
            repaint();
        } else {
            state = 1;
            repaint();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == 1) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (state == play) {
            g.drawImage(playImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (state == exit) {
            g.drawImage(exitImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
