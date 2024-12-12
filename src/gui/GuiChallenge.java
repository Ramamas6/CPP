package src.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GuiChallenge {

    private final JPanel jPanel;
    private final Consumer<Integer> onNewChallenge;

    private Integer N = 5;

    public GuiChallenge(Consumer<Integer> onNewChallenge) {
        this.onNewChallenge = onNewChallenge;
        jPanel = buildPanel();
        buildWidgets();
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    private JPanel buildPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    private void buildWidgets() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buildChoiceN(gbc);
        builButtonNewChallenge(gbc);
    }

    private void builButtonNewChallenge(GridBagConstraints gbc) {
        gbc.gridy += 1;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton buttonNewChallenge = new JButton("Start new challenge");
        jPanel.add(buttonNewChallenge, gbc);
        buttonNewChallenge.addActionListener(_ -> onNewChallenge.accept(N));
    }

    private void buildChoiceN(GridBagConstraints gbc) {
        // Text
        gbc.gridy += 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel textN = new JLabel(" N : ");
        textN.setFont(new Font("Arial", Font.BOLD, 16));
        jPanel.add(textN, gbc);
        // Spinner
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JSpinner spinnerN = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
        spinnerN.setPreferredSize(new Dimension(50, 35));
        ((JSpinner.DefaultEditor) spinnerN.getEditor()).getTextField()
            .setFont(new Font("Arial", Font.BOLD, 15));
        jPanel.add(spinnerN, gbc);
        spinnerN.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Integer newValue = (Integer) spinnerN.getValue();
                if (newValue > 1 && newValue < 10) N = newValue;
            }
        });
    }
}
