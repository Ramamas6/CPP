package src.creation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import src.Point;

public class Room {

    private static int SQUARE_SIZE = 3;

    private int sizeX;
    private int sizeY;
    private State[][] room;
    private final JPanel jPanel;

    Room(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.room = new State[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.room[i][j] = State.FREE;
            }
        }
        jPanel = new JPanel(new GridBagLayout());
    }

    void reset() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (this.room[i][j] == State.VISITED) this.room[i][j] = State.FREE;
            }
        }
    }

    int getRowSize() { return sizeX; }
    int getColumnSize() { return sizeY; }

    private boolean contains(int row, int column) {
        return (row >= 0 && column >= 0 && row < sizeX && column < sizeY);
    }
    boolean contains(Point point) { return contains(point.x, point.y); }

    private boolean isFree(int row, int column) {
        if (!contains(row, column)) return false;
        return room[row][column].getAvailable();
    }
    boolean isFree(Point point) { return isFree(point.x, point.y); }

    private void setWall(int row, int column) {
        if (!contains(row, column)) return;
        room[row][column] = State.WALL;
    }
    void setWall(Point point) { setWall(point.x, point.y); }
    
    private void setObstacle(int row, int column) {
        if (isFree(row, column)) room[row][column] = State.OBSTACLE;
    }
    void setObstacle(Point point) { setObstacle(point.x, point.y); }

    private boolean setRoamed(int row, int column) {
        if (!isFree(row, column)) return false;
        if (room[row][column] == State.VISITED) return false;
        room[row][column] = State.VISITED;
        return true;
    }
    boolean setRoamed(Point point) { return setRoamed(point.x, point.y); }

    public void repaint() {
        jPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < sizeX+2; i++) {
            for (int j = 0; j < sizeY+2; j++) {
                JLabel label = new JLabel();
                if (i == 0 || j == 0 || i == sizeX+1 || j == sizeY+1)
                    label.setBackground(Color.BLACK);
                else label.setBackground(room[i-1][j-1].getColor());
                label.setOpaque(true);
                label.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                gbc.gridx = j;
                gbc.gridy = i;
                gbc.weightx = 0;
                gbc.weighty = 0;
                gbc.fill = GridBagConstraints.NONE;
                jPanel.add(label, gbc);
            }
        }
        jPanel.revalidate();
        jPanel.repaint();
    }
    public JPanel getJPanel() { return jPanel; }
}