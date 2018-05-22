package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nodes.EuclideanAStarNode;
import nodes.Node;

// Represents a maze solver using the A* search method with
// Euclidean distance heuristic
public class AStarEuclideanSolver extends Maze {
  private List<List<EuclideanAStarNode>> euclideanNodes;

  public AStarEuclideanSolver(Maze maze) {
    super(maze);
    euclideanNodes = new ArrayList<List<EuclideanAStarNode>>();
    for (int i = 0; i < rows; i++) {
      euclideanNodes.add(new ArrayList<EuclideanAStarNode>());
      for (int j = 0; j < cols; j++) {
        Node n = nodes.get(i).get(j);
        n.setFound(false);
        euclideanNodes.get(i).add(new EuclideanAStarNode(n.getRow(), n.getCol(), n.isTop(),
                n.isRight(), n.isBottom(), n.isLeft()));
      }
    }
  }

  public AStarEuclideanSolver(int cols, int rows) {
    super(cols, rows);
  }

  @Override
  public void solve() {
    Map<Node, Node> cameFromMap = new HashMap<Node, Node>();
    PriorityQueue<EuclideanAStarNode> queue = new PriorityQueue<EuclideanAStarNode>();

    EuclideanAStarNode first = euclideanNodes.get(0).get(0);
    Node firstNode = nodes.get(0).get(0);
    queue.add(first);
    cameFromMap.put(firstNode, firstNode);

    while (queue.size() > 0) {
      EuclideanAStarNode n = queue.poll();
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
          EuclideanAStarNode toAdd = euclideanNodes.get(neighbor.getRow()).get(neighbor.getCol());
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
