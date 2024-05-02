import java.util.HashSet;
import java.util.Optional;
import java.util.Iterator;

public class Graph {

    public Node Add(int x, int y) {
        return new Node(x, y);
    }

    public Optional<Node> Find(int x, int y) {
        for(var node : allNodes) {
            if(node.x == x && node.y == y) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    private HashSet<Node> allNodes = new HashSet<>();

    public Iterator<Node> IterateNodes() {
        return allNodes.iterator();
    }

    public class Node {
        public int x, y;
        public int value = 0;
        public HashSet<Node> connections = new HashSet<>();

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            allNodes.add(this);
        }

        public void connect(Node other) {
            connections.add(other);
            other.connections.add(this);
        }
    }
}