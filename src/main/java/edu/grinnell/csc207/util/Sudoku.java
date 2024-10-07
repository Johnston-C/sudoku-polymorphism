package edu.grinnell.csc207.util;

public interface Sudoku {

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
  public boolean set(int x, int y, int v) throws Exception;

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
  public boolean canSet(int x, int y, int v) throws Exception;

  /**
   * Attempts to solve the sudoku.
   * @return If the solve worked.
   */
  public boolean solveRecursive();

  /**
   * Prints out the sudoku as a formatted string.
   * @return The formatted string.
   */
  public String toString();
} // Sudoku