package jonnu.mines.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Minefield {
    private final int x;
    private final int y;
    private final Plot[][] mines;
}
