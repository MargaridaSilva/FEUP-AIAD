package utils;

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
}
