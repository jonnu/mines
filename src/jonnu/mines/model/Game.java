package jonnu.mines.model;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {

    public enum GameState {
        READY,
        PLAYING,
        GAME_OVER
    }

    private final SimpleObjectProperty<GameState> state = new SimpleObjectProperty<>(this, "state", GameState.READY);

    public void changeState(final GameState gameState) {
        state.set(gameState);
    }

    public boolean isInState(final GameState gameState) {
        return state.get().equals(gameState);
    }

    public boolean isGameOver() {
        return isInState(GameState.GAME_OVER);
    }
}
