package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nodes.EuclideanGreedyNode;
import nodes.Node;

// Maze solver using Greedy Best First Search using
// Euclidean distance heuristic.
public class GreedyEuclideanSolver extends Maze {
  List<List<EuclideanGreedyNode>> euclideanNodes;

  public GreedyEuclideanSolver(Maze maze) {
    super(maze);
    euclideanNodes = new ArrayList<List<EuclideanGreedyNode>>();
    for (int i = 0; i < rows; i++) {
      euclideanNodes.add(new ArrayList<EuclideanGreedyNode>());
      for (int j = 0; j < cols; j++) {
        Node n = nodes.get(i).get(j);
        n.setFound(false);
        euclideanNodes.get(i).add(new EuclideanGreedyNode(n.getRow(), n.getCol(), n.isTop(),
                n.isRight(), n.isBottom(), n.isLeft()));
      }
    }
  }

  public GreedyEuclideanSolver(int cols, int rows) {
    super(cols, rows);
  }

  @Override
  public void solve() {
    Map<Node, Node> cameFromMap = new HashMap<Node, Node>();
    PriorityQueue<EuclideanGreedyNode> queue = new PriorityQueue<EuclideanGreedyNode>();

    EuclideanGreedyNode first = euclideanNodes.get(0).get(0);
    Node firstNode = nodes.get(0).get(0);
    queue.add(first);
    cameFromMap.put(firstNode, firstNode);

    while (queue.size() > 0) {
      EuclideanGreedyNode n = queue.poll();
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
          EuclideanGreedyNode toAdd = euclideanNodes.get(neighbor.getRow()).get(neighbor.getCol());
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
