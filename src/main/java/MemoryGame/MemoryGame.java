package MemoryGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemoryGame extends Application
{

  private static Stage primaryStage;

  private GameLogic gameLogic;
  private MainMenuGUI mainMenuGUI;
  private Scene startScene;
  private Scene gameScene;
  private Scene endScene;

  @Override
  public void start(Stage primaryStage) throws Exception {

    // s = new GameScene
    // s.onSOmethingHappenes()
    // primary.setScene()

    this.primaryStage = primaryStage;
    primaryStage.setTitle("Memory Training Game!");

    mainMenuGUI = new MainMenuGUI(this);
    gameLogic = new GameLogic(mainMenuGUI, Difficulty.DEFAULT);
    mainMenuGUI.setLogic(gameLogic);

    startScene = new Scene(mainMenuGUI.getStartScreenSettingsPanel(),480,800);

    startScene.getStylesheets().add(this.getClass().getResource("/mainMenuStyle.css").toExternalForm());

    gameScene = new Scene(mainMenuGUI.getGameScreenLayout(), 480, 800);
    endScene = new Scene(mainMenuGUI.getEndScreenLayout(), 480, 800);

    primaryStage.setScene(startScene);

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
