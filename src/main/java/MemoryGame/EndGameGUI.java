package MemoryGame;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EndGameGUI {

  private MemoryGame memoryGame;
  private GameLogic gameLogic;

  private VBox endGameLayout;

  public EndGameGUI (MemoryGame memoryGame, GameLogic gameLogic) {

    this.memoryGame = memoryGame;
    this.gameLogic = gameLogic;
    endGameLayout = new VBox();
    Label text = new Label("This is the final screen!");
    endGameLayout.getChildren().add(text);
    endGameLayout.setAlignment(Pos.CENTER);
  }

  public VBox getEndGameLayout () {
    return endGameLayout;
  }
}
