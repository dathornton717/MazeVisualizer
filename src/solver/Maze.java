package solver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import main.Main;
import nodes.Edge;
import nodes.Node;

// Represents a maze to traverse.
public abstract class Maze extends JPanel {
  protected List<List<Node>> nodes;
  int cols;
  int rows;
  boolean solved;
  int nodesVisited;
  List<Node> solution;

  Maze(Maze maze) {
    this.nodes = maze.getNodes();
    this.cols = maze.getCols();
    this.rows = maze.getRows();
    this.solved = false;
    this.nodesVisited = 0;
    this.solution = new ArrayList<Node>();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Node n = nodes.get(i).get(j);
        nodes.get(i).set(j, new Node(n.getRow(), n.getCol(), n.isTop(),
                n.isRight(), n.isBottom(), n.isLeft()));
      }
    }
  }

  Maze(int cols, int rows) {
    this.cols = cols;
    this.rows = rows;
    this.nodes = new ArrayList<List<Node>>();
    this.solved = false;
    this.nodesVisited = 0;
    this.solution = new ArrayList<Node>();

    for (int i = 0; i < rows; i++) {
      nodes.add(new ArrayList<Node>());
      for (int j = 0; j < cols; j++) {
        nodes.get(i).add(new Node(i, j, false));
      }
    }

    List<Edge> edges = initEdges();
    List<Edge> minSpanningTree = kruskals(edges);
    setBooleans(minSpanningTree);
  }

  /**
   * Solve this maze where the start is the top left corner and
   * the finish is the bottom right corner.
   */
  public abstract void solve();

  /**
   * Find the path of nodes to the finish.
   * @param cameFromMap A map of nodes and which node they came from
   */
  void findSolution(Map<Node, Node> cameFromMap) {
    Node current = nodes.get(this.rows - 1).get(this.cols - 1);

    while (!current.equals(cameFromMap.get(current))) {
      this.solution.add(current);
      current = cameFromMap.get(current);
    }

    this.solution.add(current);
  }

  /**
   * Set the booleans of the nodes in this maze based on the
   * minimum spanning tree.
   * @param minSpanningTree The list of edges that represents a min spanning tree
   */
  private void setBooleans(List<Edge> minSpanningTree) {
    for (Edge e : minSpanningTree) {
      Node from = e.getFrom();
      Node to = e.getTo();

      int rowDiff = from.getRow() - to.getRow();

      if (rowDiff == 0) {
        from.setRight(true);
        to.setLeft(true);
      }
      else {
        from.setBottom(true);
        to.setTop(true);
      }
    }
  }

  /**
   * Initialize all the nodes into edges.
   * @return The list of edges that links all the nodes of the maze
   */
  private List<Edge> initEdges() {
    Random rand = new Random();
    List<Edge> result = new ArrayList<Edge>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Node fromNode = nodes.get(i).get(j);
        if (i != this.rows - 1) {
          Node toNode = nodes.get(i + 1).get(j);
          result.add(new Edge(fromNode, toNode, rand.nextInt(Integer.MAX_VALUE)));
        }

        if (j != this.cols - 1) {
          Node toNode = nodes.get(i).get(j + 1);
          result.add(new Edge(fromNode, toNode, rand.nextInt(Integer.MAX_VALUE)));
        }
      }
    }

    Collections.sort(result);
    return result;
  }

  /**
   * Kruskal's algorithm to calculate the min spanning tree for this maze.
   * @param edges The edges of the nodes in this maze
   * @return The min spanning tree of the maze
   */
  private List<Edge> kruskals(List<Edge> edges) {
    List<Edge> result = new ArrayList<Edge>();
    Map<Node, Node> map = new HashMap<Node, Node>();
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        Node n = nodes.get(i).get(j);
        map.put(n, n);
      }
    }

    while (edges.size() != 0) {
      Edge e = edges.remove(0);
      Node from = e.getFrom();
      Node to = e.getTo();
      Node fromFind = find(map, from);
      Node toFind = find(map, to);

      if (!(fromFind.equals(toFind))) {
        result.add(e);
        map.put(fromFind, toFind);
      }
    }

    return result;
  }

  /**
   * Finds the representative for the given node in the representative map.
   * @param map The representative map
   * @param n The node to find the representative for
   * @return The representing node
   */
  private Node find(Map<Node, Node> map, Node n) {
    while (!n.equals(map.get(n))) {
      n = map.get(n);
    }

    return n;
  }

  /**
   * Get the nodes of this maze.
   * @return The nodes of the maze
   */
  public List<List<Node>> getNodes() {
    return nodes;
  }

  /**
   * Set the nodes of this maze.
   * @param nodes The nodes to set
   */
  public void setNodes(List<List<Node>> nodes) {
    this.nodes = nodes;
  }

  /**
   * Get the number of columns in this maze.
   * @return The number of columns in the maze
   */
  public int getCols() {
    return cols;
  }

  /**
   * Set the number of columns in this maze.
   * @param cols The number of columns to set
   */
  public void setCols(int cols) {
    this.cols = cols;
  }

  /**
   * Get the number of rows in this maze.
   * @return The number of rows in the maze
   */
  public int getRows() {
    return rows;
  }

  /**
   * Set the number of rows in this maze.
   * @param rows The number of rows to set
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * Is this maze solved?
   * @return Whether or not this maze is solved
   */
  public boolean isSolved() {
    return solved;
  }

  /**
   * Set whether or not this maze is solved.
   * @param solved The value to set
   */
  public void setSolved(boolean solved) {
    this.solved = solved;
  }

  /**
   * Get the number of nodes that have been visited.
   * @return The number of nodes visited
   */
  public int getNodesVisited() {
    return nodesVisited;
  }

  /**
   * Set the number of nodes that have been visited.
   * @param nodesVisited The number of nodes visited to set
   */
  public void setNodesVisited(int nodesVisited) {
    this.nodesVisited = nodesVisited;
  }

  /**
   * Get the solution to this maze.
   * @return A list of nodes representing the solution to the maze
   */
  public List<Node> getSolution() {
    return solution;
  }

  /**
   * Set the solution to this maze.
   * @param solution The list of nodes to set as the solution to the maze
   */
  public void setSolution(List<Node> solution) {
    this.solution = solution;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(Main.WIDTH, Main.HEIGHT);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Node node = nodes.get(i).get(j);

        if (!node.isTop()) {
          int fromX = j * Main.SQUARE_LEN;
          int fromY = i * Main.SQUARE_LEN;
          g.drawLine(fromX, fromY, fromX + Main.SQUARE_LEN, fromY);
        }

        if (!node.isRight()) {
          int fromX = j * Main.SQUARE_LEN + Main.SQUARE_LEN;
          int fromY = i * Main.SQUARE_LEN;
          g.drawLine(fromX, fromY, fromX, fromY + Main.SQUARE_LEN);
        }

        if (!node.isBottom()) {
          int fromX = j * Main.SQUARE_LEN;
          int fromY = i * Main.SQUARE_LEN + Main.SQUARE_LEN;
          g.drawLine(fromX, fromY, fromX + Main.SQUARE_LEN, fromY);
        }

        if (!node.isLeft()) {
          int fromX = j * Main.SQUARE_LEN;
          int fromY = i * Main.SQUARE_LEN;
          g.drawLine(fromX, fromY, fromX, fromY + Main.SQUARE_LEN);
        }

        if (node.isFound()) {
          g.setColor(Color.BLUE);
          int x = j * Main.SQUARE_LEN + 1;
          int y = i * Main.SQUARE_LEN + 1;
          g.fillRect(x, y, Main.SQUARE_LEN - 2, Main.SQUARE_LEN - 2);
          g.setColor(Color.BLACK);
        }
      }
    }

    if (solved) {
      g.setColor(Color.YELLOW);
      for (Node n : this.solution) {
        int x = n.getCol() * Main.SQUARE_LEN + 1;
        int y = n.getRow() * Main.SQUARE_LEN + 1;
        g.fillRect(x, y, Main.SQUARE_LEN - 2, Main.SQUARE_LEN - 2);
      }
      g.setColor(Color.BLACK);
    }
  }
}
