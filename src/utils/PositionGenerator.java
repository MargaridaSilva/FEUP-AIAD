package utils;

import java.util.HashSet;
import java.util.Random;

public abstract class PositionGenerator {
    protected final int BOARD_DIM;

    public PositionGenerator(int BOARD_DIM) {
        this.BOARD_DIM = BOARD_DIM;
    }

    public int[] getPosition(){
        return new int[]{0,0};
    }
}
