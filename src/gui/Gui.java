package src.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {

    private final JFrame jframe;
    private final GuiMain guiMain;
    private final GuiChallenge guiChallenge;
    private final GuiSolving guiSolving;

    public Gui(GuiListener listener, String[] solvers) {
        guiMain = new GuiMain();
        guiChallenge = new GuiChallenge(v -> listener.onSpeedChanged(v),
            N -> listener.onNewChallenge(N));
        guiSolving = new GuiSolving(solvers,
            () -> listener.onResolutionPaused(), i -> listener.onReset(i));
        jframe = buildFrame();
    }

    public void repaint() {
        jframe.revalidate();
        jframe.repaint();
    }

    public void start(int casesNumber) {
        guiMain.start(casesNumber);
        repaint();
    }

    public void actualise(int cleanedCases, int roamedCases) {
        guiMain.updateTexts(cleanedCases, roamedCases);
    }

    public void setVisible() {
        jframe.setVisible(true);
    }

    public void addMainPanel(JPanel panel) {
        jframe.add(panel, BorderLayout.CENTER);
    }

    private JFrame buildFrame() {
        JFrame frame = new JFrame();
        // Windows configuration
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Add gui windows
        frame.add(guiMain.getJPanel(), BorderLayout.NORTH);
        frame.add(guiChallenge.getJPanel(), BorderLayout.WEST);
        frame.add(guiSolving.getJPanel(), BorderLayout.EAST);
        return frame;
    }
}
