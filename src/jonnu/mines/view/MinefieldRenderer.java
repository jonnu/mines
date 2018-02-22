package jonnu.mines.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MinefieldRenderer implements Renderer<Minefield> {

    private Group group;

    public MinefieldRenderer(Group group) {
        this.group = group;
    }

    public static Node renderPlot(Plot plot, int w, int h) {
        Node square = renderPlot(plot.getX() * w, plot.getY() * h, w, h);
        square.setId(String.format("plot_%d_%d", plot.getX(), plot.getY()));
        plot.setNode(square);
        return square;
    }

    public static Node renderPlot(int x, int y, int w, int h) {
        Rectangle square = new Rectangle(x, y, w, h);
        square.setStroke(Color.BLACK);
        square.setStrokeWidth(0.5d);
        square.setFill(Color.TRANSPARENT);
        return square;
    }

    @Override
    public void render(final Minefield data) {

        final int rows = data.getMines().length;
        final int cols = data.getMines()[0].length;

        final int width = 20;
        final int height = 20;

        int xStart = 10;
        int yStart = 10;

        // use intstream here so that this can be parallel'd
        Rectangle[] renderArray = IntStream.range(0, data.getMines().length)
                .boxed()
                .map(row -> IntStream.range(0, data.getMines()[0].length)
                        .boxed()
                        .map(col -> renderPlot(data.getMines()[col][row], width, height))
                        .toArray(Rectangle[]::new)
                ).flatMap(Stream::of)
                .toArray(Rectangle[]::new);

        group.getChildren().addAll(renderArray);

        group.setOnMouseClicked(event -> {

            if (event.getPickResult().getIntersectedNode().getClass() != Rectangle.class) {
                return;
            }

            Rectangle n = (Rectangle)event.getPickResult().getIntersectedNode();
            Optional<Plot> p = Arrays.stream(data.getMines()).flatMap(Stream::of).filter(z -> z.getNode().equals(n)).findFirst();

            if (!p.isPresent()) {
                return;
            }

            if (!p.get().isFlagged()) {
                p.get().setFlagged(true);
                n.setFill(Color.RED);
            }
            else {
                n.setFill(Color.BLUE);
            }

            System.out.println(String.format("%s, id: %s", n, n.getId()));
        });
    }
}
