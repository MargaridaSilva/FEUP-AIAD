package utils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public final class Position implements Serializable {

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position clone() {
        return new Position(this.x, this.y);
    }

    public double getDist(Position p2) {
        return (p2.x - this.x) + (p2.y - this.y);
    }

    public Position getClosestPosition(HashSet<Position> possibleTargets) {
        Position closest = new Position(1000,1000);
        for (Position pos: possibleTargets){
            if (this.getDist(closest) > this.getDist(pos))
                closest = pos;
        }
        return closest;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Position))
            return false;

        Position p2 = (Position) obj;

        return (p2.x == this.x && p2.y == this.y);
    }

    @Override
    public String toString() {
        
        return "(" + x + ", " + y + ")"; 
    }

}