package MemoryGame.game;

import org.junit.Test;

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
}
