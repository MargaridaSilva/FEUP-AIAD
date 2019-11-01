package utils;

import java.util.HashSet;
import java.util.Random;

public final class RandomPositionGenerator extends PositionGenerator {

    private Random random;
    private HashSet<Integer> randomNumbers;

    public RandomPositionGenerator(int BOARD_DIM) {
        super(BOARD_DIM);
        this.random = new Random(System.currentTimeMillis());
        this.randomNumbers = new HashSet<>();
    }

    public Position getPosition() {
        int x, y, randomNumber = 0;
        
        do {
            randomNumber = random.nextInt(BOARD_DIM * BOARD_DIM);
        } while(randomNumbers.contains(randomNumber));
        randomNumbers.add(randomNumber);

        x = randomNumber / BOARD_DIM;
        y = randomNumber % BOARD_DIM;

        return new Position(x,y);
    }
}