package MemoryGame;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MainMenuGUI {

  private MemoryGame memoryGame;

  private VBox menuLayout;
  private HBox menuSettingsPanel;

  private Button startButton;
  private Button quitButton;

  private Button easyButton;
  private Button defaultButton;
  private Button hardButton;

  private GameLogic gameLogic;

  public MainMenuGUI(MemoryGame memoryGame) {

    this.memoryGame = memoryGame;

    menuLayout = new VBox();
    menuLayout.setSpacing(10);

    menuSettingsPanel = new HBox();
    menuSettingsPanel.setSpacing(10);
    menuSettingsPanel.setVisible(false);
    menuSettingsPanel.setAlignment(Pos.CENTER);

    startButton = new Button("Start!");
    startButton.setOnAction(e -> startButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    menuLayout.setAlignment(Pos.CENTER);
    menuLayout.getChildren().addAll(startButton, menuSettingsPanel, quitButton);
    menuLayout.setId("menu");

    easyButton = new Button("Easy (3x3)");
    menuSettingsPanel.getChildren().add(easyButton);
    easyButton.setOnAction(e -> easyButtonPressed());

    defaultButton = new Button("Normal (4x4)");
    menuSettingsPanel.getChildren().add(defaultButton);
    defaultButton.setOnAction(e -> defaultButtonPressed());

    hardButton = new Button("Hard (5x5)");
    menuSettingsPanel.getChildren().add(hardButton);
    hardButton.setOnAction(e -> hardButtonPressed());

  }

  private void quitButtonPressed () {
    Platform.exit();
  }

  private void startButtonPressed () {
    menuSettingsPanel.setVisible(true);
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

  public void setLogic (GameLogic gameLogic) {

    this.gameLogic = gameLogic;
  }

  public VBox getMenuSettingsPanel() {
    return menuLayout;
  }

}
