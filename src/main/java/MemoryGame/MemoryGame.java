package MemoryGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemoryGame extends Application
{

  private static Stage primaryStage;

  private GameLogic gameLogic;
  private MainMenuGUI mainMenuGUI;
  private GamePlayGUI gamePlayGUI;
  private EndGameGUI endGameGUI;
  private Scene mainScene;
  private Scene gameScene;
  private Scene endScene;

  @Override
  public void start(Stage primaryStage) throws Exception {

    this.primaryStage = primaryStage;
    primaryStage.setTitle("Memory Training Game!");

    gameLogic = new GameLogic(Difficulty.DEFAULT);

    mainMenuGUI = new MainMenuGUI(this, gameLogic);
    gamePlayGUI = new GamePlayGUI(this, gameLogic);
    endGameGUI = new EndGameGUI(this, gameLogic);

    mainScene = new Scene(mainMenuGUI.getMenuSettingsPanel(),480,800);
    mainScene.getStylesheets().add(this.getClass().getResource("/mainMenuStyle.css").toExternalForm());

    gameScene = new Scene(gamePlayGUI.getGameLayout(), 800, 900);
    gameScene.getStylesheets().add(this.getClass().getResource("/gamePlayStyle.css").toExternalForm());

    endScene = new Scene(endGameGUI.getEndGameLayout(), 480, 800);

    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  public void setScene (Scene scene) {
    this.primaryStage.setScene(scene);
  }

  public Scene getMainScene () {
    return mainScene;
  }

  public Scene getGameScene () {
    return gameScene;
  }

  public Scene getEndScene () {
    return endScene;
  }

  public static void main (String[] args) {
    launch(args);
  }
}
