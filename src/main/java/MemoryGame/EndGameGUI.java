package MemoryGame;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class EndGameGUI {

  private MemoryGame memoryGame;

  private BorderPane endGameLayout;

  public EndGameGUI (MemoryGame memoryGame) {

    this.memoryGame = memoryGame;
    endGameLayout = new BorderPane();
    Label text = new Label("This is the final screen!");
    endGameLayout.setCenter(text);
  }

  public BorderPane getEndGameLayout () {
    return endGameLayout;
  }
}
