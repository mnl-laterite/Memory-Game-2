package MemoryGame;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenuGUI {

  MemoryGame memoryGame;

  private Canvas gameCanvas;
  private Image[] gamePieces;

  private AnchorPane gameScreenLayout;
  private VBox startScreenMenuLayout;
  private HBox startScreenSettingsPanel;
  private BorderPane endScreenLayout;
  private GridPane gameSettingsPanel;

  private Button startButton;
  private Button quitButton;

  private Button easyButton;
  private Button defaultButton;
  private Button hardButton;

  private Button testButton2; //to be replaced by actual gameplay content

  private GameLogic gameLogic;

  public MainMenuGUI(MemoryGame memoryGame) {

    this.memoryGame = memoryGame;

    startScreenMenuLayout = new VBox();
    startScreenMenuLayout.setSpacing(10);

    startScreenSettingsPanel = new HBox();
    startScreenSettingsPanel.setSpacing(10);
    startScreenSettingsPanel.setVisible(false);
    startScreenSettingsPanel.setAlignment(Pos.CENTER);

    endScreenLayout = new BorderPane();

    startButton = new Button("Start!");
    startButton.setOnAction(e -> startButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    testButton2 = new Button("Go to final scene!");
    testButton2.setOnAction(e -> testButton2Pressed());

    startScreenMenuLayout.setAlignment(Pos.CENTER);
    startScreenMenuLayout.getChildren().addAll(startButton,startScreenSettingsPanel,quitButton);
    startScreenMenuLayout.setId("menu");

    easyButton = new Button("Easy (3x3)");
    startScreenSettingsPanel.getChildren().add(easyButton);
    easyButton.setOnAction(e -> easyButtonPressed());

    defaultButton = new Button("Normal (4x4)");
    startScreenSettingsPanel.getChildren().add(defaultButton);
    defaultButton.setOnAction(e -> defaultButtonPressed());

    hardButton = new Button("Hard (5x5)");
    startScreenSettingsPanel.getChildren().add(hardButton);
    hardButton.setOnAction(e -> hardButtonPressed());

    gameScreenLayout = new AnchorPane();
    gameSettingsPanel = new GridPane();
    gameCanvas = new Canvas();
    gameSettingsPanel.getChildren().add(testButton2);

    gameScreenLayout.getChildren().addAll(gameCanvas, gameSettingsPanel);

    Label text = new Label("This is the final screen!");
    endScreenLayout.setCenter(text);

  }

  private void quitButtonPressed () {
    Platform.exit();
  }

  private void startButtonPressed () {
    startScreenSettingsPanel.setVisible(true);
  }

  private void easyButtonPressed () {
    gameLogic.setDifficulty(Difficulty.EASY);
    difficultyButtonPressed();
  }

  private void defaultButtonPressed () {
    gameLogic.setDifficulty(Difficulty.DEFAULT);
    difficultyButtonPressed();
  }

  private void hardButtonPressed () {
    gameLogic.setDifficulty(Difficulty.HARD);
    difficultyButtonPressed();
  }

  private void difficultyButtonPressed() {

    memoryGame.setScene(memoryGame.getGameScene());
  }

  private void testButton2Pressed() {
    memoryGame.setScene(memoryGame.getEndScene());
  }

  public void setLogic (GameLogic gameLogic) {

    this.gameLogic = gameLogic;
  }

  public AnchorPane getGameScreenLayout() {
    return gameScreenLayout;
  }

  public VBox getStartScreenSettingsPanel() {
    return startScreenMenuLayout;
  }

  public BorderPane getEndScreenLayout() {
    return endScreenLayout;
  }

}
