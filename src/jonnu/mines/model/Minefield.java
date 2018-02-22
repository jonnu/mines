package jonnu.mines.model;

import lombok.Getter;

@Getter
public class Minefield {

    private final int x;
    private final int y;
    private final Plot[][] mines;

    public Minefield(final int x, final int y) {
        this.x = x;
        this.y = y;
        mines = new Plot[y][x];
    }
}
