package src;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import src.creation.Generation;
import src.creation.Room;

public class Main {

    public static void main(String[] args){

        JFrame frame = buildFrame();
        Generation generation = new Generation();
        Room room = generation.generateMap(10);
        frame.add(room.getJPanel(), BorderLayout.CENTER);
        frame.setVisible(true);
	}

    private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        // Configuration de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        return frame;
    }

}
