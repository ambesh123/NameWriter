package namewriter;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author ambesht
 */
public class Board extends JFrame implements Runnable {
    
    final static int FRAME_WIDTH = 1500;
    final static int FRAME_HEIGHT = 1000;
    final static int FRAME_MARGIN = 50;
    final static int CHAR_WIDTH = 120;
    final static int CHAR_HEIGHT = 120;
    final static int CHAR_MARGIN = 20;
    final static int SLEEP_TIME = 5;
    final static int CHARS_IN_A_ROW = (FRAME_WIDTH - 2*FRAME_MARGIN)/CHAR_WIDTH;
    final static int MAX_CHARS = CHARS_IN_A_ROW * ((FRAME_WIDTH - 2*FRAME_MARGIN) / CHAR_WIDTH);
    final static Color BACKGROUND_COLOR = Color.WHITE;
    
    final Thread frameUpdateThread;
    
    Color penColor = Color.WHITE;
    
    int currentBlock;
    int penRadius;
    int penX;
    int penY;
    boolean penDown;
    
    Board() {
        penRadius = 5;
        currentBlock = 0;
        this.setTitle("Name Writer");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setVisible(true);
        this.setLocation(0, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(BACKGROUND_COLOR);
        penDown = false;
        
        frameUpdateThread = new Thread(this);
        frameUpdateThread.start();
    }
    
    @Override
    public void paint(Graphics g) {
        drawCircle(g);
    }
    
    private void drawCircle(Graphics g) {
        g.fillOval(penX - penRadius, penY - penRadius, 2*penRadius, 2*penRadius);
    }

    @Override
    public void run() {
        while(true) {
            this.repaint();
            sleep(SLEEP_TIME/2);
        }
    }
    
    private int getBlockX(int blockNo) {
        return FRAME_MARGIN + CHAR_WIDTH * (currentBlock % CHARS_IN_A_ROW);
    }
    
    private int getBlockY(int blockNo) {
        return FRAME_MARGIN + CHAR_HEIGHT * (currentBlock/CHARS_IN_A_ROW);
    }
    
    private void sleep(int sleepTime) {
        if(sleepTime <= 0)return;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clearBoard() {
        Graphics g = this.getGraphics();
        if(g == null)return;
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        g.setColor(penColor);
        currentBlock = 0;
    }
    
    public void drawString(String str) {
        for(int i = 0; i < str.length(); ++i) {
            switch(str.charAt(i)) {
                case 'A': drawA();
                    break;
                case 'B': drawB();
                    break;
                case 'C': drawC();
                    break;
                case 'D': drawD();
                    break;
                case 'E': drawE();
                    break;
                case 'F': drawF();
                    break;
                case 'G': drawG();
                    break;
                case 'H': drawH();
                    break;
                case 'I': drawI();
                    break;
                case 'J': drawJ();
                    break;
                case 'K': drawK();
                    break;
                case 'L': drawL();
                    break;
                case 'M': drawM();
                    break;
                case 'N': drawN();
                    break;
                case 'O': drawO();
                    break;
                case 'P': drawP();
                    break;
                case 'Q': drawQ();
                    break;
                case 'R': drawR();
                    break;
                case 'S': drawS();
                    break;
                case 'T': drawT();
                    break;
                case 'U': drawU();
                    break;
                case 'V': drawV();
                    break;
                case 'W': drawW();
                    break;
                case 'X': drawX();
                    break;
                case 'Y': drawY();
                    break;
                case 'Z': drawZ();
                    break;
                case ' ': drawSpace();
                    break;
                case '.': clearBoard();
                    break;
                default: throw new UnsupportedOperationException("Character not supported");
            }
            if(currentBlock > MAX_CHARS){
                clearBoard();
            }
        }
    }
    
    private void drawArc(int cx, int cy, int radius, 
            int startAngle, int endAngle, boolean anticlockwise) {
        
        if(!penDown)return;
        
        if((startAngle < endAngle && anticlockwise) || (startAngle > endAngle && !anticlockwise))return;
        
        int inc = anticlockwise ? -5 : 5;
        
        for(int a = startAngle; a != endAngle; a += inc) {
            double r = (Math.PI * a)/180.0;
            penX = cx + (int)(Math.cos(r) * radius);
            penY = cy + (int)(Math.sin(r) * radius);
            sleep(SLEEP_TIME);
        }
        
    }
    
    private void drawSlantLine(int x1, int y1, int x2, int y2) {
        double slope = ((double)(y2 - y1)) / (x2 - x1);
        int xinc = Math.max(1, penRadius/2);
        
        penX = x1;
        penY = y1;
        sleep(SLEEP_TIME);
        
        if(x2 < x1) {
            xinc = -xinc;
            while(penX > x2) {
                penX += xinc;
                penY += (int) (slope * xinc);
                sleep(SLEEP_TIME);
            }
        } else {
            while(penX < x2) {
                penX += xinc;
                penY += (int) (slope * xinc);
                sleep(SLEEP_TIME);
            }
        }
        
    }
    
    private void drawVerticalLine(int x, int y1, int y2) {
        penX = x;
        penY = y1;
        int yinc = Math.max(1, penRadius/2);
        if(y2 < y1) yinc = -yinc;
        
        sleep(SLEEP_TIME);
        
        while(penY != y2) {
            penY += yinc;
            sleep(SLEEP_TIME);
        }
    }
    
    private void drawHorizontalLine(int x1, int x2, int y) {
        penX = x1;
        penY = y;
        int xinc = Math.max(1, penRadius/2);
        sleep(SLEEP_TIME);
        if(x2 < x1) {
            xinc = -xinc;
            while(penX > x2) {
                penX += xinc;
                sleep(SLEEP_TIME);
            }
        } else {
            while(penX < x2) {
                penX += xinc;
                sleep(SLEEP_TIME);
            }
        }
    }
    
    private void drawSpace() {
        ++currentBlock;
    }
    
    private void drawA() {
        
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        
        penDown = true;
        
        sleep(SLEEP_TIME);
        
        drawSlantLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                x + CHAR_WIDTH/2, y + CHAR_MARGIN);
        
        drawSlantLine(x + CHAR_WIDTH/2, y + CHAR_MARGIN, 
                x + CHAR_WIDTH - CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN + (CHAR_HEIGHT-2*CHAR_MARGIN)/4, 
                x + CHAR_WIDTH - (CHAR_HEIGHT-2*CHAR_MARGIN)/2, y + CHAR_HEIGHT/2);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawB() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, x + CHAR_MARGIN + CHAR_WIDTH/4,
                y + CHAR_MARGIN);
        
        drawArc(x + CHAR_MARGIN+ CHAR_WIDTH/4, y + CHAR_MARGIN + (CHAR_HEIGHT - 2*CHAR_MARGIN)/4,
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/4, -90, 90, false);
        
        drawHorizontalLine(x + CHAR_MARGIN + CHAR_WIDTH/4, x + CHAR_MARGIN,
                y + CHAR_HEIGHT/2);
        
        drawArc(x + CHAR_MARGIN+ CHAR_WIDTH/4, y + CHAR_MARGIN + (CHAR_HEIGHT - 2*CHAR_MARGIN)*3 /4,
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/4, -90, 90, false);
        
        drawHorizontalLine(x + CHAR_MARGIN + CHAR_WIDTH/4, x + CHAR_MARGIN,
                y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawC() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_HEIGHT/2, CHAR_WIDTH/2 - CHAR_MARGIN, 300, 60, true);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawD() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int ydec = Math.max(penRadius/2, 1);
        final int xinc = Math.max(penRadius/2, 1);
        
        penX = x + CHAR_MARGIN;
        penY = y + CHAR_HEIGHT - CHAR_MARGIN;
        penDown = true;
        
        sleep(SLEEP_TIME);
        
        while(penY > y + CHAR_MARGIN) {
            penY -= ydec;
            sleep(SLEEP_TIME);
        }
        
        while(penX < x + CHAR_MARGIN + CHAR_WIDTH/4){
            penX += xinc;
            sleep(SLEEP_TIME);
        }
        
        drawArc(x + CHAR_MARGIN+ CHAR_WIDTH/4, y + CHAR_HEIGHT/2,
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/2, -90, 90, false);
        
        penY = y + CHAR_HEIGHT - CHAR_MARGIN;
        penX = x + CHAR_MARGIN + CHAR_WIDTH/4;
        
        while(penX > x + CHAR_MARGIN){
            penX -= xinc;
            sleep(SLEEP_TIME);
        }
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawE() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int ydec = Math.max(penRadius/2, 1);
        final int xinc = Math.max(penRadius/2, 1);
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x + CHAR_MARGIN + CHAR_WIDTH/2, y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x + CHAR_MARGIN + CHAR_WIDTH/2, y + CHAR_HEIGHT/2);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x + CHAR_MARGIN + CHAR_WIDTH/2, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawF() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int ydec = Math.max(penRadius/2, 1);
        final int xinc = Math.max(penRadius/2, 1);
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x + CHAR_MARGIN + CHAR_WIDTH/2, y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x + CHAR_MARGIN + CHAR_WIDTH/2, y + CHAR_HEIGHT/2);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawG() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int xdec = Math.max(1, penRadius/2);
        penDown = true;
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_HEIGHT/2, CHAR_WIDTH/2 - CHAR_MARGIN, 300, 0, true);
        
        penX = x + CHAR_WIDTH - CHAR_MARGIN;
        penY = y + CHAR_HEIGHT/2;
        
        sleep(SLEEP_TIME);
        
        while(penX > x + CHAR_MARGIN + CHAR_WIDTH/2) {
            penX -= xdec;
            sleep(SLEEP_TIME);
        }
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawH() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN, 
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, x - 2*CHAR_MARGIN + CHAR_WIDTH, 
                y + CHAR_HEIGHT/2);
        
        drawVerticalLine(x +CHAR_HEIGHT - 2*CHAR_MARGIN, 
                y + CHAR_HEIGHT - CHAR_MARGIN, y + CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawI() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawVerticalLine(x + CHAR_HEIGHT/2, y + CHAR_HEIGHT - CHAR_MARGIN, 
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + 2*CHAR_MARGIN, x - 2*CHAR_MARGIN + CHAR_WIDTH, 
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + 2*CHAR_MARGIN, x - 2*CHAR_MARGIN + CHAR_WIDTH, 
                y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawJ() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        int arcRadius = (CHAR_WIDTH - 2*CHAR_MARGIN)/4;
        
        penDown = true;
        
        drawHorizontalLine(x + CHAR_MARGIN, x - 2*CHAR_MARGIN + CHAR_WIDTH, 
                y + CHAR_MARGIN);
        
        drawVerticalLine(x + CHAR_HEIGHT/2, y + CHAR_MARGIN,
                y + CHAR_HEIGHT - CHAR_MARGIN - arcRadius);
        
        drawArc(x + CHAR_HEIGHT/2 - arcRadius, 
                y + CHAR_HEIGHT - CHAR_MARGIN - arcRadius,
                arcRadius,
                0, 180, false);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawK() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                y + CHAR_MARGIN);
        
        drawSlantLine(x + CHAR_MARGIN, y + CHAR_HEIGHT/2, 
                x + CHAR_WIDTH/2, y + CHAR_MARGIN);
        
        drawSlantLine(x + CHAR_MARGIN, y + CHAR_HEIGHT/2, 
                x + CHAR_WIDTH/2, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawL() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int ydec = Math.max(penRadius/2, 1);
        final int xinc = Math.max(penRadius/2, 1);
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_MARGIN,
                y + CHAR_HEIGHT - CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x + CHAR_MARGIN + CHAR_WIDTH/2, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawM() {
        double slope = (CHAR_HEIGHT - 2*CHAR_MARGIN) * 4.0 / (CHAR_WIDTH - 2*CHAR_MARGIN);
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int xinc = Math.max(penRadius/2 - 1, 1);
        
        penX = x + CHAR_MARGIN;
        penY = y + CHAR_HEIGHT - CHAR_MARGIN;
        penDown = true;
        
        sleep(SLEEP_TIME);
        
        while(penY > y + CHAR_MARGIN) {
            penX += xinc;
            penY -= (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        while(penY + penRadius < y + CHAR_HEIGHT - CHAR_MARGIN) {
            penX += xinc;
            penY += (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        while(penY > y + CHAR_MARGIN) {
            penX += xinc;
            penY -= (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        while(penY < y + CHAR_HEIGHT - CHAR_MARGIN) {
            penX += xinc;
            penY += (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        penDown = false;
        ++currentBlock;
        
    }
    
    private void drawN() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN, 
                y + CHAR_MARGIN);
        
        drawSlantLine(x + CHAR_MARGIN, y + CHAR_MARGIN,
                x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        drawVerticalLine(x + CHAR_WIDTH - CHAR_MARGIN, 
                y + CHAR_HEIGHT - CHAR_MARGIN, y + CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawO() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        int arcRadius = (CHAR_HEIGHT - 2*CHAR_MARGIN)/3;
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_MARGIN + arcRadius,
                arcRadius, 180, 360, false);
        
        drawVerticalLine(x + CHAR_WIDTH/2 + arcRadius, 
                y + CHAR_MARGIN + arcRadius, y + CHAR_MARGIN + 2*arcRadius);
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_MARGIN + 2*arcRadius,
                arcRadius, 0, 180, false);
        
        drawVerticalLine(x + CHAR_WIDTH/2 - arcRadius,
                y + CHAR_MARGIN + 2*arcRadius, y + CHAR_MARGIN + arcRadius);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawP() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        
        penDown = true;
        
        drawVerticalLine(x + 2*CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + 2*CHAR_MARGIN, x + 2*CHAR_MARGIN + CHAR_WIDTH/4,
                y + CHAR_MARGIN);
        
        drawArc(x + 2*CHAR_MARGIN+ CHAR_WIDTH/4, y + CHAR_MARGIN + (CHAR_HEIGHT - 2*CHAR_MARGIN)/4,
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/4, -90, 90, false);
        
        drawHorizontalLine(x + 2*CHAR_MARGIN + CHAR_WIDTH/4, x + 2*CHAR_MARGIN,
                y + CHAR_HEIGHT/2);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawQ() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        int arcRadius = (CHAR_HEIGHT - 2*CHAR_MARGIN)/3;
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_MARGIN + arcRadius,
                arcRadius, 180, 360, false);
        
        drawVerticalLine(x + CHAR_WIDTH/2 + arcRadius, 
                y + CHAR_MARGIN + arcRadius, y + CHAR_MARGIN + 2*arcRadius);
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_MARGIN + 2*arcRadius,
                arcRadius, 0, 180, false);
        
        drawVerticalLine(x + CHAR_WIDTH/2 - arcRadius,
                y + CHAR_MARGIN + 2*arcRadius, y + CHAR_MARGIN + arcRadius);
        
        drawSlantLine(x + CHAR_WIDTH/2, 
                y + CHAR_MARGIN + 2*arcRadius, x + CHAR_WIDTH/2 + arcRadius,
                y + CHAR_MARGIN + 3*arcRadius);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawR() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        
        penDown = true;
        
        drawVerticalLine(x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN,
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, x + CHAR_MARGIN + CHAR_WIDTH/4,
                y + CHAR_MARGIN);
        
        drawArc(x + CHAR_MARGIN+ CHAR_WIDTH/4, y + CHAR_MARGIN + (CHAR_HEIGHT - 2*CHAR_MARGIN)/4,
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/4, -90, 90, false);
        
        drawHorizontalLine(x + CHAR_MARGIN + CHAR_WIDTH/4, x + CHAR_MARGIN,
                y + CHAR_HEIGHT/2);
        
        drawSlantLine(x + 2*CHAR_MARGIN, y + CHAR_HEIGHT/2, 
                x + CHAR_WIDTH/2 + CHAR_MARGIN, 
                y + CHAR_HEIGHT - CHAR_MARGIN + 10);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawS() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_HEIGHT/4 + CHAR_MARGIN/2, 
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/3, 0, -180, true);
        
        drawSlantLine(x + 2*CHAR_MARGIN, y + CHAR_HEIGHT/4 + CHAR_MARGIN/2,
                x + CHAR_WIDTH - 2*CHAR_MARGIN, y + 3*CHAR_HEIGHT/4 - CHAR_MARGIN/2);
        
        drawArc(x + CHAR_WIDTH/2, 
                y + 3*CHAR_HEIGHT/4 - CHAR_MARGIN/2, 
                (CHAR_HEIGHT - 2*CHAR_MARGIN)/3, 0, 180, false);
        
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawT() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawVerticalLine(x + CHAR_HEIGHT/2, y + CHAR_HEIGHT - CHAR_MARGIN, 
                y + CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, x - CHAR_MARGIN + CHAR_WIDTH, 
                y + CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawU() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        int arcRadius = (CHAR_HEIGHT - 2*CHAR_MARGIN)/2;
        
        drawVerticalLine(x + CHAR_WIDTH/2 + arcRadius, 
                y + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN - arcRadius);
        
        drawArc(x + CHAR_WIDTH/2, 
                y + CHAR_HEIGHT - CHAR_MARGIN - arcRadius,
                arcRadius, 0, 180, false);
        
        drawVerticalLine(x + CHAR_WIDTH/2 - arcRadius,
                y + CHAR_HEIGHT - CHAR_MARGIN - arcRadius, y + CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawV() {
        
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        
        penDown = true;
        
        sleep(SLEEP_TIME);
        
        drawSlantLine(x + CHAR_MARGIN, y + CHAR_MARGIN, 
                x + CHAR_WIDTH/2, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        drawSlantLine(x + CHAR_WIDTH/2, y + CHAR_HEIGHT - CHAR_MARGIN,
                x + CHAR_WIDTH - CHAR_MARGIN, y + CHAR_MARGIN);
        
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawW() {
        double slope = (CHAR_HEIGHT - 2*CHAR_MARGIN) * 4.0 / (CHAR_WIDTH - 2*CHAR_MARGIN);
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        final int xinc = Math.max(penRadius/2 - 1, 1);
        
        penX = x + CHAR_MARGIN;
        penY = y + CHAR_MARGIN;
        penDown = true;
        
        sleep(SLEEP_TIME);
        
        while(penY < y + CHAR_HEIGHT - CHAR_MARGIN) {
            penX += xinc;
            penY += (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        while(penY - penRadius > y + CHAR_MARGIN) {
            penX += xinc;
            penY -= (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        while(penY < y + CHAR_HEIGHT - CHAR_MARGIN) {
            penX += xinc;
            penY += (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        while(penY > y + CHAR_MARGIN) {
            penX += xinc;
            penY -= (int) slope * xinc;
            sleep(SLEEP_TIME);
        }
        
        penDown = false;
        ++currentBlock;
        
    }
    
    private void drawX() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawSlantLine(x + CHAR_MARGIN, y + CHAR_MARGIN,
                x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        drawSlantLine(x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_MARGIN,
                x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawY() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawSlantLine(x + 2*CHAR_MARGIN, y + CHAR_MARGIN,
                x + CHAR_MARGIN + (CHAR_WIDTH - CHAR_MARGIN)/2, 
                y + CHAR_HEIGHT/2 + 10);
        
        drawSlantLine(x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_MARGIN,
                x + 3*CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
    private void drawZ() {
        final int x = getBlockX(currentBlock);
        final int y = getBlockY(currentBlock);
        penDown = true;
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_MARGIN);
        
        drawSlantLine(x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_MARGIN,
                x + CHAR_MARGIN, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        drawHorizontalLine(x + CHAR_MARGIN, 
                x - CHAR_MARGIN + CHAR_WIDTH, y + CHAR_HEIGHT - CHAR_MARGIN);
        
        penDown = false;
        ++currentBlock;
    }
    
}
