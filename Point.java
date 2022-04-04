import java.util.HashMap;
import becker.robots.*;

public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public Point Add(int x, int y) {
        return new Point(this.x+x, this.y+y);
    }

    public Point Add(Point p) {
        return Add(p.x, p.y);
    }

    public Point Add(Direction d) {
        return Add(directions.get(d));
    }

    public static final HashMap<Direction, Point> directions = new HashMap<>() {{
        put(Direction.NORTH, new Point(0, -1));
        put(Direction.EAST , new Point(1, 0));
        put(Direction.SOUTH, new Point(0, 1));
        put(Direction.WEST , new Point(-1, 0));
    }};

    public Point Get(Direction direction) {
        return this.Add(direction);
    }
}