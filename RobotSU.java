import becker.robots.*;
import java.util.HashSet;
import java.util.Random;
import java.io.File;

public class RobotSU extends RobotSE {
    public RobotSU(City city, int x, int y, Direction direction, int things) { super(city, y, x, direction, things); }
    public RobotSU(City city, int x, int y) { super(city, y, x, Direction.NORTH); }
    public RobotSU(City city, int x, int y, int things) { super(city, y, x, Direction.NORTH, things); }
    public RobotSU(City city, int x, int y, Direction direction) { super(city, y, x, direction); }

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

    public void Move(Point p) {
        Move(p.x, p.y);
    }

    public void Goto(int x, int y) {
        Move(x-GetX(), y-GetY());
    }

    public void Goto(Point p) {
        Goto(p.x, p.y);
    }
}