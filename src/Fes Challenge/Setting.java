package MatchGame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Setting extends JPanel implements MouseListener, MouseMotionListener {
    private BufferedImage noClickSettingImage, clickBackSetting, clickBackgroundOn, clickMatchOn;
    JFrame frame;
    private boolean isClicked;
    private Point mousePos = new Point(-1, -1);
    private Rectangle area, area2, area3;
    private int state, back, backgroundOn, matchOn;

    public Setting(Rectangle area, Rectangle area2, Rectangle area3, JFrame jFrame) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.area=area;
        this.frame=jFrame;
        this.area2=area2;
        this.area3 = area3;
        state=1;
        back=2;
        backgroundOn=3;
        matchOn=4;

        // Load the background image
        try {
            noClickSettingImage=ImageIO.read(getClass().getResourceAsStream("/res/Setting-noMouseOnBack - BMoffMMoff.png"));
            clickBackgroundOn=ImageIO.read(getClass().getResourceAsStream("/res/noMouseOnBack - BMonMMoff.png"));
            clickMatchOn=ImageIO.read(getClass().getResourceAsStream("/res/Setting-noMouseOnBack - BMoffMMon.png"));
            clickBackSetting=ImageIO.read(getClass().getResourceAsStream("/res/MouseOnBack - BMoffMMoff.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (area2.contains(e.getPoint())) {
            backToMain();
        }else if(area.contains(e.getPoint())){
            switchBackgroundMusic();
        }
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Pressed at: (" + x + ", " + y + ")");
    }
//     public void handleMouseEvent(MouseEvent e) {
// //        frame.dispose();
// //        backToMain();
//     }
    private void switchBackgroundMusic(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            state = backgroundOn;
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void backToMain() {
        frame.dispose();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new CandyCrush();
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
           state=backgroundOn;
            repaint();
        } else if (area2.contains(point)) {
            state=back;
            repaint();
        } 
        else if(area3.contains(point)){   
            state=matchOn;
            repaint();
        }
       else{
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

        if(state==1) {
            g.drawImage(noClickSettingImage, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==back){
            g.drawImage(clickBackSetting, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==backgroundOn){
            g.drawImage(clickBackgroundOn, 0, 0, getWidth(), getHeight(), this);
        }
        if(state==matchOn){
            g.drawImage(clickMatchOn, 0, 0, getWidth(), getHeight(), this);
        }
    }


}

