import becker.robots.*;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

/***
 * based on Thomas' maze bot that summoned 400 transparent robots to scan the maze and take the shortest route
 * only uses 200 transparent robots for 10x10 tho... and fancy graphs
 */
public class MazeBot400 extends RobotSU {
    private Point thingPoint;

    public MazeBot400(City city, int x, int y) {
        super(city, x, y, Direction.NORTH);
    }

    // credit to Thomas for the innovation
    public Graph Scan400(final int start_x, final int start_y, final int avenues, final int streets) {
        var northAndWest = new Direction[]{ Direction.NORTH, Direction.WEST };
        var graph = new Graph();
        for(int x = start_x; x < avenues; ++x) {
        for(int y = start_y; y < streets; ++y) {
            var p = new Point(x, y);
            var node = graph.Add(x, y);
            for(var direction : northAndWest) {
                var scannyBoi = new Robot(getCity(), y, x, direction);
                if(scannyBoi.canPickThing()) {
                    thingPoint = new Point(x, y);
                }
                scannyBoi.setTransparency(1.0);
                if(scannyBoi.frontIsClear()) {
                    var connectedPoint = p.Get(direction);
                    var connectedNode = graph.Find(connectedPoint.x, connectedPoint.y);
                    if(connectedNode.isPresent()) {
                        node.connect(connectedNode.get());
                    }
                }
            }
        } }
        return graph;
    }

    public void Solve(Point end, Graph graph) throws Exception {
        Point start = CurrentPosition();
        var startNode = graph.Find(start.x, start.y);
        var endNode = graph.Find(end.x, end.y);
        if(startNode.isEmpty() || endNode.isEmpty()) {
            throw new Exception("graph doesn't contain start and end points!");
        }
        Solve(startNode.get(), endNode.get(), graph);
    }

    public void Solve(Graph.Node start, Graph.Node end, Graph graph) {
        var visited = new HashSet<Graph.Node>();
        Queue<Graph.Node> toVisit = new LinkedList<Graph.Node>();

        toVisit.add(end);

        int value = 0;
        while(!toVisit.isEmpty()) {
            var visiting = toVisit.remove();
            visiting.value = ++value;
            for(var node : visiting.connections) {
                if(!visited.contains(node)) {
                    toVisit.add(node);
                }
            }
            visited.add(visiting);
        }

        var walk = start;
        while(walk != end) {
            var smallest = value;
            for(var node : walk.connections) {
                if(node.value <= smallest) {
                    walk = node;
                    smallest = node.value;
                }
            }
            Goto(walk.x, walk.y);
        }
    }

    public void Solve(int avenues, int streets) {
        var home = CurrentPosition();
        try {
            var graph = Scan400(0, 0, avenues, streets);
            Solve(thingPoint, graph);
            while(canPickThing()) {
                pickThing();
            }
            Solve(home, graph);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int streets = 10, avenues = 10;
        var city = new MazeCity(streets, avenues);
        var rand = new Random();
        new Thing(city, rand.nextInt(streets), rand.nextInt(avenues));
        var bot = new MazeBot400(city, 5, 5);
        bot.Solve(avenues, streets);
    }
}