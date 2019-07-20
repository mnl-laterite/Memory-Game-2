package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
  private static Stage primaryStage;

  private GameLogic gameLogic;
  private MainMenuGUI mainMenuGUI;
  private GamePlayGUI gamePlayGUI;
  private EndGameGUI endGameGUI;

  private Scene scene;

  private VBox menuLayout;
  private BorderPane gameLayout;
  private VBox endGameLayout;

  @Override
  public void start(Stage primaryStage) throws Exception {

    this.primaryStage = primaryStage;
    primaryStage.setTitle("Memory Training Game!");

    gameLogic = new GameLogic(Difficulty.DEFAULT);

    mainMenuGUI = new MainMenuGUI(this, gameLogic);
    gamePlayGUI = new GamePlayGUI(this, gameLogic);
    endGameGUI = new EndGameGUI(this, gameLogic);

    menuLayout = mainMenuGUI.getMenuLayout();
    gameLayout = gamePlayGUI.getGameLayout();
    endGameLayout = endGameGUI.getEndGameLayout();

    menuLayout.getStylesheets().add(this.getClass().getResource("/mainMenuStyle.css").toExternalForm());
    gameLayout.getStylesheets().add(this.getClass().getResource("/gamePlayStyle.css").toExternalForm());
    endGameLayout.getStylesheets().add(this.getClass().getResource("/endGameStyle.css").toExternalForm());

    scene = new Scene(menuLayout, 600, 800);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void switchToMenuLayout () {
    scene.setRoot(menuLayout);
  }

  public void switchToGameLayout () {
    scene.setRoot(gameLayout);
  }

  public void switchToEndGameLayout () {
    scene.setRoot(endGameLayout);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
