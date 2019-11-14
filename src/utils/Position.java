package utils;

import java.io.Serializable;

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
        return Math.sqrt(Math.pow(p2.x - this.x, 2) + Math.pow(p2.y - this.y, 2));
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