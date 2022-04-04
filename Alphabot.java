import becker.robots.*;
import java.util.List;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;

public class Alphabot extends RobotSE {
    public static class Point {
        int x, y;
    }

    public static class Line {
        Point start, end;

        public Line(int sx, int sy, int ex, int ey) {
            start = new Point();
            start.x = sx;
            start.y = sy;
            end = new Point();
            end.x = ex;
            end.y = ey;
        }
    }

    public static final HashMap<Character, Line[]> mappings = new HashMap<>() {{
        put('A', new Line[]{new Line(0, 6, 0, 3), new Line(0, 2, 2, 0), new Line(3, 1, 4, 2), new Line(4, 3, 4, 6), new Line(3, 3, 1, 3)});
        put('B', new Line[]{new Line(0, 0, 0, 6), new Line(1, 6, 3, 6), new Line(4, 5, 4, 4), new Line(3, 3, 1, 3), new Line(4, 2, 4, 1), new Line(3, 0, 1, 0)});
        put('C', new Line[]{new Line(4, 0, 3, 0), new Line(2, 0, 0, 2), new Line(0, 3, 0, 4), new Line(1, 5, 2, 6), new Line(3, 6, 4, 6)});
        put('D', new Line[]{new Line(0, 6, 0, 0), new Line(1, 0, 1, 0), new Line(2, 0, 4, 2), new Line(4, 3, 4, 3), new Line(4, 4, 2, 6), new Line(1, 6, 1, 6)});
        put('E', new Line[]{new Line(0, 0, 0, 6), new Line(1, 6, 4, 6), new Line(1, 3, 3, 3), new Line(1, 0, 4, 0)});
        put('F', new Line[]{new Line(0, 6, 0, 0), new Line(1, 0, 4, 0), new Line(1, 3, 3, 3)});
        put('G', new Line[]{new Line(4, 1, 3, 0), new Line(2, 0, 0, 2), new Line(0, 3, 0, 4), new Line(1, 5, 2, 6), new Line(3, 6, 4, 6), new Line(4, 5, 4, 4), new Line(3, 4, 3, 4)});
        put('H', new Line[]{new Line(0, 0, 0, 6), new Line(1, 3, 3, 3), new Line(4, 0, 4, 6)});
        put('I', new Line[]{new Line(0, 6, 4, 6), new Line(2, 5, 2, 1), new Line(0, 0, 4, 0)});
        put('J', new Line[]{new Line(0, 5, 1, 6), new Line(2, 5, 2, 1), new Line(0, 0, 4, 0)});
        put('K', new Line[]{new Line(0, 6, 0, 0), new Line(1, 3, 4, 0), new Line(2, 4, 4, 6)});
        put('L', new Line[]{new Line(0, 0, 0, 6), new Line(1, 6, 4, 6)});
        put('M', new Line[]{new Line(0, 6, 0, 1), new Line(0, 0, 2, 2), new Line(3, 1, 4, 0), new Line(4, 1, 4, 6)});
        put('N', new Line[]{new Line(0, 6, 0, 1), new Line(0, 0, 3, 3), new Line(4, 6, 4, 0)});
        put('O', new Line[]{new Line(1, 0, 3, 0), new Line(4, 1, 4, 5), new Line(3, 6, 1, 6), new Line(0, 5, 0, 1)});
        put('P', new Line[]{new Line(0, 6, 0, 0), new Line(1, 0, 3, 0), new Line(4, 1, 4, 2), new Line(3, 3, 1, 3)});
        put('Q', new Line[]{new Line(1, 0, 3, 0), new Line(4, 1, 4, 4), new Line(2, 4, 4, 6), new Line(2, 6, 1, 6), new Line(0, 5, 0, 1)});
        put('R', new Line[]{new Line(0, 6, 0, 0), new Line(1, 0, 3, 0), new Line(4, 1, 4, 2), new Line(3, 3, 1, 3), new Line(2, 4, 4, 6)});
        put('S', new Line[]{new Line(4, 1, 3, 0), new Line(2, 0, 1, 0), new Line(0, 1, 4, 5), new Line(3, 6, 2, 6), new Line(1, 6, 0, 5)});
        put('T', new Line[]{new Line(2, 6, 2, 1), new Line(0, 0, 4, 0)});
        put('U', new Line[]{new Line(0, 0, 0, 5), new Line(1, 6, 3, 6), new Line(4, 5, 4, 0)});
        put('V', new Line[]{new Line(0, 0, 0, 3), new Line(0, 4, 2, 6), new Line(3, 5, 4, 4), new Line(4, 3, 4, 0)});
        put('W', new Line[]{new Line(0, 0, 0, 5), new Line(0, 6, 2, 4), new Line(3, 5, 4, 6), new Line(4, 5, 4, 0)});
        put('X', new Line[]{new Line(0, 0, 0, 1), new Line(1, 2, 3, 4), new Line(4, 5, 4, 6), new Line(4, 0, 4, 1), new Line(3, 2, 3, 2), new Line(1, 4, 1, 4), new Line(0, 5, 0, 6)});
        put('Y', new Line[]{new Line(2, 6, 2, 3), new Line(2, 2, 0, 0), new Line(2, 2, 4, 0)});
        put('Z', new Line[]{new Line(0, 0, 4, 0), new Line(4, 1, 0, 5), new Line(0, 6, 4, 6)});
    }};

    public Alphabot(City city, int x, int y) {
        super(city, y, x, Direction.NORTH);
    }

    public int GetX() { return getAvenue(); }
    public int GetY() { return getStreet(); }

    public void TurnTo(Direction target) {
        if(getDirection() == target)
            return;
        if(getDirection().right() == target)
            turnRight();
        else if(getDirection().left() == target)
            turnLeft();
        else
            turnAround();
    }

    public void Move(Direction direction, int spaces) {
        if(spaces <= 0) return;
        TurnTo(direction);
        for(; spaces > 0; --spaces) {
            move();
        }
    }

    public void Move(Direction direction) {
        Move(direction, 1);
    }

    public void Move(int x, int y) {
        Move(x > 0 ? Direction.EAST : Direction.WEST, Math.abs(x));
        Move(y > 0 ? Direction.SOUTH : Direction.NORTH, Math.abs(y));
    }

    public void Goto(int x, int y) {
        Move(x-GetX(), y-GetY());
    }

    public void LeaveBlackThing() {
        new Thing(getCity(), GetY(), GetX()).setColor(new Color(0, 0, 0));
    }

    public void DrawLine(Line line, int offX, int offY) {
        Goto(line.start.x+offX, line.start.y+offY);
        int dx = Math.abs(line.end.x - line.start.x) + 1;
        int dy = Math.abs(line.end.y - line.start.y) + 1;

        int steps = Math.max(1, Math.min(dx, dy));
        int step_size = Math.max(dx, dy) / steps;
        Direction smol_step = dx < dy ? (line.end.x > line.start.x ? Direction.EAST : Direction.WEST) : (line.end.y > line.start.y ? Direction.SOUTH: Direction.NORTH);
        Direction big_step  = dx < dy ? (line.end.y > line.start.y ? Direction.SOUTH: Direction.NORTH) : (line.end.x > line.start.x ? Direction.EAST : Direction.WEST);

        for(; steps > 0; --steps) {
            for(int i=0; i<step_size; ++i) {
                LeaveBlackThing();
                if(GetX() == line.end.x+offX && GetY() == line.end.y+offY) return;
                Move(big_step);
            }
            Move(smol_step);
        }
    }

    public boolean DrawLetter(char letter, int startX, int startY) {
        Line[] lines = mappings.get(Character.toUpperCase(letter));
        if(lines == null) return false; // mappings doesnt contain letter

        for(Line line : lines) {
            DrawLine(line, startX, startY);
        }

        return true;
    }

    public boolean DrawLetter(char letter) {
        return DrawLetter(letter, GetX(), GetY());
    }

    public int DrawString(String word, int x, int y) {
        for(char c : word.toCharArray()) {
            DrawLetter(c, x, y);
            x += 6;
        }
        return x;
    }

    public static void main(String[] args) {
        City city = new City();
        city.showThingCounts(true);
        Alphabot chad = new Alphabot(city, 0, 0);
        int x=2;
        int y=2;

        for(String arg : args) {
            if(arg.equals("+")) {
                x = 2;
                y += 8;
            } else {
                x = chad.DrawString(arg, x, y);
                x += 5;
            }
        }
    }
}