package utils;

public abstract class PositionGenerator {

    protected final int BOARD_DIM;

    public PositionGenerator(int BOARD_DIM) {
        this.BOARD_DIM = BOARD_DIM;
    }

    public Position getPosition(){
        return new Position(0,0);
    }
}
