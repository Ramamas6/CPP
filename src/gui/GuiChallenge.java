package src.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GuiChallenge {

    private final Consumer<Integer> onSpeedChanged;
    private final Consumer<Integer> onNewChallenge;
    private final JPanel jPanel;

    private Integer N = 5;

    public GuiChallenge(Consumer<Integer> onSpeedChanged, Consumer<Integer> onNewChallenge) {
        this.onSpeedChanged = onSpeedChanged;
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
        buildSpeedChoice(gbc);
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
        spinnerN.setPreferredSize(new Dimension(50, 30));
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

    private void buildSpeedChoice(GridBagConstraints gbc) {
        // Text
        gbc.insets = new Insets(100, 5, 10, 5);
        gbc.gridy += 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel newLabel = new JLabel("Resolution period");
        newLabel.setFont(new Font("Arial", Font.BOLD, 16));
        jPanel.add(newLabel, gbc);
        // Spinner
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        SpinnerNumberModel model = new SpinnerNumberModel(100, 1, 10000, 10);
        JSpinner spinnerSpeed = new JSpinner(model);
        spinnerSpeed.setPreferredSize(new Dimension(80, 30));
        ((JSpinner.DefaultEditor) spinnerSpeed.getEditor()).getTextField()
        .setFont(new Font("Arial", Font.BOLD, 15));
        jPanel.add(spinnerSpeed, gbc);
        spinnerSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Integer value = (Integer) spinnerSpeed.getValue();
                if (value <= 10) { model.setStepSize(1);
                } else if (value <= 1000) { model.setStepSize(10);
                } else { model.setStepSize(100); }
                onSpeedChanged.accept(value);
            }
        });
    }
}
