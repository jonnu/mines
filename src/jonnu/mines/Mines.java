package jonnu.mines;

import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jonnu.mines.controller.GameController;
import jonnu.mines.model.Minefield;
import jonnu.mines.view.MinefieldRenderer;
import jonnu.mines.view.PlotRenderer;

public class Mines extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mines.fxml"));
        primaryStage.setTitle("Mines");

        Group group = new Group(root);
        Scene scene = new Scene(group, 300, 275);


        MinefieldFactory f = new MinefieldFactory(new Random());
        Minefield mf = f.createMinefield(9, 9, 10);

        GameController game = GameController.getGameController(mf);//new GameController(mf);

        MinefieldRenderer mfr = new MinefieldRenderer(group, new PlotRenderer());
        mfr.render(mf);

        //game.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
