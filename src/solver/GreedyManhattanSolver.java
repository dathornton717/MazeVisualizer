package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nodes.ManhattanGreedyNode;
import nodes.Node;

// Maze solver using Greedy Best First Search using
// Manhattan distance heuristic.
public class GreedyManhattanSolver extends Maze {
  List<List<ManhattanGreedyNode>> manhattanNodes;

  public GreedyManhattanSolver(Maze maze) {
    super(maze);
    manhattanNodes = new ArrayList<List<ManhattanGreedyNode>>();
    for (int i = 0; i < rows; i++) {
      manhattanNodes.add(new ArrayList<ManhattanGreedyNode>());
      for (int j = 0; j < cols; j++) {
        Node n = nodes.get(i).get(j);
        n.setFound(false);
        manhattanNodes.get(i).add(new ManhattanGreedyNode(n.getRow(), n.getCol(), n.isTop(),
                        n.isRight(), n.isBottom(), n.isLeft()));
      }
    }
  }

  public GreedyManhattanSolver(int cols, int rows) {
    super(cols, rows);
  }

  @Override
  public void solve() {
    Map<Node, Node> cameFromMap = new HashMap<Node, Node>();
    PriorityQueue<ManhattanGreedyNode> queue = new PriorityQueue<ManhattanGreedyNode>();

    ManhattanGreedyNode first = manhattanNodes.get(0).get(0);
    Node firstNode = nodes.get(0).get(0);
    queue.add(first);
    cameFromMap.put(firstNode, firstNode);

    while (queue.size() > 0) {
      ManhattanGreedyNode n = queue.poll();
      nodes.get(n.getRow()).get(n.getCol()).setFound(true);
      this.nodesVisited++;
      this.repaint();

      if (n.getRow() == this.rows - 1 && n.getCol() == this.cols - 1) {
        this.solved = true;
        findSolution(cameFromMap);
        this.repaint();
        return;
      }

      // For each of the neighbors, add to priority queue
      List<Node> neighbors = n.getNeighbors(nodes);
      for (Node neighbor : neighbors) {
        if (!neighbor.isFound()) {
          ManhattanGreedyNode toAdd = manhattanNodes.get(neighbor.getRow()).get(neighbor.getCol());
          toAdd.setDistance(n.getDistance() + 1);
          queue.add(toAdd);
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
