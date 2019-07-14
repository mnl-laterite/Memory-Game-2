package MemoryGame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GamePlayGUI {

  private MemoryGame memoryGame;

  private Canvas gameCanvas;
  private Pane canvasContainer;
  private Image[] gamePieces;
  private GraphicsContext gc;

  private BorderPane gameLayout;
  private HBox gameOptions;

  private Button quitButton;
  private Button resetButton;
  private Button getHintButton;

  public GamePlayGUI (MemoryGame memoryGame) {

    this.memoryGame = memoryGame;
    gameLayout = new BorderPane();
    canvasContainer = new Pane();

    gameCanvas = new ResizableCanvas(canvasContainer.widthProperty(),canvasContainer.heightProperty());
    canvasContainer.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    canvasContainer.getChildren().add(gameCanvas);
    gameOptions = new HBox();

    gameLayout.setCenter(canvasContainer);
    gameLayout.setBottom(gameOptions);

    getHintButton = new Button("Get Hint!");
    getHintButton.setOnAction(e -> getHintButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    resetButton = new Button("Reset.");
    resetButton.setOnAction(e -> resetButtonPressed());

    gameOptions.getChildren().addAll(resetButton, getHintButton, quitButton);
    gameOptions.setSpacing(10);
    gameOptions.setPadding(new Insets(10,10,10,10));
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
