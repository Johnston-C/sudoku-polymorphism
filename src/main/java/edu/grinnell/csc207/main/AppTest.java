package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.SudokuNxM;
import edu.grinnell.csc207.util.LeaperSudoku;
import edu.grinnell.csc207.util.Sudoku;
import java.io.PrintWriter;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
   * Our cute Halloween pumpkin.
   *
   * @param args
   *   Command-line arguments (currently ignored).
   *
   * @exception Exception
   *   If something goes wrong with one of the underlying classes.
   */
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);
    Sudoku a = new SudokuNxM(2, 2, new int[4][4]);
    a.set(0, 0, 1);
    pen.println(a.toString());
    Sudoku b = new SudokuNxM(3, 2, new int[6][6]);
    b.set(0, 0, 1);
    pen.println(b.toString());
    Sudoku c = new LeaperSudoku(new SudokuNxM(2, 2, new int[4][4]), 2, 1);
    c.set(0, 0, 1);
    pen.println(c.toString());
    a.solveRecursive();
    pen.println(a.toString());
    c.solveRecursive();
    pen.println(c.toString());
  }
}