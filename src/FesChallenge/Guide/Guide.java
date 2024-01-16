package src.FesChallenge.Guide;

import src.FesChallenge.Main.CandyCrush;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Guide extends JPanel implements MouseListener, MouseMotionListener {
    private BufferedImage guideBackground,guideBackground_Back;
    JFrame frame;
;
    private Rectangle area;
    private int state, back;

    public Guide(Rectangle area, JFrame jFrame) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.area=area;
        this.frame=jFrame;
        state=1;
        back=2;

        // Load the background image
        try {
            guideBackground =ImageIO.read(getClass().getResourceAsStream("/src/res/Rules-NoTapMouseOnBack.png"));
            guideBackground_Back =ImageIO.read(getClass().getResourceAsStream("/src/res/Rules-TapMouseOnBack.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (area.contains(e.getPoint())) {
            // handleMouseEvent(e);
            backToMain();
        }
    }
    // public void handleMouseEvent(MouseEvent e) {
    //     frame.dispose();
    //     backToMain();
    // }
    private void backToMain() {
        frame.dispose();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new CandyCrush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        if (area.contains(point)) {
            state = back;
            repaint();
        } else {
            state = 1;
            repaint();
        }
    }
    public void mouseDragged(MouseEvent e) {}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(state==1) {
            g.drawImage(guideBackground, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==back){
            g.drawImage(guideBackground_Back, 0, 0, getWidth(), getHeight(), this);
        }
    }


}

