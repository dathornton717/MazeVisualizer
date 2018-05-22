package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import main.Main;

// Represents a node in a maze
public class Node {
  int row;
  int col;
  boolean found;
  boolean top;
  boolean right;
  boolean bottom;
  boolean left;
  int distance;

  public Node(int row, int col, boolean found) {
    this.row = row;
    this.col = col;
    this.found = found;
    this.top = false;
    this.right = false;
    this.bottom = false;
    this.left = false;
    this.distance = 0;
  }

  public Node(int row, int col, boolean top, boolean right, boolean bottom, boolean left) {
    this.row = row;
    this.col = col;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
    this.found = false;
    this.distance = 0;
  }

  /**
   * Get the row of this node.
   * @return The row of the node
   */
  public int getRow() {
    return row;
  }

  /**
   * Set the row of this node.
   * @param row The row to set
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Get the column of this node.
   * @return The column of the node
   */
  public int getCol() {
    return col;
  }

  /**
   * Set the column of this node.
   * @param col The column to set
   */
  public void setCol(int col) {
    this.col = col;
  }

  /**
   * Has this node been found yet?
   * @return Whether or not the node has been found
   */
  public boolean isFound() {
    return found;
  }

  /**
   * Set if this node has been found.
   * @param found The found value to set
   */
  public void setFound(boolean found) {
    this.found = found;
  }

  /**
   * Does this node have a neighbor to the top?
   * @return Whether or not the node has a neighbor to the top
   */
  public boolean isTop() {
    return top;
  }

  /**
   * Set if this node has a neighbor to the top.
   * @param top The top neighbor value
   */
  public void setTop(boolean top) {
    this.top = top;
  }

  /**
   * Does this node have a neighbor to the right?
   * @return Whether or not the node has a neighbor to the right
   */
  public boolean isRight() {
    return right;
  }

  /**
   * Set if this node has a neighbor to the right.
   * @param right The right neighbor value
   */
  public void setRight(boolean right) {
    this.right = right;
  }

  /**
   * Does this node have a neighbor to the bottom?
   * @return Whether or not the node has a neighbor to the bottom
   */
  public boolean isBottom() {
    return bottom;
  }

  /**
   * Set if this node has a neighbor to the bottom.
   * @param bottom The bottom neighbor value
   */
  public void setBottom(boolean bottom) {
    this.bottom = bottom;
  }

  /**
   * Does this node have a neighbor to the left?
   * @return Whether or not the node has a neighbor to the left
   */
  public boolean isLeft() {
    return left;
  }

  /**
   * Set if this node has a neighbor to the left.
   * @param left The left neighbor value
   */
  public void setLeft(boolean left) {
    this.left = left;
  }

  /**
   * Get the distance this node is from the starting node.
   * @return The distance of this node
   */
  public int getDistance() {
    return distance;
  }

  /**
   * Set the distance this node is from the starting node.
   * @param distance The distance to set
   */
  public void setDistance(int distance) {
    this.distance = distance;
  }

  /**
   * Get the neighbors of this node.
   * @param nodes The complete grid of nodes.\
   * @return A list of neighbors for this node
   */
  public List<Node> getNeighbors(List<List<Node>> nodes) {
    List<Node> result = new ArrayList<Node>();
    if (this.top) {
      result.add(nodes.get(row - 1).get(col));
    }

    if (this.left) {
      result.add(nodes.get(row).get(col - 1));
    }

    if (this.bottom) {
      result.add(nodes.get(row + 1).get(col));
    }

    if (this.right) {
      result.add(nodes.get(row).get(col + 1));
    }

    //Collections.shuffle(result);
    return result;
  }

  /**
   * Determine the Manhattan distance this node is from the end node.
   * @return The x and y distance this node is from the end node
   */
  int manhattanDistance() {
    return Math.abs(row - Main.HEIGHT / Main.SQUARE_LEN - 1)
            + Math.abs(col - Main.WIDTH / Main.SQUARE_LEN - 1);
  }

  /**
   * Calculate the Euclidean distance this node is from the end node.
   * @return How far away this node is from the end node
   */
  double euclideanDistance() {
    int x = row - Main.HEIGHT / Main.SQUARE_LEN - 1;
    int y = col - Main.WIDTH / Main.SQUARE_LEN - 1;
    return Math.sqrt(x * x + y * y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Node)) {
      return false;
    }

    Node that = (Node) o;

    return row == that.getRow() && col == that.getCol();
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
