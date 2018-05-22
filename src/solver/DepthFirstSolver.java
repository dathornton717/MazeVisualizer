package solver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import nodes.Node;

// Maze solver using Depth First Search
public class DepthFirstSolver extends Maze {
  public DepthFirstSolver(Maze maze) {
    super(maze);
  }

  public DepthFirstSolver(int cols, int rows) {
    super(cols, rows);
  }

  @Override
  public void solve() {
    Stack<Node> stack = new Stack<Node>();
    Map<Node, Node> cameFromMap = new HashMap<Node, Node>();
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        Node n = nodes.get(i).get(j);
        cameFromMap.put(n, n);
      }
    }

    Node n = this.nodes.get(0).get(0);
    n.setFound(true);
    stack.push(n);
    this.repaint();

    while (stack.size() != 0) {
      n = stack.pop();
      n.setFound(true);
      this.nodesVisited++;

      if (n.getRow() == this.rows - 1 && n.getCol() == this.cols - 1) {
        this.solved = true;
        findSolution(cameFromMap);
        this.repaint();
        return;
      }

      this.repaint();
      List<Node> neighbors = n.getNeighbors(this.nodes);

      for (Node neighbor : neighbors) {
        if (!neighbor.isFound()) {
          stack.push(neighbor);
          cameFromMap.put(neighbor, n);
        }
      }

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
