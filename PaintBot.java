import becker.robots.*;
import java.util.HashSet;
import java.util.Random;
import java.io.File;
import java.util.Stack;

public class PaintBot extends RobotSU {

    public PaintBot(City city, int x, int y) {
        super(city, x, y, Direction.NORTH, 100);
    }

    int DistanceTo(Point point) {
        Point curr = CurrentPosition();
        return Math.abs(point.getX() - curr.getX()) + Math.abs(point.getY() - curr.getY());
    }

    Point Closest(HashSet<Point> set) {
        int closestDist = Integer.MAX_VALUE;
        Point closest = null;
        for(var point : set) {
            int dist = DistanceTo(point);
            if(dist <= closestDist) {
                closestDist = dist;
                closest = point;
            }
        }
        return closest;
    }

    // assumes start within polygon
    public void Paint() {
        HashSet<Point> visited = new HashSet<Point>();
        HashSet<Point> toVisit = new HashSet<Point>();
        toVisit.add(CurrentPosition());

        while(!toVisit.isEmpty() && countThingsInBackpack() > 0) {
            Point p = Closest(toVisit);
            toVisit.remove(p);
            if(visited.contains(p)) continue;
            Goto(p);
            visited.add(p);
            if(canPickThing()) continue;
            putThing();
            for(Direction d : Utils.directions) {
                toVisit.add(p.Get(d));
            }
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Expected Usage PaintBot <worlds folder>");
            System.exit(0);
        }
        City city = new City(Utils.RandomFileFromList(args[0]));
        new PaintBot(city, 6, 2).Paint();
    }
}
