package jonnu.mines.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jonnu.mines.model.Plot;
import jonnu.mines.presenter.PlotPresenter;

public class PlotRenderer implements Renderer<Plot> {

    private static int PLOT_WIDTH = 20;
    private static int PLOT_HEIGHT = 20;

    @Override
    public Node render(final Plot plot) {

        //System.out.println("Rendering: " + plot.toString());

        // Create border.
        Rectangle border = new Rectangle(plot.getX() * PLOT_WIDTH, plot.getY() * PLOT_HEIGHT, PLOT_WIDTH, PLOT_HEIGHT);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(0.5d);
        border.setPickOnBounds(true);

        // Create text.
        Text text = new Text();
        text.setPickOnBounds(false);

        // Combine into a stack pane.
        PlotPresenter pane = new PlotPresenter(plot, border, text);
        pane.setPickOnBounds(false);

        // Bind events - todo... move these
        border.setOnMouseClicked(MinefieldRenderer::handlePlotClick);
        border.setOnMouseEntered(MinefieldRenderer::handlePlotHover);

        return pane;
    }

}
