package jonnu.mines.model;

import java.util.Arrays;
import java.util.function.Predicate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Minefield {

    private final int x;
    private final int y;
    private final Plot[][] plots;

    /**
     * Return a count of {@code Plot} objects that adhere to the given predicate.
     * @param predicate Predicate to filter on.
     * @return Count of plots that adhere to the predicate.
     */
    public long getFilteredPlotCount(final Predicate<Plot> predicate) {
        return Arrays.stream(plots)
                .flatMap(Arrays::stream)
                .filter(predicate)
                .count();
    }

    /**
     * Return a count of all {@code Plot} objects within the minefield.
     * @return Count of all plots.
     */
    public long getTotalPlotCount() {
        return x * y;
    }
}
