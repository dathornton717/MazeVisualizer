package nodes;

import java.util.Objects;

// Represents a weighted edge between two nodes.
public class Edge implements Comparable<Edge> {
  private Node to;
  private Node from;
  private int value;

  public Edge(Node from, Node to, int value) {
    this.from = from;
    this.to = to;
    this.value = value;
  }

  /**
   * Set the node this edge is going to.
   * @param to The node to set
   */
  public void setTo(Node to) {
    this.to = to;
  }

  /**
   * Set the node this edge is going from.
   * @param from The node to set
   */
  public void setFrom(Node from) {
    this.from = from;
  }

  /**
   * Set the value of the edge.
   * @param value The value to set
   */
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * Get the node this edge is going to.
   * @return The destination node of the edge
   */
  public Node getTo() {
    return to;
  }

  /**
   * Get the node this edge is coming from.
   * @return The source node of the edge
   */
  public Node getFrom() {
    return from;
  }

  /**
   * Get the value of this edge.
   * @return The value of the edge
   */
  public int getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Edge)) {
      return false;
    }

    Edge that = (Edge) o;

    return to.equals(that.getTo()) && from.equals(that.getFrom()) && value == that.getValue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(to, from , value);
  }

  @Override
  public int compareTo(Edge o) {
    return this.value - o.getValue();
  }
}
