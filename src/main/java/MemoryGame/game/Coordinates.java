package MemoryGame.game;

/**
 * Describes 2D coordinates for the game map.
 */
class Coordinates {

  private int rowIndex;
  private int colIndex;

  Coordinates (int rowIndex, int colIndex) {
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  int getRowIndex () {
    return rowIndex;
  }

  int getColIndex () {
    return colIndex;
  }
}
