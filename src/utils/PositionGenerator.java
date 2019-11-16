package utils;

import java.util.Random;

public abstract class PositionGenerator {

    protected final int width;
    protected final int height;

    public PositionGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Position getPosition(){
        return new Position(0,0);
    }

    public Position getRandomPosition(){
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        return new Position(x, y);
    }
}
