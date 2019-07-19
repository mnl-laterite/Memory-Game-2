package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GamePlayGUI {

  private Main main;
  private GameLogic gameLogic;

  private Pane canvasContainer;
  private ResizableCanvas gameCanvas;
  private Image[] gamePieces = new Image[25];
  private GraphicsContext gc;
  private Timeline animationLoop;
  private long gameStartTime;
  private int piecesTurned;

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

    gamePieces[0] = new Image(this.getClass().getResource("/gamepieces/tilebackside.png").toExternalForm());

    for (int i = 1; i <= 12; ++i) {
      gamePieces[i] = new Image(this.getClass().getResource("/gamepieces/" + i + ".webp").toExternalForm());
    }

    gc = gameCanvas.getGraphicsContext2D();

    piecesTurned = 0;

    gameCanvas.setOnMouseClicked(event -> {

      int drawLimit = gameLogic.getGamePlayMapDepth();
      double boxSizeX = gameCanvas.getWidth() / drawLimit;
      double boxSizeY = gameCanvas.getHeight() / drawLimit;

      int i = (int) event.getX() / (int)boxSizeX;
      int j = (int) event.getY() / (int)boxSizeY;

      if (i <= gameLogic.getGamePlayMapDepth() && j <= gameLogic.getGamePlayMapDepth()) {

        if (! gameLogic.pieceEliminated(i,j))
          if (! gameLogic.pieceTurned(i,j)) {
            gameLogic.turnPiece(i,j);
            piecesTurned++;
          }

        if (gameLogic.pairFound()) {
          gameLogic.hideUnfoundPairs();
          piecesTurned = 0;
        }
        else
          if (piecesTurned > 2) {
            piecesTurned = 1;
            gameLogic.hideUnfoundPairs();
            gameLogic.turnPiece(i,j);
          }
      }

      if (gameLogic.getPairsTotal() - gameLogic.getPairsFound() == 0) {
        setEndGameGUI();
      }

    });

      animationLoop = new Timeline();
      animationLoop.setCycleCount(Timeline.INDEFINITE);

      KeyFrame kf = new KeyFrame(
      Duration.seconds(0.017),
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

          int drawLimit = gameLogic.getGamePlayMapDepth();
          double boxSizeX = gameCanvas.getWidth() / drawLimit;
          double boxSizeY = gameCanvas.getHeight() / drawLimit;

          gc.clearRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());

          for (int i = 0; i < drawLimit; ++i) {
            for (int j = 0; j < drawLimit; ++j) {
              if (gameLogic.pieceTurned(i,j))
              gc.drawImage(gamePieces[gameLogic.getMapContents(i,j)],i*boxSizeX+10,j*boxSizeY+10,boxSizeX-10,boxSizeY-10);
              else
                if (!gameLogic.pieceTurned(i,j) && !gameLogic.pieceEliminated(i,j))
                gc.drawImage(gamePieces[0], i*boxSizeX+10,j*boxSizeY+10,boxSizeX-10,boxSizeY-10);
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

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    resetButton = new Button("Reset");
    resetButton.setOnAction(e -> resetButtonPressed());

    gameOptions.getChildren().addAll(resetButton, quitButton);
    gameOptions.setSpacing(10);
    gameOptions.setPadding(new Insets(10,10,10,10));
    gameOptions.setAlignment(Pos.BOTTOM_RIGHT);

  }

  private void resetButtonPressed() {
    gameLogic.resetGame(Difficulty.DEFAULT);
    main.setScene(main.getMainScene());
  }

  private void quitButtonPressed() {
    Platform.exit();
  }

  private void setEndGameGUI() {
    main.setScene(main.getEndScene());
  }

  public BorderPane getGameLayout () {
    return gameLayout;
  }
}
