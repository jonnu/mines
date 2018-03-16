package jonnu.mines.presenter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jonnu.mines.model.Plot;
import lombok.Getter;

@Getter
public class PlotPresenter extends StackPane implements Presenter {

    private final Plot plot;

    public PlotPresenter(final Plot plot, final Node... children) {

        super(children);
        this.plot = plot;

        plot.getState().addListener(this::onStateChange);
        plot.getProximity().addListener(this::onProximityChange);
    }

    public void onStateChange(ObservableValue observable, Plot.PlotState oldValue, Plot.PlotState newValue) {

        Color colour;
        Plot plot = (Plot) ((SimpleObjectProperty)observable).getBean();

        switch (newValue) {

            case SEARCHED:
                colour = plot.isMined() ? Color.RED : Color.GREY;
                break;
            case FLAGGED:
                colour = Color.GREEN;
                break;
            default:
                colour = Color.WHITE;
                break;
        }

        getByLookup(Rectangle.class, ".plot").setFill(colour);
    }

    public void onProximityChange(ObservableValue observable, Number oldValue, Number newValue) {
        getByLookup(Text.class, ".text").setText(String.valueOf(newValue));
    }

    private <T> T getByLookup(final Class<T> clazz, final String selector) {
        return clazz.cast(lookup(selector));
    }
}
