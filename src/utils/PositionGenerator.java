package utils;

import java.util.HashSet;
import java.util.Random;

public final class PositionGenerator {

    private Random random;
    private HashSet<Integer> randomNumbers;
    private final int BOARD_DIM;

    public PositionGenerator(int BOARD_DIM) {
        this.random = new Random(System.currentTimeMillis());
        this.randomNumbers = new HashSet<>();
        this.BOARD_DIM = BOARD_DIM;
    }
    
    public int[] getPosition() {
        int x, y, randomNumber = 0;
        
        do {
            randomNumber = random.nextInt(BOARD_DIM * BOARD_DIM);
        } while(randomNumbers.contains(randomNumber));
        randomNumbers.add(randomNumber);

        x = randomNumber / BOARD_DIM;
        y = randomNumber % BOARD_DIM;
        
        return new int[]{x,y};
    }
}