package src.creation;

import java.awt.Color;

enum State {
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

    boolean getAvailable() { return AVAILABLE; }
    Color getColor() { return COLOR; }
}
