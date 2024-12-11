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

    private final JPanel jPanel;

    private int sizeX;
    private int sizeY;
    private Cell[][] room;

    Room() {
        jPanel = new JPanel(new GridBagLayout());
    }

    void newRoom(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.room = new Cell[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.room[i][j] = new Cell();
            }
        }
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

    boolean contains(Point point) {
        return (point.x >= 0 && point.y >= 0 && point.x < sizeX && point.y < sizeY);
    }

    boolean isFree(Point point) {
        if (!contains(point)) return false;
        return room[point.x][point.y].getAvailable();
    }

    void setWall(Point point) {
        if (!contains(point)) return;
        room[point.x][point.y].setWall();
    }
    
    void setObstacle(Point point) {
        if (contains(point)) room[point.x][point.y].setObstacle();
    }

    boolean setRoamed(Point point) {
        if (!contains(point)) return false;
        return room[point.x][point.y].setRoamed();
    }

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