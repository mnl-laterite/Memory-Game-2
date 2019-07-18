package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class MainMenuGUI {

  private Main main;

  private VBox menuLayout;
  private HBox menuSettingsPanel;

  private Button startButton;
  private Button quitButton;

  private Button easyButton;
  private Button defaultButton;
  private Button hardButton;

  private GameLogic gameLogic;

  public MainMenuGUI(Main main, GameLogic gameLogic) {

    this.main = main;
    this.gameLogic = gameLogic;

    menuLayout = new VBox();
    menuLayout.setSpacing(10);

    createMenuSettingsPanel();

    startButton = new Button("Start!");
    startButton.setOnAction(e -> startButtonPressed());

    quitButton = new Button("Quit.");
    quitButton.setOnAction(e -> quitButtonPressed());

    menuLayout.setAlignment(Pos.CENTER);
    menuLayout.getChildren().addAll(startButton, menuSettingsPanel, quitButton);
    menuLayout.setId("menulayout");

  }

  private void createMenuSettingsPanel () {

    menuSettingsPanel = new HBox();
    menuSettingsPanel.setSpacing(10);
    menuSettingsPanel.setVisible(false);
    menuSettingsPanel.setAlignment(Pos.CENTER);

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
    startButton.setVisible(false);
  }

  private void easyButtonPressed () {
    gameLogic.resetGame(Difficulty.EASY);
    difficultyButtonPressed();
  }

  private void defaultButtonPressed () {
    gameLogic.resetGame(Difficulty.DEFAULT);
    difficultyButtonPressed();
  }

  private void hardButtonPressed () {
    gameLogic.resetGame(Difficulty.HARD);
    difficultyButtonPressed();
  }

  private void difficultyButtonPressed() {

    main.setScene(main.getGameScene());
  }

  public VBox getMenuLayout() {
    return menuLayout;
  }

}
