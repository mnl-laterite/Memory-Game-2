package MemoryGame.game;

public enum Difficulty {
  EASY(4, 3),
  DEFAULT(8, 4),
  HARD(12, 5);

  private Difficulty(int pairsTotal, int gamePlayMapDepth) {
    this.pairsTotal = pairsTotal;
    this.gamePlayMapDepth = gamePlayMapDepth;
  }

  public final int pairsTotal;
  public final int gamePlayMapDepth;
}
