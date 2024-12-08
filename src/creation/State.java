package src.creation;

import java.awt.Color;

public enum State {
    WALL(false, Color.BLACK),
    OBSTACLE(false, Color.DARK_GRAY),
    VISITED(true, Color.GREEN),
    FREE(true, Color.WHITE);

    private final boolean AVAILABLE;
    private final Color COLOR;

    private State(boolean available, Color color) {
        AVAILABLE = available;
        COLOR = color;
    }

    public boolean getAvailable() { return AVAILABLE; }
    public Color getColor() { return COLOR; }
}
