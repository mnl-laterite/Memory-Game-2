package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Describes the gameplay screen.
 *
 * @author mnl-laterite
 */
class GamePlayGUI {

  /**
   * Instance of the main class, needed to change screens.
   */
  private Main main;

  /**
   * Instance of the underlying game board on which the game is played. Saves current game state.
   */
  private GameLogic gameLogic;

  /**
   * Container node for the canvas.
   */
  private Pane canvasContainer;

  /**
   * Game board on which the game piece sprites are drawn.
   */
  private ResizableCanvas gameCanvas;

  /**
   * The game piece sprites. One for the backside of the pieces, and 12 unique ones for the topside of the pieces.
   */
  private Image[] gamePieces = new Image[13];

  /**
   * Instance of the class that issues draw calls on the game canvas.
   */
  private GraphicsContext graphicsContext;

  /**
   * The parent node for the gameplay screen layout.
   */
  private BorderPane gameLayout;

  /**
   * Container node for the game's reset and quit buttons.
   */
  private HBox optionsButtons;

  /**
   * Creates the gameplay screen and starts the game loop.
   *
   * @param main      instance of the main app class, needed to change game screens.
   * @param gameLogic instance of the game board that saves the current game state.
   */
  GamePlayGUI (Main main, GameLogic gameLogic) {

    this.main = main;
    this.gameLogic = gameLogic;

    gameLayout = new BorderPane();
    canvasContainer = new Pane();
    canvasContainer.setId("container");

    createGameCanvas();
    createGameOptions();

    gameLayout.setCenter(canvasContainer);
    gameLayout.setBottom(optionsButtons);

    //getting the bottomside sprite of the pices
    gamePieces[0] = new Image(this.getClass().getResource("/gamepieces/tilebackside.png").toExternalForm());

    //getting the 12 unique topside sprites for the pieces
    for (int i = 1; i <= 12; ++i) {
      gamePieces[i] = new Image(this.getClass().getResource("/gamepieces/" + i + ".png").toExternalForm());
    }

    graphicsContext = gameCanvas.getGraphicsContext2D();

    gameCanvas.setOnMouseClicked(this::onMouseClicked);

    Timeline gameTimeline = new Timeline();
    gameTimeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame kf = new KeyFrame(Duration.seconds(0.034), event -> updateDisplay());
    gameTimeline.getKeyFrames().add(kf);
    gameTimeline.play();
  }

  /**
   * Updates the game state and draws the pieces on the game board.
   */
  private void updateDisplay () {
    gameLogic.updateState();
    drawPieces();

    if (gameLogic.getPairsTotal() - gameLogic.getPairsFound() == 0) {
      setEndGameGUI();
    }
  }

  /**
   * Draws the game pieces on the canvas using the graphics context according to current game state.
   */
  private void drawPieces () {
    int drawLimit = gameLogic.getGamePlayMapDepth();
    double canvasWidth = gameCanvas.getWidth();
    double canvasHeight = gameCanvas.getHeight();
    double boxSize, offset;

    if (canvasWidth > canvasHeight) {

      boxSize = canvasHeight / drawLimit;
      offset = (canvasWidth - canvasHeight) / 2;
    } else {

      boxSize = canvasWidth / drawLimit;
      offset = (canvasHeight - canvasWidth) / 2;
    }

    graphicsContext.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

    if (canvasWidth > canvasHeight) {
      for (int i = 0; i < drawLimit; ++i) {
        for (int j = 0; j < drawLimit; ++j) {

          if (gameLogic.pieceTurned(i, j) && gameLogic.isMarkedForElimination(i, j)) {

            graphicsContext.drawImage(gamePieces[gameLogic.getMarkedForEliminationMapContents(i, j)],
              i * boxSize + offset,
              j * boxSize,
              boxSize - 10,
              boxSize - 10);

          } else if (gameLogic.pieceTurned(i, j) && !gameLogic.isMarkedForElimination(i, j)) {
            graphicsContext.drawImage(gamePieces[gameLogic.getMapContents(i, j)],
              i * boxSize + offset,
              j * boxSize,
              boxSize - 10,
              boxSize - 10);

          } else if (!gameLogic.pieceTurned(i, j) && gameLogic.pieceStillInGame(i, j)) {
            graphicsContext.drawImage(gamePieces[0],
              i * boxSize + offset,
              j * boxSize,
              boxSize - 10,
              boxSize - 10);
          }
        }
      }
    } else {
      for (int i = 0; i < drawLimit; ++i) {
        for (int j = 0; j < drawLimit; ++j) {

          if (gameLogic.pieceTurned(i, j) && gameLogic.isMarkedForElimination(i, j)) {

            graphicsContext.drawImage(gamePieces[gameLogic.getMarkedForEliminationMapContents(i, j)],
              i * boxSize,
              j * boxSize + offset,
              boxSize - 10,
              boxSize - 10);

          } else if (gameLogic.pieceTurned(i, j) && !gameLogic.isMarkedForElimination(i, j)) {
            graphicsContext.drawImage(gamePieces[gameLogic.getMapContents(i, j)],
              i * boxSize,
              j * boxSize + offset,
              boxSize - 10,
              boxSize - 10);

          } else if (!gameLogic.pieceTurned(i, j) && gameLogic.pieceStillInGame(i, j)) {
            graphicsContext.drawImage(gamePieces[0],
              i * boxSize,
              j * boxSize + offset,
              boxSize - 10,
              boxSize - 10);
          }
        }
      }
    }
  }

  /**
   * Checks if the input is valid (i.e. on a piece) and then plays the coordinates clicked on the game board.
   *
   * @param event mouse click on the canvas.
   */
  private void onMouseClicked (MouseEvent event) {

    int drawLimit = gameLogic.getGamePlayMapDepth();
    int i, j;

    double canvasWidth = gameCanvas.getWidth();
    double canvasHeight = gameCanvas.getHeight();
    double boxSize, offset;

    if (canvasWidth > canvasHeight) {

      boxSize = canvasHeight / drawLimit;
      offset = (canvasWidth - canvasHeight) / 2;
    } else {

      boxSize = canvasWidth / drawLimit;
      offset = (canvasHeight - canvasWidth) / 2;
    }

    if (canvasWidth > canvasHeight) {
      if (event.getX() >= offset && event.getX() <= offset + canvasHeight) {
        i = (int) (event.getX() - offset) / (int) boxSize;
        j = (int) event.getY() / (int) boxSize;

        gameLogic.playCoordinates(i, j);
      }
    } else {
      if (event.getY() >= offset && event.getY() <= offset + canvasWidth) {
        i = (int) event.getX() / (int) boxSize;
        j = (int) (event.getY() - offset) / (int) boxSize;

        gameLogic.playCoordinates(i, j);
      }
    }
  }

  /**
   * Creates the game canvas and sets it in its container pane.
   */
  private void createGameCanvas () {

    gameCanvas = new ResizableCanvas(canvasContainer.widthProperty(), canvasContainer.heightProperty());
    canvasContainer.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    canvasContainer.getChildren().add(gameCanvas);
  }

  /**
   * Creates the panel that contains the game reset and quit buttons.
   */
  private void createGameOptions () {

    optionsButtons = new HBox();

    Button quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    Button resetButton = new Button("Reset");
    resetButton.setOnAction(e -> resetGame());

    optionsButtons.getChildren().addAll(resetButton, quitButton);
    optionsButtons.setSpacing(10);
    optionsButtons.setPadding(new Insets(10, 10, 10, 10));
    optionsButtons.setAlignment(Pos.BOTTOM_RIGHT);

  }

  /**
   * Resets the game and sets the current screen to the main menu screen.
   */
  private void resetGame () {
    gameLogic.resetGame(Difficulty.DEFAULT);
    main.switchToMenuLayout();
  }

  /**
   * Exits the application.
   */
  private void quitButtonPressed () {
    Platform.exit();
  }

  /**
   * Sets the current screen to the end game screen.
   */
  private void setEndGameGUI () {
    main.switchToEndGameLayout();
  }

  /**
   * @return the parent node for the game play screen layout.
   */
  BorderPane getGameLayout () {
    return gameLayout;
  }
}
