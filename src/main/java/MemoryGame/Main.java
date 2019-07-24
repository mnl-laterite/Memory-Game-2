package MemoryGame;

import MemoryGame.game.Difficulty;
import MemoryGame.game.GameLogic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A memory game app.
 *
 * @author mnl-laterite
 */
public class Main extends Application {

  private Scene scene;

  /**
   * Parent node for the menu screen layout.
   */
  private VBox menuLayout;

  /**
   * Parent node for the gameplay screen layout.
   */
  private BorderPane gameLayout;

  /**
   * Parent node for the end game screen layout.
   */
  private VBox endGameLayout;

  @Override
  public void start (Stage primaryStage) {

    primaryStage.setTitle("Memory Training Game!");

    //creating the underlying game board on which the game is played (saves game state)
    GameLogic gameLogic = new GameLogic(Difficulty.DEFAULT);

    //creating the 3 screens of the app
    MainMenuGUI mainMenuGUI = new MainMenuGUI(this, gameLogic);
    GamePlayGUI gamePlayGUI = new GamePlayGUI(this, gameLogic);
    EndGameGUI endGameGUI = new EndGameGUI(this, gameLogic);

    menuLayout = mainMenuGUI.getMenuLayout();
    gameLayout = gamePlayGUI.getGameLayout();
    endGameLayout = endGameGUI.getEndGameLayout();

    menuLayout.getStylesheets().add(this.getClass().getResource("/mainMenuStyle.css").toExternalForm());
    gameLayout.getStylesheets().add(this.getClass().getResource("/gamePlayStyle.css").toExternalForm());
    endGameLayout.getStylesheets().add(this.getClass().getResource("/endGameStyle.css").toExternalForm());

    scene = new Scene(menuLayout, 720, 800);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  void switchToMenuLayout () {
    scene.setRoot(menuLayout);
  }

  void switchToGameLayout () {
    scene.setRoot(gameLayout);
  }

  void switchToEndGameLayout () {
    scene.setRoot(endGameLayout);
  }

  public static void main (String[] args) {
    launch(args);
  }
}
