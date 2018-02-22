package jonnu.mines.model;

import javafx.scene.Node;
import lombok.Data;

/**
 * Represents a 'plot' of land.
 */
@Data
public class Plot {

    /**
     * The X co-ordinate of the plot.
     */
    private final int x;

    /**
     * The Y co-ordinate of the plot.
     */
    private final int y;

    /**
     * Did the player flag this as an interesting plot?
     */
    private boolean flagged;

    /**
     * Did the player dig this plot?
     */
    private boolean searched;

    /**
     * What is the face value of this plot?
     */
    private int value;

    private Node node;
}
