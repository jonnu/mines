package jonnu.mines.view;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.Group;
import javafx.scene.Node;
import jonnu.mines.model.Minefield;
import jonnu.mines.model.Plot;
import jonnu.mines.presenter.MinefieldPresenter;

public class MinefieldRenderer implements Renderer<Minefield> {

    private Group group;
    private Renderer<Plot> plotRenderer;

    public MinefieldRenderer(final Group group, final Renderer<Plot> plotRenderer) {
        this.group = group;
        this.plotRenderer = plotRenderer;
    }

    @Override
    public Node render(final Minefield data) {

        // use IntStream here so that this can be parallel'd
        Node[] renderArray = IntStream.range(0, data.getPlots().length)
                .boxed()
                .map(row -> IntStream.range(0, data.getPlots()[0].length)
                        .boxed()
                        .map(col -> plotRenderer.render(data.getPlots()[col][row]))
                        .toArray(Node[]::new)
                ).flatMap(Stream::of)
                .toArray(Node[]::new);

        group.getChildren().add(new MinefieldPresenter(data, renderArray));

        return group;
    }
}
