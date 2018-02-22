package jonnu.mines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;

public class MinefieldFactory {

    private final Random random;

    // turn into singleton
    public MinefieldFactory(final Random random) {
        this.random = random;
    }

    public Minefield createMinefield(final int x, final int y, final int mines) {

        Minefield minefield = new Minefield(x, y);

        List<Plot> plotList = IntStream.range(0, x * y).boxed()
                .map(counter -> {
                    int row = Math.floorDiv(counter, x);
                    int col = counter % x;
                    return new Plot(row, col);
                })
                .collect(Collectors.toList());

        // Randomly select, and add mines.

        List<Integer> indexes = IntStream.range(0, plotList.size())
                .boxed()
                .collect(Collectors.toList());

        // add minez.
        for (int i = 0; i < mines; i++) {

            // add mine
            int index = random.nextInt(indexes.size());
            Plot p = plotList.get(indexes.get(index));
            p.setValue(1);

            indexes.remove(index);

            System.out.println(String.format("New mine: (%d, %d)", p.getX(), p.getY()));
        }

        Plot[][] plots = IntStream.range(0, x)
                .boxed()
                .map(row -> plotList.subList(row * y, (row + 1) * y).toArray(new Plot[x]))
                .toArray(Plot[][]::new);

        return minefield;
    }
}
