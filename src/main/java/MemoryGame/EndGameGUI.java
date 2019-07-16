package MemoryGame;

import MemoryGame.game.GameLogic;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndGameGUI {

  private Main main;
  private GameLogic gameLogic;

  private VBox endGameLayout;

  public EndGameGUI (Main main, GameLogic gameLogic) {

    this.main = main;
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
