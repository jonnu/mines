package jonnu.mines.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.input.MouseEvent;
import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;
import jonnu.mines.presenter.PlotPresenter;

public class GameController {

    private static GameController instance;

    private final Minefield minefield;

    private GameController(final Minefield minefield) {
        this.minefield = minefield;
    }

    public static GameController getGameController(final Minefield minefield) {

        if (instance == null) {
            instance = new GameController(minefield);
        }

        return instance;
    }

    public void onPlotClicked(final MouseEvent event) {

        PlotPresenter presenter = (PlotPresenter)event.getPickResult().getIntersectedNode().getParent();

        presenter.getPlot().getState().set(Plot.PlotState.SEARCHED);
        System.out.println("Player clicked: " + presenter.getPlot());

        checkGameOver(presenter.getPlot());
        findAstar(presenter.getPlot());
    }


    private Plot tryAddPlot(int x, int y) {

        if (y < 0 || y >= minefield.getPlots().length) {
            return null;
        }

        if (x < 0 || x >= minefield.getPlots()[0].length) {
            return null;
        }

        return minefield.getPlots()[x][y];
    }

    private void checkGameOver(final Plot plot) {

        if (plot.isMined()) {
            System.out.println("*****************************");
            System.out.println("*********** BOOM ************");
            System.out.println("*****************************");
        }
    }

    private void findAstar(final Plot origin) {

        // start checking the cardinal directions.
        List<Plot> surroundingPlots = Stream.of(
                tryAddPlot(origin.getX(), origin.getY() - 1), // North
                tryAddPlot(origin.getX() + 1, origin.getY() - 1), // North-East
                tryAddPlot(origin.getX() + 1, origin.getY()), // East
                tryAddPlot(origin.getX() + 1, origin.getY() + 1), // South-East
                tryAddPlot(origin.getX(), origin.getY() + 1), // South
                tryAddPlot(origin.getX() - 1, origin.getY() + 1), // South-West
                tryAddPlot(origin.getX() - 1, origin.getY()), // West
                tryAddPlot(origin.getX() - 1, origin.getY() - 1)  // North-West
        ).filter(Objects::nonNull)
                .collect(Collectors.toList());

        origin.getState().set(Plot.PlotState.SEARCHED);

        if (surroundingPlots.isEmpty()) {
            return;
        }

        long surroundingCount = getSurroundingMineCount(surroundingPlots);

        System.out.println(String.format("(%d, %d) has %d mines surrounding it [%s]",
                origin.getX(), origin.getY(), surroundingCount, origin.getState().get()));

        origin.getProximity().set(surroundingCount);

        if (surroundingCount == 0) {
            surroundingPlots.stream()
                    .filter(p -> p.getState().get() == Plot.PlotState.DEFAULT)
                    .forEach(this::findAstar);
        }
    }

    private long getSurroundingMineCount(final List<Plot> plots) {
        return plots.stream().filter(Plot::isMined).count();
    }

}
