package MemoryGame;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GamePlayGUI {

  private MemoryGame memoryGame;

  private Canvas gameCanvas;
  private Image[] gamePieces;

  private BorderPane gameLayout;
  private HBox gameOptions;

  private Button quitButton;
  private Button resetButton;
  private Button getHintButton;

  public GamePlayGUI (MemoryGame memoryGame) {

    this.memoryGame = memoryGame;
    gameLayout = new BorderPane();
    gameCanvas = new Canvas();
    gameOptions = new HBox();

    gameLayout.setCenter(gameCanvas);
    gameLayout.setBottom(gameOptions);

    getHintButton = new Button("Get Hint!");
    getHintButton.setOnAction(e -> getHintButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    resetButton = new Button("Reset.");
    resetButton.setOnAction(e -> resetButtonPressed());

    gameOptions.getChildren().addAll(resetButton, getHintButton, quitButton);
    gameOptions.setSpacing(10);
    gameOptions.setAlignment(Pos.BOTTOM_RIGHT);

  }

  private void resetButtonPressed() {
    memoryGame.setScene(memoryGame.getMainScene());
  }

  private void quitButtonPressed() {
    Platform.exit();
  }

  private void getHintButtonPressed() {

  }

  private void testButton2Pressed() {
    memoryGame.setScene(memoryGame.getEndScene());
  }

  public BorderPane getGameLayout () {
    return gameLayout;
  }
}
