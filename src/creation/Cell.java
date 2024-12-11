package src.creation;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;

class Cell {

    private static int SQUARE_SIZE = 5;

    private final JLabel label;
    private State state;

    Cell() {
        state = State.FREE;
        label = buildLabel();
    }

    boolean getAvailable() {
        return state.getAvailable();
    }

    JLabel getLabel() {
        return label;
    }

    void setWall() {
        state = State.WALL;
    }

    void setObstacle() {
        if (state.getAvailable()) state = State.OBSTACLE;
    }

    boolean setRoamed() {
        if (!state.getAvailable() || state == State.VISITED) return false;
        state = State.VISITED;
        return true;
    }

    void reset() {
        if (state == State.VISITED) state = State.FREE;
        repaint();
    }

    void repaint(Color color) {
        label.setBackground(color);
    }
    void repaint() {
        label.setBackground(state.getColor());
    }

    private JLabel buildLabel() {
        JLabel newLabel = new JLabel();
        newLabel.setOpaque(true);
        newLabel.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
        return newLabel;
    }
}
