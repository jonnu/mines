package jonnu.mines.view;

import javafx.scene.Node;

public interface Renderer<T> {
    Node render(final T data);
}
