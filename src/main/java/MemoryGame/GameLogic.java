package MemoryGame;

import java.util.Random;

public class GameLogic {

  private int[][] gamePlayMap;
  private int gamePlayMapDepth;
  private Difficulty gameDifficulty;
  private short pairsFound;
  private short pairsTotal;

  public GameLogic (Difficulty difficulty) {

    setDifficulty(difficulty);
    pairsFound = 0;
    gamePlayMap = new int[5][5];
    shuffleGamePieces();



  }

  public void resetGame (Difficulty difficulty) {

    pairsFound = 0;
    setDifficulty(difficulty);
    shuffleGamePieces();

  }


  public void setDifficulty (Difficulty difficulty) {

    switch (difficulty) {

      case EASY:
        gameDifficulty = Difficulty.EASY;
        pairsTotal = 4;
        gamePlayMapDepth = 3;
        break;

      case HARD:
        gameDifficulty = Difficulty.HARD;
        pairsTotal = 12;
        gamePlayMapDepth = 5;
        break;

      case DEFAULT:
        gameDifficulty = Difficulty.DEFAULT;
        pairsTotal = 8;
        gamePlayMapDepth = 4;
        break;

      default:
        gameDifficulty = Difficulty.DEFAULT;
        pairsTotal = 8;
        gamePlayMapDepth = 4;
        break;
    }

  }

  private void shuffleGamePieces () {

    int i, j, temp;

    Random rand = new Random();
    int[] shuffler = new int[pairsTotal * 2];

    for (i = 0; i < pairsTotal * 2; ++i) {
      shuffler[i] = i < pairsTotal ? -i - 1 : - i - 1 + pairsTotal;
    }

    for (i = pairsTotal * 2 - 1; i > 0; --i) {

      j = rand.nextInt(i);

      temp = shuffler[j];
      shuffler[j] = shuffler[i];
      shuffler[i] = temp;
    }

    temp = 0;
    for (i = 0; i < gamePlayMapDepth; ++i) {
      for (j = 0; j < gamePlayMapDepth; ++j) {
        gamePlayMap[i][j] = temp < 2*pairsTotal ? shuffler[temp] : 0;
        ++temp;
      }
    }
  }

}
