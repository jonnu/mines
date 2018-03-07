package jonnu.mines.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;

/**
 * Represents a 'plot' of land.
 */
@Data
public class Plot {

    public enum PlotState {
        DEFAULT,
        SEARCHED,
        FLAGGED
    }

    /**
     * The X co-ordinate of the plot.
     */
    private final int x;

    /**
     * The Y co-ordinate of the plot.
     */
    private final int y;

    /**
     * Does this plot contain a mine?
     */
    private boolean mined;

    /**
     * The state of the plot.
     * [Observable]
     */
    private final SimpleObjectProperty<PlotState> state = new SimpleObjectProperty<>(this, "state", PlotState.DEFAULT);

    /**
     * The number of plots in proximity to this one with mines.
     * [Observable]
     */
    private final SimpleLongProperty proximity = new SimpleLongProperty(this, "proximity");

    @Override
    public String toString() {
        return String.format("Plot(%d, %d) [Has Mine: %s, Status: %s]", x, y, mined, state.get().toString());
    }
}
