package src.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuiStats {

    private final JPanel jPanel;
    private JLabel textCleanedCases;
    private JLabel textRoamedCases;
    private int goalCellNumber;

    public GuiStats() {
        jPanel = buildPanel();
        buildWidgets();
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    public void updateTexts(int cleanedCases, int roamedCases) {
        textCleanedCases.setText(cleanedCases + " / " + goalCellNumber
                        + " (" + 100*cleanedCases/goalCellNumber + "%)");
        if (cleanedCases == 0) cleanedCases += 1;
        textRoamedCases.setText(roamedCases + " (" + 100*roamedCases/cleanedCases + "%)");
    }

    public void start(int goalCellNumber) {
        this.goalCellNumber = goalCellNumber;
        updateTexts(0, 0);
    }

    private JPanel buildPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    private void buildWidgets() {
        GridBagConstraints gbc = new GridBagConstraints();
        buildTextCleanedCases(gbc);
        buildTextRoamedCases(gbc);
    }

    private void buildTextCleanedCases(GridBagConstraints gbc) {
        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel newLabel = new JLabel("Cleaned cases : ");
        newLabel.setFont(new Font("Arial", Font.BOLD, 24));
        jPanel.add(newLabel, gbc);
        gbc.gridx = 1;
        textCleanedCases = new JLabel("");
        textCleanedCases.setFont(new Font("Arial", Font.BOLD, 24));
        jPanel.add(textCleanedCases, gbc);
    }
    private void buildTextRoamedCases(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel newLabel = new JLabel("Cases roamed : ");
        newLabel.setFont(new Font("Arial", Font.BOLD, 24));
        jPanel.add(newLabel, gbc);
        gbc.gridx = 1;
        textRoamedCases = new JLabel("");
        textRoamedCases.setFont(new Font("Arial", Font.BOLD, 24));
        jPanel.add(textRoamedCases, gbc);
    }
}