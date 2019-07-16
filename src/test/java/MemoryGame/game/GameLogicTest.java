package MemoryGame.game;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GameLogicTest {

  @Test
  public void testSetDifficulty() {
    GameLogic gameLogic = new GameLogic(Difficulty.DEFAULT);
    assertEquals(8, gameLogic.getPairsTotal());

    gameLogic.resetGame(Difficulty.EASY);
    assertEquals(4,gameLogic.getPairsTotal());

    gameLogic.resetGame(Difficulty.HARD);
    assertEquals(12,gameLogic.getPairsTotal());

  }

  @Test
  public void testTurnPiece() {
    GameLogic gameLogic = new GameLogic(Difficulty.EASY);

    assert gameLogic.getMapContents(0,0) < 0;
    gameLogic.turnPiece(0,0);
    assert gameLogic.getMapContents(0,0) > 0;

  }

  @Test
  public void testHideUnfoundPairs () {
    GameLogic gameLogic = new GameLogic(Difficulty.DEFAULT);

    gameLogic.turnPiece(0,0);
    gameLogic.turnPiece(0,1);

    assert gameLogic.getMapContents(0,0) > 0;
    assert gameLogic.getMapContents(0,1) > 0;

    gameLogic.hideUnfoundPairs();

    assert gameLogic.getMapContents(0,0) < 0;
    assert gameLogic.getMapContents(0,1) < 0;
  }

  @Test
  public void testPairFound() {
    GameLogic gameLogic = new GameLogic(Difficulty.DEFAULT);

    int temp = gameLogic.getMapContents(0,0);
    int searchBound = gameLogic.getGamePlayMapDepth();
    int i, itemp = 0, j, jtemp = 0;
    boolean search = true;

    for (i = searchBound -1 ; i >= 0 && search == true; --i)
      for (j = searchBound - 1; j >= 0; --j) {

        if (gameLogic.getMapContents(i, j) == temp) {
          itemp = i;
          jtemp = j;
          search = false;
        }
        break;
      }

    assert gameLogic.getMapContents(0,0) == gameLogic.getMapContents(itemp,jtemp);

    gameLogic.turnPiece(0,0);
    gameLogic.turnPiece(itemp, jtemp);

    assert gameLogic.getMapContents(0,0) > 0;
    assert gameLogic.getMapContents(itemp,jtemp) > 0;

    assertEquals(true,gameLogic.pairFound());

  }

  @Test
  public void testEliminateFoundPairs () {
    GameLogic gameLogic = new GameLogic(Difficulty.HARD);

    int temp = gameLogic.getMapContents(0,0);
    int searchBound = gameLogic.getGamePlayMapDepth();
    int i, itemp = 0, j, jtemp = 0;
    boolean search = true;

    for (i = searchBound -1 ; i >= 0 && search == true; --i)
      for (j = searchBound - 1; j >= 0; --j) {

        if (gameLogic.getMapContents(i, j) == temp) {
          itemp = i;
          jtemp = j;
          search = false;
        }
        break;
      }

    gameLogic.turnPiece(0,0);
    gameLogic.turnPiece(itemp, jtemp);

    assert gameLogic.getMapContents(0,0) > 0;
    assert gameLogic.getMapContents(itemp,jtemp) > 0;

    gameLogic.eliminateFoundPairs();

    assertEquals(0,gameLogic.getMapContents(0,0));
    assertEquals(0,gameLogic.getMapContents(itemp,jtemp));

  }

  @Test
  public void testFullGameLoopEasy () {
    GameLogic gameLogic = new GameLogic(Difficulty.EASY);

    int tempOne, tempTwo;
    int i1, j1, i2, j2;
    int piecesTurned = 0;
    Random index = new Random();
    int indexBound = gameLogic.getGamePlayMapDepth();

    while (gameLogic.getPairsTotal() - gameLogic.getPairsFound() > 0) {

      i1 = index.nextInt(indexBound);
      j1 = index.nextInt(indexBound);
      if (gameLogic.getMapContents(i1, j1) != 0) {

        if (gameLogic.getMapContents(i1, j1) < 0) {
          gameLogic.turnPiece(i1, j1);
          tempOne = gameLogic.getMapContents(i1, j1);
          assert tempOne > 0;
          piecesTurned++;
        }
      }

      if (piecesTurned < 2) {
        i2 = index.nextInt(indexBound);
        j2 = index.nextInt(indexBound);
        if (gameLogic.getMapContents(i2, j2) != 0) {

          if (gameLogic.getMapContents(i2, j2) < 0) {
            gameLogic.turnPiece(i2, j2);
            tempTwo = gameLogic.getMapContents(i2, j2);
            assert tempTwo > 0;
            piecesTurned++;
          }
        }
      }


      if (gameLogic.pairFound()) {
        gameLogic.eliminateFoundPairs();
        piecesTurned = 0;
      }
      else {
        if (piecesTurned >= 2)
          piecesTurned = 0;
        gameLogic.hideUnfoundPairs();
      }

    }

    assertEquals(gameLogic.getPairsTotal(),gameLogic.getPairsFound());

    for (i1 = 0; i1 < indexBound; ++i1)
      for (j1 = 0; j1 < indexBound; ++j1)
        assertEquals(0,gameLogic.getMapContents(i1,j1));
  }

  @Test
  public void testFullGameLoopDefault () {
    GameLogic gameLogic = new GameLogic(Difficulty.DEFAULT);

    int tempOne, tempTwo;
    int i1, j1, i2, j2;
    int piecesTurned = 0;
    Random index = new Random();
    int indexBound = gameLogic.getGamePlayMapDepth();

    while (gameLogic.getPairsTotal() - gameLogic.getPairsFound() > 0) {

      i1 = index.nextInt(indexBound);
      j1 = index.nextInt(indexBound);
      if (gameLogic.getMapContents(i1, j1) != 0) {

        if (gameLogic.getMapContents(i1, j1) < 0) {
          gameLogic.turnPiece(i1, j1);
          tempOne = gameLogic.getMapContents(i1, j1);
          assert tempOne > 0;
          piecesTurned++;
        }
      }

      if (piecesTurned < 2) {
        i2 = index.nextInt(indexBound);
        j2 = index.nextInt(indexBound);
        if (gameLogic.getMapContents(i2, j2) != 0) {

          if (gameLogic.getMapContents(i2, j2) < 0) {
            gameLogic.turnPiece(i2, j2);
            tempTwo = gameLogic.getMapContents(i2, j2);
            assert tempTwo > 0;
            piecesTurned++;
          }
        }
      }


      if (gameLogic.pairFound()) {
        gameLogic.eliminateFoundPairs();
        piecesTurned = 0;
      }
      else {
        if (piecesTurned >= 2)
          piecesTurned = 0;
        gameLogic.hideUnfoundPairs();
      }

    }

    assertEquals(gameLogic.getPairsTotal(),gameLogic.getPairsFound());

    for (i1 = 0; i1 < indexBound; ++i1)
      for (j1 = 0; j1 < indexBound; ++j1)
        assertEquals(0,gameLogic.getMapContents(i1,j1));
  }

  @Test
  public void testFullGameLoopHard () {
    GameLogic gameLogic = new GameLogic(Difficulty.HARD);

    int tempOne, tempTwo;
    int i1, j1, i2, j2;
    int piecesTurned = 0;
    Random index = new Random();
    int indexBound = gameLogic.getGamePlayMapDepth();

    while (gameLogic.getPairsTotal() - gameLogic.getPairsFound() > 0) {

      i1 = index.nextInt(indexBound);
      j1 = index.nextInt(indexBound);
      if (gameLogic.getMapContents(i1, j1) != 0) {

        if (gameLogic.getMapContents(i1, j1) < 0) {
          gameLogic.turnPiece(i1, j1);
          tempOne = gameLogic.getMapContents(i1, j1);
          assert tempOne > 0;
          piecesTurned++;
        }
      }

      if (piecesTurned < 2) {
        i2 = index.nextInt(indexBound);
        j2 = index.nextInt(indexBound);
        if (gameLogic.getMapContents(i2, j2) != 0) {

          if (gameLogic.getMapContents(i2, j2) < 0) {
            gameLogic.turnPiece(i2, j2);
            tempTwo = gameLogic.getMapContents(i2, j2);
            assert tempTwo > 0;
            piecesTurned++;
          }
        }
      }


      if (gameLogic.pairFound()) {
        gameLogic.eliminateFoundPairs();
        piecesTurned = 0;
      }
      else {
        if (piecesTurned >= 2)
          piecesTurned = 0;
        gameLogic.hideUnfoundPairs();
      }

    }

    assertEquals(gameLogic.getPairsTotal(),gameLogic.getPairsFound());

    for (i1 = 0; i1 < indexBound; ++i1)
      for (j1 = 0; j1 < indexBound; ++j1)
        assertEquals(0,gameLogic.getMapContents(i1,j1));
  }
  
}
