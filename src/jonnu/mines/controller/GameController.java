package jonnu.mines.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;
import jonnu.mines.presenter.PlotPresenter;
import lombok.Getter;

public class GameController {

    public enum GameState {
        READY,
        PLAYING,
        GAME_OVER
    }

    @Getter
    private static GameController instance;

    private final Minefield minefield;
    private GameState state;

    private GameController(final Minefield minefield) {
        this.minefield = minefield;
        this.state = GameState.READY;
    }

    public static GameController getGameController(final Minefield minefield) {

        if (instance == null) {
            instance = new GameController(minefield);
        }

        return instance;
    }

    public void onPlotClicked(final MouseEvent event) {

        if (state == GameState.READY) {
            state = GameState.PLAYING;
        }

        if (state != GameState.PLAYING) {
            return;
        }

        PlotPresenter presenter = (PlotPresenter)event.getPickResult().getIntersectedNode().getParent();

        Plot.PlotState currentState = presenter.getPlot().getState().get();
        if (currentState.equals(Plot.PlotState.SEARCHED)) {
            return;
        }

        if (event.getButton() == MouseButton.SECONDARY || event.isControlDown()) {
            Plot.PlotState newState = currentState.equals(Plot.PlotState.FLAGGED) ? Plot.PlotState.DEFAULT : Plot.PlotState.FLAGGED;
            presenter.getPlot().getState().set(newState);
            return;
        }

        presenter.getPlot().getState().set(Plot.PlotState.SEARCHED);

        checkGameOver(presenter.getPlot());

        if (state == GameState.GAME_OVER) {
            return;
        }

        findAstar(presenter.getPlot());
    }

    public void onPlotHover(final MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        node.setCursor(state == GameState.GAME_OVER ? Cursor.DEFAULT : Cursor.HAND);
    }

    private Plot addPotentialPlot(int x, int y) {

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
            state = GameState.GAME_OVER;
        }
    }

    private void findAstar(final Plot origin) {

        // start checking the cardinal directions.
        List<Plot> surroundingPlots = Stream.of(
                addPotentialPlot(origin.getX(), origin.getY() - 1), // North
                addPotentialPlot(origin.getX() + 1, origin.getY() - 1), // North-East
                addPotentialPlot(origin.getX() + 1, origin.getY()), // East
                addPotentialPlot(origin.getX() + 1, origin.getY() + 1), // South-East
                addPotentialPlot(origin.getX(), origin.getY() + 1), // South
                addPotentialPlot(origin.getX() - 1, origin.getY() + 1), // South-West
                addPotentialPlot(origin.getX() - 1, origin.getY()), // West
                addPotentialPlot(origin.getX() - 1, origin.getY() - 1)  // North-West
        ).filter(Objects::nonNull)
                .collect(Collectors.toList());

        origin.getState().set(Plot.PlotState.SEARCHED);

        if (surroundingPlots.isEmpty()) {
            return;
        }

        long surroundingCount = getSurroundingMineCount(surroundingPlots);

        //System.out.println(String.format("(%d, %d) has %d mines surrounding it [%s]",
        //        origin.getX(), origin.getY(), surroundingCount, origin.getState().get()));

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
