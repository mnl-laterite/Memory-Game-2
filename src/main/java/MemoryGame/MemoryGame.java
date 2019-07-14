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

    mainMenuGUI = new MainMenuGUI(this);
    gamePlayGUI = new GamePlayGUI(this);
    endGameGUI = new EndGameGUI(this);
    gameLogic = new GameLogic(mainMenuGUI, Difficulty.DEFAULT);
    mainMenuGUI.setLogic(gameLogic);

    mainScene = new Scene(mainMenuGUI.getMenuSettingsPanel(),480,800);

    mainScene.getStylesheets().add(this.getClass().getResource("/mainMenuStyle.css").toExternalForm());

    gameScene = new Scene(gamePlayGUI.getGameLayout(), 480, 800);
    endScene = new Scene(endGameGUI.getEndGameLayout(), 480, 800);

    primaryStage.setScene(mainScene);

    primaryStage.show();
  }

  public void setScene (Scene scene) {
    this.primaryStage.setScene(scene);
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
