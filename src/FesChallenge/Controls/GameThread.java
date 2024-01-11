package src.FesChallenge.Controls;



import javax.swing.*;

public class GameThread extends Thread {
    private MatchBoard matchBoard;
    private StatusPanel statusPanel;
    private MatchPanel matchPanel;

    private static int score;
    private static int goal = 50;
    private static int level = 1;

    private static boolean gameover;
    private static volatile boolean paused = false;
    private static int speed = 1000;


    private JFrame gameAreaFrame;
    private static boolean running;


    public GameThread(MatchBoard matchBoard, StatusPanel statusPanel) {
        this.matchBoard = matchBoard;
        this.statusPanel = statusPanel;
        running = false;
//  level=1;
  score=0;
  goal=50;
//        paused = false;
        gameAreaFrame = (JFrame) SwingUtilities.getRoot(matchBoard);
        gameAreaFrame.setFocusable(true);
    }

    @Override
    public void run() {
        if(score>=goal){
            Next();
        }else{
            gameAreaFrame.dispose();
            restart();
        }

    }
    public static void setScore()
    {
        score=0;
    }



    public void startGame() {
        if (!running) {
            running = true;
            start();
        }
    }

    public  static void stopGame() {
        running = false;
    }


    public static void setGameover() {
        gameover = false;
    }

  public static void ResetGoal(){
        goal=50;
  }
    public static int getScore() {
        return GameThread.score;
    }

    public static int getGoal() {
        return GameThread.goal;
    }

    public static void setGoal(int goal) {
        GameThread.goal = goal;
    }
    public static void setLevel(int goal) {
        GameThread.level = goal;
    }

    public static void setSpeed(int goal) {
        GameThread.speed = goal;
    }
    public static int getLevel() {
        return GameThread.level;
    }


    public static void  reset()
    {
        level=1;
    }
    private void Next() {
        new NextScene(matchBoard,this);
    }

    public void restart() {
//        Restart restart = new Restart();
        statusPanel.updateScore(0,0);
        matchPanel.restart();
    }
}
