package jonnu.mines.presenter;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
public class PlotPresenter extends StackPane {

    private final Plot plot;

    public PlotPresenter(final Plot plot, final Node... children) {
        super(children);
        this.plot = plot;

        plot.getState().addListener(this::onStateChange);
        plot.getProximity().addListener(this::onProximityChange);
    }

    public void onStateChange(ObservableValue observable, Plot.PlotState oldValue, Plot.PlotState newValue) {
        Plot plot = (Plot) ((SimpleObjectProperty)observable).getBean();

        Rectangle r = (Rectangle) this.getChildren().filtered(x -> x.getClass() == Rectangle.class).get(0);
        switch (newValue) {
            case DEFAULT:
                r.setFill(Color.WHITE);
                break;
            case SEARCHED:
                r.setFill(Color.GREY);
                break;
            case FLAGGED:
                r.setFill(Color.GREEN);
                break;
        }
        System.out.println(plot + "| Old: " + oldValue + " | New: " + newValue);
    }

    public void onProximityChange(ObservableValue observable, Number oldValue, Number newValue) {
        Plot plot = (Plot) ((SimpleLongProperty)observable).getBean();
        Text text = (Text) this.getChildren().filtered(n -> n.getClass() == Text.class).get(0);
        System.out.printf("Setting proximity of (%d, %d) to %d", plot.getX(), plot.getY(), newValue.intValue());
        text.setText(String.valueOf(newValue));
    }
}
