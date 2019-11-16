package utils;

import java.util.Random;

public abstract class PositionGenerator {

    protected final int BOARD_DIM;

    public PositionGenerator(int BOARD_DIM) {
        this.BOARD_DIM = BOARD_DIM;
    }

    public Position getPosition(){
        return new Position(0,0);
    }

    public Position getRandomPosition(){
        Random random = new Random();
        int x = random.nextInt(BOARD_DIM);
        int y = random.nextInt(BOARD_DIM);
        return new Position(x, y);
    }
}
