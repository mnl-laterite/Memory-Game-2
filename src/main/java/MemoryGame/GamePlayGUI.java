package MemoryGame;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class GamePlayGUI {

  private MemoryGame memoryGame;

  private Canvas gameCanvas;
  private Image[] gamePieces;

  private AnchorPane gameLayout;
  private Button testButton2; //to be replaced by actual gameplay content

  public GamePlayGUI (MemoryGame memoryGame) {

    this.memoryGame = memoryGame;
    gameLayout = new AnchorPane();

    testButton2 = new Button("Go to final scene!");
    testButton2.setOnAction(e -> testButton2Pressed());
    gameLayout.getChildren().add(testButton2);

  }

  private void testButton2Pressed() {
    memoryGame.setScene(memoryGame.getEndScene());
  }

  public AnchorPane getGameLayout () {
    return gameLayout;
  }
}
