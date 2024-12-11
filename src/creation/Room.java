package src.creation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import src.Point;

class Room {

    private static int SQUARE_SIZE = 5;

    private int sizeX;
    private int sizeY;
    private Cell[][] room;
    private final JPanel jPanel;

    Room(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.room = new Cell[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.room[i][j] = new Cell();
            }
        }
        jPanel = new JPanel(new GridBagLayout());
    }

    void reset() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.room[i][j].reset();
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
        room[row][column].setWall();
    }
    void setWall(Point point) { setWall(point.x, point.y); }
    
    private void setObstacle(int row, int column) {
        if (contains(row, column)) room[row][column].setObstacle();
    }
    void setObstacle(Point point) { setObstacle(point.x, point.y); }

    private boolean setRoamed(int row, int column) {
        if (!contains(row, column)) return false;
        return room[row][column].setRoamed();
    }
    boolean setRoamed(Point point) { return setRoamed(point.x, point.y); }

    void buildPanel() {
        jPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0;
        gbc.weighty = 0;
        for (int i = 0; i < sizeX+2; i++) {
            for (int j = 0; j < sizeY+2; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                gbc.fill = GridBagConstraints.NONE;
                if (i == 0 || j == 0 || i == sizeX+1 || j == sizeY+1) {
                    JLabel label = new JLabel();
                    label.setBackground(Color.BLACK);
                    label.setOpaque(true);
                    label.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                    jPanel.add(label, gbc);
                }
                else {
                    room[i-1][j-1].repaint();
                    jPanel.add(room[i-1][j-1].getLabel(), gbc);
                }
            }
        }
    }

    void repaint(Point oldPoint, Point currentPoint) {
        room[oldPoint.x][oldPoint.y].repaint();
        room[currentPoint.x][currentPoint.y].repaint(Color.RED);
        jPanel.revalidate();
        jPanel.repaint();
    }
    JPanel getJPanel() { return jPanel; }
}