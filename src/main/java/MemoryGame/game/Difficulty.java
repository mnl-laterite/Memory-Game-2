package MemoryGame.game;

/**
 * Defines the possible difficulties of the game and the relevant parameter value changes attached to each difficulty:
 * the total number of pairs to be found in a game with that respective difficulty level, and the iterator limit or map
 * depth used when arranging the pieces on the game board.
 * @author mnl-laterite
 */
public enum Difficulty {
  EASY(4, 3),
  DEFAULT(8, 4),
  HARD(12, 5);

  Difficulty (int pairsTotal, int gamePlayMapDepth) {
    this.pairsTotal = pairsTotal;
    this.gamePlayMapDepth = gamePlayMapDepth;
  }

  public final int pairsTotal;
  public final int gamePlayMapDepth;
}
