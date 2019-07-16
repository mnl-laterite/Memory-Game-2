package MemoryGame.game;

import javafx.geometry.Dimension2D;
import java.util.Random;

public class GameLogic {

  private int[][] gamePlayMap;
  private int gamePlayMapDepth;
  private short pairsFound;
  private short pairsTotal;
  private short piecesTurned;

  public GameLogic(Difficulty difficulty) {

    gamePlayMap = new int[5][5];
    resetGame(difficulty);

  }

  public void resetGame(Difficulty difficulty) {

    pairsFound = 0;
    piecesTurned = 0;
    setDifficulty(difficulty);
    shuffleGamePieces();

  }

  public void turnPiece(int i, int j) {

    gamePlayMap[i][j] *= -1;

  }

  public void hideUnfoundPairs () {

    int i, j;
    for (i = 0; i < gamePlayMapDepth; ++i)
      for (j = 0; j < gamePlayMapDepth; ++j) {

        if (gamePlayMap[i][j] > 0)
          gamePlayMap[i][j] *= -1;

      }

  }

  public void eliminateFoundPairs () {

    int i, j;

    for (i = 0; i < gamePlayMapDepth; ++i)
      for (j = 0; j < gamePlayMapDepth; ++j) {

        if (gamePlayMap[i][j] > 0)
          gamePlayMap[i][j] = 0;

      }

  }

  public boolean pairFound() {

    int pointsSearched = 0;
    int i, j;
    Dimension2D pointOne = new Dimension2D(-1, -1);
    Dimension2D pointTwo = new Dimension2D(-1, -1);
    boolean searching = true;

    for (i = 0; i < gamePlayMapDepth && searching; ++i)
      for (j = 0; j < gamePlayMapDepth; ++j) {

        if (gamePlayMap[i][j] > 0)
        {
          pointOne = new Dimension2D(i, j);
          searching = false;
          break;
        }
        pointsSearched++;
      }

    if (pointsSearched == pairsTotal * 2)
      return false;

    searching = true;

    for (i = gamePlayMapDepth - 1; i >= 0 && searching; --i)
      for (j = gamePlayMapDepth - 1; j >= 0; --j) {

        if (gamePlayMap[i][j] > 0) {
          pointTwo = new Dimension2D(i, j);
          searching = false;
          break;
        }
      }

    if (pointOne.getWidth() == -1 || pointTwo.getWidth() == -1)
      return false;

    if (pointOne.getWidth() == pointTwo.getWidth() && pointOne.getHeight() == pointTwo.getHeight())
      return false;

    if (gamePlayMap[(int) pointOne.getWidth()][(int) pointOne.getHeight()] ==
        gamePlayMap[(int) pointTwo.getWidth()][(int) pointTwo.getHeight()])
      return true;
      else return false;

  }

  public int getPairsFound() {
    return pairsFound;
  }

  public int getPairsTotal() {
    return pairsTotal;
  }

  public int getGamePlayMapDepth() {
    return gamePlayMapDepth;
  }

  public int getMapContents(int i, int j) {
    return gamePlayMap[i][j];
  }

  private void setDifficulty (Difficulty difficulty) {

    switch (difficulty) {

      case EASY:
        pairsTotal = 4;
        gamePlayMapDepth = 3;
        break;

      case HARD:
        pairsTotal = 12;
        gamePlayMapDepth = 5;
        break;

      case DEFAULT:
        pairsTotal = 8;
        gamePlayMapDepth = 4;
        break;

      default:
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
      shuffler[i] = i < pairsTotal ? -i - 1 :  -i - 1 + pairsTotal;
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
