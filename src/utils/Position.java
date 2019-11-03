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

    @Override
    public boolean equals(Object obj) {
       
        if (!(obj instanceof Position)) 
            return false;

        Position p2 = (Position) obj;

        return (p2.x == this.x && p2.y == this.y);
    }

}