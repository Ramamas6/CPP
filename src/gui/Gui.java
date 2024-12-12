package src.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {

    private final JFrame jframe;
    private final GuiStats guiStats;
    private final GuiChallenge guiChallenge;
    private final GuiSolving guiSolving;
    private final GuiConfigs guiConfigs;

    public Gui(GuiListener listener, String[] solvers) {
        guiStats = new GuiStats();
        guiChallenge = new GuiChallenge(N -> listener.onNewChallenge(N));
        guiSolving = new GuiSolving(solvers, () -> listener.onResolutionPaused(), i -> listener.onReset(i));
        guiConfigs = new GuiConfigs(v -> listener.onSpeedChanged(v), b -> listener.onDiagonalMovementChanged(b));
        jframe = buildFrame();
    }

    public void repaint() {
        jframe.revalidate();
        jframe.repaint();
    }

    public void start(int casesNumber) {
        guiStats.start(casesNumber);
        repaint();
    }

    public void actualise(int cleanedCases, int roamedCases) {
        guiStats.updateTexts(cleanedCases, roamedCases);
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
        frame.add(guiStats.getJPanel(), BorderLayout.NORTH);
        frame.add(guiChallenge.getJPanel(), BorderLayout.WEST);
        frame.add(guiSolving.getJPanel(), BorderLayout.EAST);
        frame.add(guiConfigs.getJPanel(), BorderLayout.SOUTH);
        return frame;
    }
}
