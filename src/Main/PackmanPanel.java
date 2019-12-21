package Main;

import java.awt.Graphics;
import javax.swing.JPanel;
import Packman.*;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PackmanPanel extends JPanel implements KeyListener, Runnable {

    PackMan packman;
    public PackmanPanel() {
        packman = new PackMan();
        this.addKeyListener(this.packman);
        this.requestFocus();
        this.setFocusable(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        packman.draw((Graphics2D) g);
        }
   
    @Override
    public void run() {
        while (true) {
            packman.update();
            repaint();
            try {
                Thread.sleep(15);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
