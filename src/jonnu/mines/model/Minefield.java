package jonnu.mines.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Minefield {
    private final int x;
    private final int y;
    private final Plot[][] plots;
}
