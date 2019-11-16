package utils;

import java.util.HashSet;
import java.util.Random;

public final class RandomPositionGenerator extends PositionGenerator {

    private Random random;
    private HashSet<Integer> randomNumbers;

    public RandomPositionGenerator(int width, int height) {
        super(width, height);
        this.random = new Random();
        this.randomNumbers = new HashSet<>();
    }

    public Position getPosition() {
        int x, y, randomNumber = 0;
        
        do {
            randomNumber = random.nextInt(width * height);
        } while(randomNumbers.contains(randomNumber));
        randomNumbers.add(randomNumber);

        x = randomNumber / width;
        y = randomNumber % height;

        return new Position(x,y);
    }
}