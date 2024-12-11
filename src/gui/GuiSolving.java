package src.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class GuiSolving {

    private final JPanel jPanel;
    private final Runnable onResolutionPaused;
    private final Consumer<Integer> onReset;

    private Integer selectedSolverIndex = 0;
    
    public GuiSolving(String[] solvers, Runnable onResolutionPaused, Consumer<Integer> onReset) {
        this.onResolutionPaused = onResolutionPaused;
        this.onReset = onReset;
        jPanel = buildPanel();
        buildWidgets(solvers);
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    private void buildWidgets(String[] solvers) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buildButtonPause(gbc);
        buildSolvingChoice(gbc, solvers);
        buildButtonReset(gbc);
    }

    private void buildSolvingChoice(GridBagConstraints gbc, String[] solvers) {
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 4;
        JComboBox<String> solvingChoices = new JComboBox<>(solvers);
        jPanel.add(solvingChoices, gbc);
        solvingChoices.addActionListener(_ -> {selectedSolverIndex = solvingChoices.getSelectedIndex(); });
    }

    private void buildButtonPause(GridBagConstraints gbc) {
        gbc.insets = new Insets(0, 10, 200, 10);
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        JButton buttonPlay = new JButton("Play / Pause");
        jPanel.add(buttonPlay, gbc);
        buttonPlay.addActionListener(_ -> onResolutionPaused.run());
    }

    private void buildButtonReset(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.SOUTH;
        JButton buttonReset = new JButton("Reset / New solver");
        jPanel.add(buttonReset, gbc);
        buttonReset.addActionListener(_ -> onReset.accept(selectedSolverIndex));
    }

    private JPanel buildPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new GridBagLayout());
        return panel;
    }
}
