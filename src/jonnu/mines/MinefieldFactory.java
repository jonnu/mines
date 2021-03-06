package jonnu.mines;

import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinefieldFactory {

    private final Random random;

    // turn into singleton
    public MinefieldFactory(final Random random) {
        this.random = random;
    }

    public Minefield createMinefield(final int x, final int y, final int mines) {

        List<Plot> plotList = IntStream.range(0, x * y).boxed()
                .map(counter -> new Plot(Math.floorDiv(counter, x), counter % x))
                .collect(Collectors.toList());

        // Randomly select, and add mines.

        List<Integer> indexes = IntStream.range(0, plotList.size())
                .boxed()
                .collect(Collectors.toList());

        // add mines.
        for (int i = 0; i < mines; i++) {

            // add mine
            int index = random.nextInt(indexes.size());
            Plot p = plotList.get(indexes.get(index));

            indexes.remove(index);

            p.setMined(true);
            //System.out.println(String.format("New mine: (%d, %d)", p.getX(), p.getY()));
        }

        Plot[][] plots = IntStream.range(0, x)
                .boxed()
                .map(row -> plotList.subList(row * y, (row + 1) * y).toArray(new Plot[x]))
                .toArray(Plot[][]::new);

        return Minefield.builder()
                .x(x)
                .y(y)
                .plots(plots)
                .build();
    }
}
