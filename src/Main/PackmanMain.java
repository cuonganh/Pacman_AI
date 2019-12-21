package Main;

import javax.swing.JFrame;

public class PackmanMain {
    private JFrame jframe;
    PackmanPanel panel;
    Thread thread;

    public PackmanMain() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        jframe = new JFrame("Packman");
         panel = new PackmanPanel();
        thread = new Thread(panel);
        jframe.setSize(750 - 285, java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - 175);
         jframe.add(panel);
        jframe.setVisible(true);
        panel.requestFocus();
        panel.setFocusable(true);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       thread.start();
    }

    public static void main(String[] args) {
        new PackmanMain();

    }
}
