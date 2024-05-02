import becker.robots.*;
import java.util.Random;


public class MicroMouse extends MazeBot400 {


    public MicroMouse(City city, int x, int y) {
        super(city, x, y);
    }

    public void DepthFirstWander(boolean checkBehind, Graph graph) {

        var home = CurrentPosition();

        var facing = getDirection();
        var directions =
            checkBehind
            ? new Direction[]{ facing, facing.left(), facing.right(), facing.opposite() }
            : new Direction[]{ facing, facing.left(), facing.right() };

        for(var direction : directions) {
            TurnTo(direction);
            if(frontIsClear()) {
                var prevNode = graph.Find(GetX(), GetY());
                move();
                var node = graph.Add(GetX(), GetY());
                if(prevNode.isPresent()) {
                    node.connect(prevNode.get());
                }
                if(canPickThing()) {
                    thingPoint = CurrentPosition();
                }
                DepthFirstWander(false, graph);
                Goto(home);
            }
        }
    }

    public void Solve() {
        var graph = new Graph();
        graph.Add(GetX(), GetY());

        DepthFirstWander(true, graph);

        try {
            Thread.sleep(500); // pause at home for a moment to see the transition

            Solve(thingPoint, graph);
            pickThing();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int streets = 10, avenues = 10;
        var city = new MazeCity(streets, avenues);
        var rand = new Random();
        new Thing(city, rand.nextInt(streets), rand.nextInt(avenues));
        var bot = new MicroMouse(city, 5, 5);
        bot.Solve();
    }
}