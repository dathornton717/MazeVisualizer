package main;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import solver.AStarEuclideanSolver;
import solver.GreedyEuclideanSolver;
import solver.GreedyManhattanSolver;
import solver.AStarManhattanSolver;
import solver.BreadthFirstSolver;
import solver.DepthFirstSolver;
import solver.Maze;

public class Main {
  public static final int WIDTH = 600;
  public static final int HEIGHT = 600;
  public static final int SQUARE_LEN = 10;

  public static void main(String[] args) {
    int cols = WIDTH / SQUARE_LEN;
    int rows = HEIGHT / SQUARE_LEN;

    GridLayout layout = new GridLayout(0, 2);
    JPanel panel = new JPanel();
    panel.setLayout(layout);

    JFrame frame = new JFrame();
    frame.setSize(WIDTH, HEIGHT);

    Maze dfs = new DepthFirstSolver(cols, rows);
    panel.add(dfs);

    String labelText = "<html>Results (Nodes Visited):</html>";
    JLabel results = new JLabel(labelText, JLabel.CENTER);
    panel.add(results);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
    dfs.solve();
    labelText = setNumNodesVisitedLabel(
            results,
            labelText,
            "Depth First Search: ",
            dfs.getNodesVisited());
    wait(3000);

    // Breadth First Search
    Maze bfs = new BreadthFirstSolver(dfs);
    solve(panel, frame, dfs, bfs);
    labelText = setNumNodesVisitedLabel(
            results,
            labelText,
            "Breadth First Search: ",
            bfs.getNodesVisited());
    wait(3000);

    // Greedy Best First Search using Manhattan Distance
    Maze greedyManhattan = new GreedyManhattanSolver(bfs);
    solve(panel, frame, bfs, greedyManhattan);
    labelText = setNumNodesVisitedLabel(
            results,
            labelText,
            "Greedy Best First Search (Manhattan Distance): ",
            greedyManhattan.getNodesVisited());
    wait(3000);

    // A* Search with Manhattan Distance
    Maze aStarManhattan = new AStarManhattanSolver(bfs);
    solve(panel, frame, greedyManhattan, aStarManhattan);
    labelText = setNumNodesVisitedLabel(
            results,
            labelText,
            "A* Search (Manhattan Distance): ",
            aStarManhattan.getNodesVisited());
    wait(3000);

    // Greedy Best First Search with Euclidean Distance
    Maze greedyEuclidean = new GreedyEuclideanSolver(bfs);
    solve(panel, frame, aStarManhattan, greedyEuclidean);
    labelText = setNumNodesVisitedLabel(
            results,
            labelText,
            "Greedy Best First Search (Euclidean Distance): ",
            greedyEuclidean.getNodesVisited());
    wait(3000);

    // A* Search with Euclidean Distance
    Maze aStarEuclidean = new AStarEuclideanSolver(bfs);
    solve(panel, frame, greedyEuclidean, aStarEuclidean);
    setNumNodesVisitedLabel(
            results,
            labelText,
            "A* Search (Euclidean Distance): ",
            aStarEuclidean.getNodesVisited());
  }

  /**
   * Wait a certain number of milliseconds.
   * @param millis The number of milliseconds to wait
   */
  private static void wait(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Solve the given maze and add it to the JPanel and JFrame.
   * @param panel The JPanel to add the maze to
   * @param frame The JFrame to add the maze to
   * @param toRemove The existing maze to remove
   * @param toSolve The new maze to solve
   */
  private static void solve(JPanel panel, JFrame frame, Maze toRemove, Maze toSolve) {
    panel.remove(toRemove);
    panel.add(toSolve, 0);
    frame.revalidate();
    frame.repaint();
    toSolve.solve();
  }

  /**
   * Add new results to the given jlabel.
   * @param jLabel The JLabel to add results to
   * @param label The label text
   * @param toSet The String results to set
   * @param numNodes The number of nodes visited
   */
  private static String setNumNodesVisitedLabel(JLabel jLabel, String label, String toSet, int numNodes) {
    label = label.substring(0, label.indexOf("</html>"));
    label += "<br/>" + toSet + numNodes + "</html>";
    jLabel.setText(label);
    return label;
  }
}
