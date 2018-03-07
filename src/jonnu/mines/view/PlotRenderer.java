package jonnu.mines.view;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jonnu.mines.controller.GameController;
import jonnu.mines.model.Plot;
import jonnu.mines.presenter.PlotPresenter;

public class PlotRenderer implements Renderer<Plot> {

    private static int PLOT_WIDTH = 20;
    private static int PLOT_HEIGHT = 20;

    private static String CLASS_PLOT = "plot";
    private static String CLASS_TEXT = "text";
    private static String CLASS_PANE = "pane";

    @Override
    public Node render(final Plot plot) {

        // Create border.
        Rectangle border = new Rectangle(plot.getX() * PLOT_WIDTH, plot.getY() * PLOT_HEIGHT, PLOT_WIDTH, PLOT_HEIGHT);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(0.5d);
        border.setPickOnBounds(true);
        border.getStyleClass().add(CLASS_PLOT);

        // Create text.
        Text text = new Text();
        text.setPickOnBounds(false);
        text.getStyleClass().add(CLASS_TEXT);

        // Combine into a stack pane.
        PlotPresenter pane = new PlotPresenter(plot, border, text);
        pane.setPickOnBounds(false);
        pane.getStyleClass().add(CLASS_PANE);

        // Bind events - todo... move these
        border.setOnMouseClicked(GameController.getInstance()::onPlotClicked);
        border.setOnMouseEntered(GameController.getInstance()::onPlotHover);

        return pane;
    }

}
