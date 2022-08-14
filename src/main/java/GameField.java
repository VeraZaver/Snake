import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;

    private int appleX;
    private int appleY;
    private int appleX1;
    private int appleY1;
    private int appleX2;
    private int appleY2;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;

    private int points = 0;

    private Image apple;
    private Image dot;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private Timer timer;

    private boolean inGame = true;

    public void loadImage(){
        ImageIcon iia = new ImageIcon("apple.png");
        ImageIcon iid = new ImageIcon("dot.png");
        apple = iia.getImage();
        dot = iid.getImage();
    }

    public void createApple(){
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;

        while ((appleX==appleX1 && appleY==appleY1) || (appleX==appleX2 && appleY==appleY2)) {
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;
        }
    }

    public void createApple1(){
        appleX1 = new Random().nextInt(20)*DOT_SIZE;
        appleY1 = new Random().nextInt(20)*DOT_SIZE;
        while ((appleX==appleX1 && appleY==appleY1) || (appleX1==appleX2 && appleY1==appleY2)) {
            appleX1 = new Random().nextInt(20) * DOT_SIZE;
            appleY1 = new Random().nextInt(20) * DOT_SIZE;
        }
    }

    public void createApple2(){
        appleX2 = new Random().nextInt(20)*DOT_SIZE;
        appleY2 = new Random().nextInt(20)*DOT_SIZE;
        while ((appleX2==appleX1 && appleY2==appleY1) || (appleX==appleX2 && appleY==appleY2)) {
            appleX2 = new Random().nextInt(20) * DOT_SIZE;
            appleY2 = new Random().nextInt(20) * DOT_SIZE;
        }
    }

    public GameField(){
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 48;
            x[i] = 48 - i*DOT_SIZE;

        }

        timer = new Timer(300, this);
        timer.start();

        createApple();
        createApple1();
        createApple2();
    }

    public void checkApple(){
        if (appleX == x[0] && appleY == y[0]) {
            dots++;
            points++;
            createApple();
        }
        if (appleX1 == x[0] && appleY1 == y[0]) {
            dots++;
            points++;
            createApple1();
        }
        if (appleX2 == x[0] && appleY2 == y[0]) {
            dots++;
            points++;
            createApple2();
        }

    }

    public void checkCollision(){
        for (int i = dots-1; i > 3; i--) {
            if (x[0]==x[i] && y[0]==y[i]){
                inGame = false;
            }
        }
        if (x[0]>SIZE)
            inGame = false;
        if (y[0]>SIZE)
            inGame = false;
        if (x[0]<0)
            inGame = false;
        if (y[0]<0)
            inGame = false;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(apple, appleX1, appleY1, this);
            g.drawImage(apple, appleX2, appleY2, this);
            g.drawString(Integer.toString(points), 290, 32);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        if(!inGame){
            g.drawString("Game over", 150, 150);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }



    public void move() {
        for (int i = dots - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (right)
            x[0] += DOT_SIZE;
        if (left)
            x[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;

    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed (KeyEvent k){
            super.keyPressed(k);
            int key = k.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
