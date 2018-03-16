package jonnu.mines.presenter;

import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import jonnu.mines.model.Minefield;

public class MinefieldPresenter extends TilePane implements Presenter {

    private final Minefield minefield;

    public MinefieldPresenter(final Minefield minefield, final Node... children) {
        super(children);

        this.setPrefRows(minefield.getY());
        this.setPrefColumns(minefield.getX());

        this.minefield = minefield;
    }
}
