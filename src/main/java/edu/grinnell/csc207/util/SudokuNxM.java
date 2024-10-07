package edu.grinnell.csc207.util;


public class SudokuNxM implements Sudoku {

  // Fields

  private int xDimSize;
  private int yDimSize;
  private int[][] grid;
  private int[][] clueGrid;

  // Constructors

  public SudokuNxM(int xSize, int ySize, int[][] clues) throws Exception {
    this.xDimSize = xSize;
    this.yDimSize = ySize;
    this.grid = new int[this.xDimSize * this.yDimSize][xDimSize*yDimSize];
    if (this.xDimSize * this.yDimSize != clues.length || this.xDimSize * this.yDimSize != clues[0].length) {
      throw new Exception("Bad clue size");
    } // if
    this.clueGrid = clues;
    for (int i = 0; i < this.xDimSize * this.yDimSize; i++) {
      for (int j = 0; j < this.xDimSize * this.yDimSize; j++) {
        this.grid[i][j] = this.clueGrid[i][j];
      } // for [j]
    } // for [i]
  } // SudokuNxM(int, int, int[][])

  // Methods
  
  /**
   * Attempt to set the value at a point.
   *
   * @param x
   *   The x coordinate of a point
   * @param y
   *   The y coordinate of a point
   * @param v
   *   The value to store at the point
   * @return if the assignment was successful, may be ignored.
   */
  public boolean set(int x, int y, int v) throws Exception {
    if (canSet(x, y, v)) {
      this.grid[x][y] = v;
      return true;
    } else {
      return false;
    } // if / else
  } // set(int, int, int)

  /**
   * Determines if you can set the value at a point.
   *
   * @param x
   *   The x coordinate of a point
   * @param y
   *   The y coordinate of a point
   * @param v
   *   The value to store at the point
   * @return if the assignment is possible.
   */
  public boolean canSet(int x, int y, int v) throws Exception {
    if ((x >= 0) && (x < this.xDimSize * this.yDimSize) && (y >=0) && (y < this.xDimSize * this.yDimSize )&& (v >= 0) && (v <= this.xDimSize * this.yDimSize)) {
      return (((v == 0) || (! (inRow(x, y, v) || inCol(x, y, v) || inBox(x, y, v)))) && (! isHint(x, y)));
    } else {
      throw new Exception("Bad parameters (Out of accepted range).");
    } // if / else
  } // canSet(int, int, int)

  /**
   * Attempts to solve the sudoku.
   */
  public boolean solveRecursive() {
    return solveRecursiveHelper(0);
  }

  /**
   * Attempts to solve the sudoku by filling the value at index, then filling the next value. If the index is equal to this.xDimSize * this.yDimSize * this.xDimSize * this.yDimSize, return true as the grid is done.
   */
  public boolean solveRecursiveHelper(int index) {
    if (index == (this.xDimSize * this.yDimSize * this.xDimSize * this.yDimSize)) {
      return true;
    } else if (this.grid[index % (this.xDimSize * this.yDimSize)][index / (this.xDimSize * this.yDimSize)] != 0) {
      return solveRecursiveHelper(index + 1);
    } else {
      for (int i = 0; i < this.xDimSize * this.yDimSize; i++) {
        try{
          if (set(index % (this.xDimSize * this.yDimSize), index / (this.xDimSize * this.yDimSize), i + 1)) {
            if (solveRecursiveHelper(index + 1)) {
              return true;
            } else {
              set(index % (this.xDimSize * this.yDimSize), index / (this.xDimSize * this.yDimSize), 0);
            } // if / else
          } // if
        } catch (Exception e) {
          // Should never happen
          System.err.println("Exception in solveRecursive.");
        }
      } // for [i]
      return false;
    } // if / else if / else
  } // solveRecursiveHelper(int)

  /**
   * Prints out the sudoku as a formatted string.
   */
  public String toString() {
    String output = "";
    for (int i = 0; i < this.xDimSize * this.yDimSize; i++) {
      if ((i % this.xDimSize == 0) && (i != 0)) {
        output += "-".repeat(this.xDimSize * (this.yDimSize + 1) * 2 - 1);
        output += "\n";
      } // if
      for (int j = 0; j < this.xDimSize * this.yDimSize; j++) {
        if ((j % this.yDimSize == 0) && (j != 0)) {
          output += " |";
        } // if
        output += " " + grid[i][j];
      } // for [j]
      output += " \n";
    } // for [i]
    return output;
  } // toString()

  /**
   * Determines if a row has a value already in it.
  * @param x
   *   The x coordinate of a point
   * @param y
   *   The y coordinate of a point
   * @param v
   *   The value to check
   * @return if the row already has the value or not.
   */
  private boolean inRow(int x, int y, int v) {
    for (int i = 0; i < this.xDimSize * this.yDimSize; i++) {
      if (this.grid[x][i] == v) {
        return true;
      }
    } // for [i]
    return false;
  } // inRow(int, int, int)

  /**
   * Determines if a column has a value already in it.
  * @param x
   *   The x coordinate of a point
   * @param y
   *   The y coordinate of a point
   * @param v
   *   The value to check
   * @return if the row already has the value or not.
   */
  private boolean inCol(int x, int y, int v) {
    for (int i = 0; i < this.xDimSize * this.yDimSize; i++) {
      if (this.grid[i][y] == v) {
        return true;
      }
    } // for [i]
    return false;
  } // inCol(int, int, int)

  /**
   * Determines if a box has a value already in it.
  * @param x
   *   The x coordinate of a point
   * @param y
   *   The y coordinate of a point
   * @param v
   *   The value to check
   * @return if the row already has the value or not.
   */
  private boolean inBox(int x, int y, int v) {
    int bigX = x / this.xDimSize * this.yDimSize;
    int bigY = y / this.xDimSize * this.yDimSize;
    for (int i = 0; i < this.xDimSize * this.yDimSize; i++) {
      if (this.grid[bigX + i % this.xDimSize][bigY + i / this.xDimSize] == v) {
        return true;
      }
    } // for [i]
    return false;
  } // inBox(int, int, int)

  /**
   * Determines if a cell has a hint value stored in it.
  * @param x
   *   The x coordinate of a point
   * @param y
   *   The y coordinate of a point
   * @return if the point has a hint value stored in it.
   */
  private boolean isHint(int x, int y) {
    return (this.clueGrid[x][y] != 0);
  } // isHint(int, int)

  /**
   * Returns the height of a box.
   * @return The height of a box.
   */
  public int xDimSize() {
    return xDimSize;
  } // xDimSize()

  /**
   * Returns the width of a box.
   * @return The width of a box.
   */
  public int yDimSize() {
    return yDimSize;
  } // yDimSize()

  /**
   * Returns the grid of the sudoku board.
   * @return The grid of the sudoku board.
   */
  public int[][] grid() {
    return grid;
  }
} // SudokuNxM