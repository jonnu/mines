package jonnu.mines.view;

import static jonnu.mines.controller.GameController.getGameController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;
import jonnu.mines.presenter.PlotPresenter;

public class MinefieldRenderer implements Renderer<Minefield> {

    private Group group;
    private Renderer<Plot> plotRenderer;

    public MinefieldRenderer(final Group group, final Renderer<Plot> plotRenderer) {
        this.group = group;
        this.plotRenderer = plotRenderer;
    }

    public static void handlePlotHover(final MouseEvent event) {

        PlotPresenter presenter = (PlotPresenter)event.getPickResult().getIntersectedNode().getParent();
//
//        if (presenter.getPlot().getState().get().equals(Plot.PlotState.SEARCHED)) {
//            return;
//        }

        if (presenter.getPlot().isMined()) {
            Text presenterText = (Text) presenter.getChildren().filtered(node -> node.getClass() == Text.class).get(0);
            presenterText.setText("!");
        }
    }

    public static void handlePlotClick(final MouseEvent event) {

        // trigger the a*
        //doAyStar();
        getGameController(null).onPlotClicked(event);

        // render results
//        Rectangle plotView = (Rectangle)event.getPickResult().getIntersectedNode();
//        plotView.setFill(Color.RED);
//        Text t = (Text) plotView.getParent().getChildrenUnmodifiable().filtered(x -> x.getClass() == Text.class).get(0);
//        t.setText("1");
    }

    @Override
    public Node render(final Minefield data) {

        // use intstream here so that this can be parallel'd
        Node[] renderArray = IntStream.range(0, data.getPlots().length)
                .boxed()
                .map(row -> IntStream.range(0, data.getPlots()[0].length)
                        .boxed()
                        .map(col -> plotRenderer.render(data.getPlots()[col][row]))
                        .toArray(Node[]::new)
                ).flatMap(Stream::of)
                .toArray(Node[]::new);

        TilePane tp = new TilePane();
        tp.setPrefColumns(data.getPlots()[0].length);
        tp.setPrefRows(data.getPlots().length);
        tp.getChildren().addAll(renderArray);

        group.getChildren().add(tp);

        return group;
    }
}
