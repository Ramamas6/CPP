package src.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GuiConfigs {

    private final JPanel jPanel;
    private final Consumer<Integer> onSpeedChanged;
    private final Consumer<Boolean> onDiagonalMovementChanged;

    public GuiConfigs(Consumer<Integer> onSpeedChanged, Consumer<Boolean> onDiagonalMovementChanged) {
        this.onSpeedChanged = onSpeedChanged;
        this.onDiagonalMovementChanged = onDiagonalMovementChanged;
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
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buildSpeedChoice(gbc);
        buildDiagonalChoice(gbc);
    }

    private void buildDiagonalChoice(GridBagConstraints gbc) {
        gbc.gridx = 2;
        // Text
        gbc.gridy = 0;
        JLabel newLabel = new JLabel("Allow diagonal movement");
        newLabel.setFont(new Font("Arial", Font.BOLD, 16));
        jPanel.add(newLabel, gbc);
        // CheckBox
        gbc.gridy += 1;
        JCheckBox checkBoxDiagonal = new JCheckBox();
        jPanel.add(checkBoxDiagonal, gbc);
        checkBoxDiagonal.addActionListener(_ -> {
            onDiagonalMovementChanged.accept(checkBoxDiagonal.isSelected());
        });
    }

    private void buildSpeedChoice(GridBagConstraints gbc) {
        // Text
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel newLabel = new JLabel("Resolution period");
        newLabel.setFont(new Font("Arial", Font.BOLD, 16));
        jPanel.add(newLabel, gbc);
        // Spinner
        gbc.gridy += 1;
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
