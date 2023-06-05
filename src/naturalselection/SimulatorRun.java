package naturalselection;

import javax.swing.*;

public class SimulatorRun {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Canvas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SimulatorPanel(800, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
