package MemoryGame.game;

import javafx.geometry.Dimension2D;
import java.util.Random;

public class GameLogic {

  private int[][] gamePlayMap;
  private int gamePlayMapDepth;
  private int pairsFound;
  private int pairsTotal;

  public GameLogic(Difficulty difficulty) {

    gamePlayMap = new int[5][5];
    resetGame(difficulty);

  }

  public void resetGame(Difficulty difficulty) {

    pairsFound = 0;
    setDifficulty(difficulty);
    shuffleGamePieces();
  }

  public boolean pieceTurned(int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex] > 0;
  }

  public boolean pieceEliminated (int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex] == 0;
  }

  public void turnPiece(int rowIndex, int colIndex) {
    gamePlayMap[rowIndex][colIndex] *= -1;
  }

  public void hideUnfoundPairs () {

    int i, j;

    for (i = 0; i < gamePlayMapDepth; ++i)
      for (j = 0; j < gamePlayMapDepth; ++j) {
        if (pieceTurned(i,j))
          turnPiece(i,j);
      }
  }

  private void eliminatePiece(int rowIndex, int colIndex) {
    gamePlayMap[rowIndex][colIndex] = 0;
  }

  public void eliminateFoundPairs () {

    int i, j;

    for (i = 0; i < gamePlayMapDepth; ++i)
      for (j = 0; j < gamePlayMapDepth; ++j) {
        if (pieceTurned(i,j))
          eliminatePiece(i,j);
      }

    pairsFound++;
  }

  public boolean pairFound() {

    int pointsSearched = 0;
    int i, j;

    Dimension2D pieceOne = new Dimension2D(-1, -1);
    Dimension2D pieceTwo = new Dimension2D(-1, -1);

    boolean searching = true;

    for (i = 0; i < gamePlayMapDepth && searching; ++i)
      for (j = 0; j < gamePlayMapDepth; ++j) {
        if (pieceTurned(i,j)) {
          pieceOne = new Dimension2D(i, j);
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
        if (pieceTurned(i,j)) {
          pieceTwo = new Dimension2D(i, j);
          searching = false;
          break;
        }
      }

    if (pieceOne.getWidth() == -1 || pieceTwo.getWidth() == -1)
      return false;

    if (pieceOne.getWidth() == pieceTwo.getWidth() && pieceOne.getHeight() == pieceTwo.getHeight())
      return false;

    if (gamePlayMap[(int) pieceOne.getWidth()][(int) pieceOne.getHeight()] ==
        gamePlayMap[(int) pieceTwo.getWidth()][(int) pieceTwo.getHeight()])
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

  public int getMapContents(int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex];
  }

  private void setDifficulty (Difficulty difficulty) {
    pairsTotal = difficulty.pairsTotal;
    gamePlayMapDepth = difficulty.gamePlayMapDepth;
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
