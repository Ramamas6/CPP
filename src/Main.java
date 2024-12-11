package src;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import src.creation.Challenge;
import src.solvings.GloutonSolve;
import src.solvings.ShortestPathSolve;
import src.solvings.Solving;

public class Main {

    private static JFrame Jframe;
    private static Challenge challenge;
    private static Solving solving;
    

    public static void main(String[] args){

        solving = new ShortestPathSolve();
        challenge = new Challenge(4);

        Jframe = buildFrame();
        Jframe.add(challenge.getRoomPanel(), BorderLayout.CENTER);
        Jframe.setVisible(true);
        solve();
	}

    private static void solve() {
        Point nextPoint = challenge.start();
        solving.start(nextPoint);
        while (nextPoint != null) {
            try { Thread.sleep(10); } catch (InterruptedException e) {}
            nextPoint = solving.next(challenge.next(nextPoint));
        }
        System.out.println("END");
        challenge.verif();
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
