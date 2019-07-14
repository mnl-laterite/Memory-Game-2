package MemoryGame;

import java.util.Random;

public class GameLogic {

  private MainMenuGUI mainMenuGUI;
  private int[] gamePieces;
  private Difficulty gameDifficulty;
  private short pairsFound;
  private short pairsTotal;

  public GameLogic (MainMenuGUI mainMenuGUI, Difficulty difficulty) {

    this.mainMenuGUI = mainMenuGUI;
    setDifficulty(difficulty);
    pairsFound = 0;
    gamePieces = new int[24];

    initialize();

  }

  private void initialize() {

  }

  public void setDifficulty (Difficulty difficulty) {

    switch (difficulty) {

      case EASY:
        gameDifficulty = Difficulty.EASY;
        pairsTotal = 4;
        break;

      case HARD:
        gameDifficulty = Difficulty.HARD;
        pairsTotal = 12;
        break;

      case DEFAULT:
        gameDifficulty = Difficulty.DEFAULT;
        pairsTotal = 8;
        break;

      default:
        gameDifficulty = Difficulty.DEFAULT;
        pairsTotal = 8;
        break;
    }

  }

  private void shuffleGamePieces () {

    int i, j, temp;

    Random rand = new Random();
    int[] shuffler = new int[pairsTotal*2];

    for (i = 0; i < pairsTotal*2; ++i) {
      shuffler[i] = i < pairsTotal ? i+1 : i+1 - pairsTotal;
    }

    for (i = pairsTotal*2 - 1; i > 0; --i) {

      j = rand.nextInt(i);

      temp = shuffler[j];
      shuffler[j] = shuffler[i];
      shuffler[i] = temp;
    }

    for (i = 0; i < pairsTotal*2; ++i) {
      gamePieces[i] = shuffler[i];
    }
  }

}
