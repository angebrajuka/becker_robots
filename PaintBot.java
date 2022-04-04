import becker.robots.*;
import java.util.HashSet;
import java.util.Random;
import java.io.File;
import java.util.Stack;

public class PaintBot extends RobotSU {

    public PaintBot(City city, int x, int y) {
        super(city, x, y);
    }

    // assumes start within polygon
    void Paint() {
        HashSet<Point> toPaint = new HashSet<Point>();
        int startX = GetX();
        int startY = GetY();

        Stack<Point> toVisit = new Stack<Point>();
        toVisit.push(new Point(startX, startY));

        while(!toVisit.empty()) {
            Point p = toVisit.pop();
            Goto(p);
            if(canPickThing() || toPaint.contains(p)) continue;
            toPaint.add(p);
            for(Direction d : Utils.directions) {
                toVisit.push(p.Get(d));
            }
        }

        for(Point p : toPaint) {
            Goto(p);
            new Thing(getCity(), GetY(), GetX());
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
