package MemoryGame.game;

import javafx.geometry.Dimension2D;

import java.util.Random;

/**
 * Constructs the underlying game board.
 *
 * @author mnl-laterite
 */
public class GameLogic {

  /**
   * Game board map where each entry is defined as follows:
   * <pre>
   * 0: no piece or piece eliminated.
   * 1 to 12: piece with given #ID turned bottom-side down (i.e. unique top-side piece sprite visible to user).
   * 13 to 24: piece with x - 12 #ID is marked for elimination.
   * -12 to -1: piece with given #ID turned bottom-side up (i.e. unique top-side piece sprite not visible to user).
   * </pre>
   */
  private int[][] gamePlayMap;

  /**
   * Index search limit. Depends on difficulty level.
   */
  private int gamePlayMapDepth;

  /**
   * Game piece pairs found on the game board.
   */
  private int pairsFound;

  /**
   * Total number of game piece pairs on the game board. Depends on difficulty level.
   */
  private int pairsTotal;

  /**
   * Counter for the number of pieces turned in a round (cannot be more than 2).
   */
  private int piecesTurned;

  /**
   * Saves the time when a found pair is marked for elimination.
   */
  private long pairMarkedTime = -1;

  /**
   * Saves the time when the user has flipped two pieces over.
   */
  private long piecesFlippedTime = -1;

  /**
   * Constructs the game board at its maximum potentially needed size.
   *
   * @param difficulty the difficulty level of the game.
   */
  public GameLogic (Difficulty difficulty) {

    gamePlayMap = new int[5][5];
    resetGame(difficulty);

  }

  /**
   * Limits the game board size based on game difficulty level, resets number of found pairs to 0, and places the game
   * pieces randomly on the game board, bottom-side up (i.e. unique piece sprite not visible to user).
   *
   * @param difficulty the difficulty level of the game.
   */
  public void resetGame (Difficulty difficulty) {

    pairsFound = 0;
    piecesTurned = 0;
    setDifficultyParameters(difficulty);
    shuffleGamePieces();
  }

  /**
   * Updates the state of the game after a required time.
   */
  public void updateState () {

    if (pairMarkedTime != -1 && System.currentTimeMillis() - pairMarkedTime > 700) {
      eliminateMarkedPairs();
      pairMarkedTime = -1;
    }

    if (piecesTurned == 2 && piecesFlippedTime != -1 && System.currentTimeMillis() - piecesFlippedTime > 2100) {
      hideUnfoundPairs();
      piecesFlippedTime = -1;
    }

  }

  /**
   * Modifies the state of the game board according to user input.
   *
   * @param rowIndex the row index in the game board matrix.
   * @param colIndex the column index in the game board matrix.
   */
  public void playCoordinates (int rowIndex, int colIndex) {

    if (rowIndex <= getGamePlayMapDepth() && colIndex <= getGamePlayMapDepth()) {

      if (pieceStillInGame(rowIndex, colIndex) && !isMarkedForElimination(rowIndex, colIndex)) {
        if (!pieceTurned(rowIndex, colIndex)) {

          turnPiece(rowIndex, colIndex);
          piecesTurned++;

        } else {

          hideUnfoundPairs();
          piecesTurned = 0;
        }
      }

      if (piecesTurned == 2) {
        if (pairFound()) {
          markPairForElimination();
          pairMarkedTime = System.currentTimeMillis();
          piecesTurned = 0;
        } else {
          piecesFlippedTime = System.currentTimeMillis();
        }
      }

      if (piecesTurned > 2) {

        piecesTurned = 1;
        hideUnfoundPairs();
        turnPiece(rowIndex, colIndex);
      }
    }

  }

  /**
   * Checks if the piece at given coordinates is turned bottom-side down (i.e. unique piece sprite visible
   * to the user).
   *
   * @param rowIndex row index in the game board matrix.
   * @param colIndex column index in the game board matrix.
   * @return true if the piece is turned bottom-side down.
   */
  public boolean pieceTurned (int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex] > 0;
  }

  /**
   * Checks if the piece at given coordinates is marked for elimination.
   *
   * @param rowIndex row index in the game board matrix.
   * @param colIndex column index in the game board matrix.
   * @return true if the piece is marked for elimination.
   */
  public boolean isMarkedForElimination (int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex] > 12;
  }

  /**
   * Checks if there is a piece placed at given coordinates in the game board matrix.
   *
   * @param rowIndex row index in the game board matrix.
   * @param colIndex column index in the game board matrix.
   * @return true if there is no piece at given coordinates.
   */
  public boolean pieceStillInGame (int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex] != 0;
  }

  /**
   * Flips the piece on the game board found at given matrix coordinates.
   *
   * @param rowIndex row index in the game board matrix.
   * @param colIndex column index in the game board matrix.
   */
  void turnPiece (int rowIndex, int colIndex) {
    gamePlayMap[rowIndex][colIndex] *= -1;
  }

  /**
   * Hides all pieces that are turned bottom-side down, under the assumption that they do not match (aren't pairs).
   * No check for pair match is performed.
   */
  void hideUnfoundPairs () {

    for (int i = 0; i < gamePlayMapDepth; ++i) {
      for (int j = 0; j < gamePlayMapDepth; ++j) {
        if (pieceTurned(i, j)) {
          turnPiece(i, j);
        }
      }
    }
  }

  /**
   * Marks a pair to be later eliminated from the game board.
   */
  private void markPairForElimination () {

    for (int i = 0; i < gamePlayMapDepth; ++i) {
      for (int j = 0; j < gamePlayMapDepth; ++j) {
        if (pieceTurned(i, j)) {
          gamePlayMap[i][j] += 12;
        }
      }
    }
  }

  /**
   * Eliminates marked pairs from the game.
   */
  private void eliminateMarkedPairs () {

    for (int i = 0; i < gamePlayMapDepth; ++i) {
      for (int j = 0; j < gamePlayMapDepth; ++j) {
        if (isMarkedForElimination(i, j)) {
          eliminatePiece(i, j);
        }
      }
    }


    ++pairsFound;
  }

  /**
   * Eliminates the piece found at given matrix coordinates from the game board.
   */
  private void eliminatePiece (int rowIndex, int colIndex) {
    gamePlayMap[rowIndex][colIndex] = 0;
  }

  /**
   * Checks for matching pieces and eliminates them from the game board.
   *
   * @return true if a pair was found and eliminated, i.e. if the user has turned two matching pieces bottom-side down.
   */
  boolean pairFound () {

    /*
     * The number of points/coordinate pairs searched on the game board. Cannot exceed the total number of pieces
     * on the game board.
     */
    int pointsSearched = 0;
    int i, j;

    Dimension2D turnedPieceOne = null;
    Dimension2D turnedPieceTwo = null;

    boolean searching = true;

    // Looks for the first piece turned.
    for (i = 0; i < gamePlayMapDepth && searching; ++i) {
      for (j = 0; j < gamePlayMapDepth; ++j) {
        if (pieceTurned(i, j)) {
          turnedPieceOne = new Dimension2D(i, j);
          searching = false;
          break;
        }
        pointsSearched++;
      }
    }

    if (pointsSearched == pairsTotal * 2)
      return false; // all pieces were searched through and none were found bottom-side down.

    searching = true;

    // Looks for the second piece turned.
    for (i = gamePlayMapDepth - 1; i >= 0 && searching; --i) {
      for (j = gamePlayMapDepth - 1; j >= 0; --j) {
        if (pieceTurned(i, j)) {
          turnedPieceTwo = new Dimension2D(i, j);
          searching = false;
          break;
        }
      }
    }

    if (turnedPieceOne.getWidth() == turnedPieceTwo.getWidth() &&
      turnedPieceOne.getHeight() == turnedPieceTwo.getHeight())
      return false; // only one piece was turned.

    if (gamePlayMap[(int) turnedPieceOne.getWidth()][(int) turnedPieceOne.getHeight()] ==
      gamePlayMap[(int) turnedPieceTwo.getWidth()][(int) turnedPieceTwo.getHeight()]) {

      return true; // a pair has been found: the two found pieces match.
    } else return false; // the two found pieces don't match.
  }

  /**
   * @return the number of pairs found so far.
   */
  public int getPairsFound () {
    return pairsFound;
  }

  /**
   * @return the total number of pairs on the game board (which depends on difficulty level).
   */
  public int getPairsTotal () {
    return pairsTotal;
  }

  /**
   * @return the index search limit (which depends on difficulty level).
   */
  public int getGamePlayMapDepth () {
    return gamePlayMapDepth;
  }

  /**
   * Extracts the piece #ID found at given coordinates.
   *
   * @param rowIndex row index in the game board matrix.
   * @param colIndex column index in the game board matrix.
   * @return the piece #ID found.
   */
  public int getMapContents (int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex];
  }

  /**
   * Extracts the piece #ID at given coordinates for a piece that is set to be eliminated.
   *
   * @param rowIndex row index in the game board matrix.
   * @param colIndex column index in the game board matrix.
   * @return the piece #ID needed.
   */
  public int getMarkedForEliminationMapContents (int rowIndex, int colIndex) {
    return gamePlayMap[rowIndex][colIndex] - 12;
  }

  /**
   * Sets the parameters of the game that depend on difficulty level.
   */
  private void setDifficultyParameters (Difficulty difficulty) {
    pairsTotal = difficulty.pairsTotal;
    gamePlayMapDepth = difficulty.gamePlayMapDepth;
  }

  /**
   * Places game piece #IDs at random on the game board.
   */
  private void shuffleGamePieces () {

    int i, j, temp;

    Random rand = new Random();
    int[] shuffler = new int[pairsTotal * 2];

    for (i = 0; i < pairsTotal * 2; ++i) {
      shuffler[i] = i < pairsTotal ? -i - 1 : -i - 1 + pairsTotal;
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
        gamePlayMap[i][j] = temp < 2 * pairsTotal ? shuffler[temp] : 0;
        ++temp;
      }
    }
  }

}
