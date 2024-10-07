package edu.grinnell.csc207.util;


public class LeaperSudoku implements Sudoku {

  // Fields

  private Sudoku base;
  private int[] xDeltas;
  private int[] yDeltas;

  // Constructors

  public LeaperSudoku(Sudoku base, int xInput, int yInput) throws Exception {
    this.base = base;
    this.xDeltas = new int[8];
    this.yDeltas = new int[8];
    int temp;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 4; j++) {
        this.xDeltas[i*4+j] = xInput;
        this.yDeltas[i*4+j] = yInput;
        temp = xInput;
        xInput = -yInput;
        yInput = temp;
      } // for [j]
      temp = xInput;
      xInput = yInput;
      yInput = temp;
    } // for [i]
  } // LeaperSudoku(Sudoku, int, int);

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
      return base.set(x,y,v);
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
   * @return If the assignment is possible.
   */
  public boolean canSet(int x, int y, int v) throws Exception {
    if (v == 0) {
      return base.canSet(x, y, v);
    }
    for (int i = 0; i < xDeltas.length; i++) {
      if ((x + xDeltas[i] >= 0) && (x + xDeltas[i] < base.xDimSize() * base.yDimSize()) && (y + yDeltas[i] >= 0) && (y + yDeltas[i] < base.xDimSize() * base.yDimSize()) && (base.grid()[x + xDeltas[i]][y + yDeltas[i]] == v)) {
        return false;
      }
    } // for [i]
    return base.canSet(x, y, v);
  } // canSet(int, int, int)

  /**
   * Attempts to solve the sudoku.
   * @return If the solve worked.
   */
  public boolean solveRecursive() {
    return solveRecursiveHelper(0);
  }

  /**
   * Attempts to solve the sudoku by filling the value at index, then filling the next value. If the index is equal to base.xDimSize() * base.yDimSize() * base.xDimSize() * base.yDimSize(), return true as the grid is done.
   */
  public boolean solveRecursiveHelper(int index) {
    if (index == (base.xDimSize() * base.yDimSize() * base.xDimSize() * base.yDimSize())) {
      return true;
    } else if (base.grid()[index % (base.xDimSize() * base.yDimSize())][index / (base.xDimSize() * base.yDimSize())] != 0) {
      return solveRecursiveHelper(index + 1);
    } else {
      for (int i = 0; i < base.xDimSize() * base.yDimSize(); i++) {
        try{
          if (set(index % (base.xDimSize() * base.yDimSize()), index / (base.xDimSize() * base.yDimSize()), i + 1)) {
            if (solveRecursiveHelper(index + 1)) {
              return true;
            } else {
              set(index % (base.xDimSize() * base.yDimSize()), index / (base.xDimSize() * base.yDimSize()), 0);
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
   * @return The formatted string.
   */
  public String toString() {
    return base.toString();
  }

  /**
   * Returns the height of a box.
   * @return The height of a box.
   */
  public int xDimSize() {
    return base.xDimSize();
  } // xDimSize()

  /**
   * Returns the width of a box.
   * @return The width of a box.
   */
  public int yDimSize() {
    return base.yDimSize();
  } // yDimSize()

  /**
   * Returns the grid of the sudoku board.
   * @return The grid of the sudoku board.
   */
  public int[][] grid() {
    return base.grid();
  }
} // LeaperSudoku