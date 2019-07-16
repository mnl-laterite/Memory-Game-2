package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.event.MouseEvent;

public class GamePlayGUI {

  private Main main;
  private GameLogic gameLogic;

  private Pane canvasContainer;
  private ResizableCanvas gameCanvas;
  private Image[] gamePieces = new Image[25];
  private GraphicsContext gc;
  private Timeline animationLoop;
  private long gameStartTime;

  private BorderPane gameLayout;
  private HBox gameOptions;

  private Button quitButton;
  private Button resetButton;
  private Button getHintButton;

  public GamePlayGUI (Main main, GameLogic gameLogic) {

    this.main = main;
    this.gameLogic = gameLogic;

    gameLayout = new BorderPane();
    canvasContainer = new Pane();
    canvasContainer.setId("container");

    createGameCanvas();
    createGameOptions();

    gameLayout.setCenter(canvasContainer);
    gameLayout.setBottom(gameOptions);

    gamePieces[0] = new Image(this.getClass().getResource("/gamepieces/testSprite2.png").toExternalForm());

    for (int i=1; i<=12; ++i) {
      gamePieces[i] = new Image(this.getClass().getResource("/gamepieces/" + i + ".webp").toExternalForm());
    }

    gc = gameCanvas.getGraphicsContext2D();

    animationLoop = new Timeline();
    animationLoop.setCycleCount(Timeline.INDEFINITE);
    //gameCanvas.setOnMouseClicked(gameLogic.getMouseClickDetector());

    gameStartTime = System.currentTimeMillis();

    KeyFrame kf = new KeyFrame(
      Duration.seconds(0.017),
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

          //needs game logic completion
          int limit = gameLogic.getGamePlayMapDepth();
          gc.clearRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());

          for (int i = 0; i<limit; ++i) {
            for (int j = 0; j<limit; ++j) {
              if (gameLogic.getMapContents(i,j) < 0)
              gc.drawImage(gamePieces[(-1)*gameLogic.getMapContents(i,j)],i*100+10,j*100+10,100,100);
              else
                gc.drawImage(gamePieces[0], i*100+10,j*100+10,100,100);
            }
          }

        }
      }
    );

    animationLoop.getKeyFrames().add( kf );
    animationLoop.play();

  }

  private void createGameCanvas () {

    gameCanvas = new ResizableCanvas(canvasContainer.widthProperty(),canvasContainer.heightProperty());
    canvasContainer.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    canvasContainer.getChildren().add(gameCanvas);

  }

  private void createGameOptions () {

    gameOptions = new HBox();

    getHintButton = new Button("Get Hint!");
    getHintButton.setOnAction(e -> getHintButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    resetButton = new Button("Reset");
    resetButton.setOnAction(e -> resetButtonPressed());

    gameOptions.getChildren().addAll(resetButton, getHintButton, quitButton);
    gameOptions.setSpacing(10);
    gameOptions.setPadding(new Insets(10,10,10,10));
    gameOptions.setAlignment(Pos.BOTTOM_RIGHT);

  }

  public Dimension2D getClickCoordinates (MouseEvent event) {
    Dimension2D dim = new Dimension2D(event.getX(), event.getY());
    return dim;
  }

  private void resetButtonPressed() {
    gameLogic.resetGame(Difficulty.DEFAULT);
    main.setScene(main.getMainScene());
  }

  private void quitButtonPressed() {
    Platform.exit();
  }

  private void getHintButtonPressed() {
    setEndGameGUI(); //temporary
  }

  private void setEndGameGUI() {
    main.setScene(main.getEndScene());
  }

  public BorderPane getGameLayout () {
    return gameLayout;
  }
}
