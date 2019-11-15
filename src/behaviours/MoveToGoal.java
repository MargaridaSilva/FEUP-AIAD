package behaviours;

import sajas.core.Agent;
import utils.Position;

public class MoveToGoal extends Move {

    private Position goalPosition;

    public MoveToGoal(Agent a, Position goalPosition) {
        super(a);
        this.goalPosition = goalPosition;
    }

    @Override
    protected void getMove() {

        double lowerDistance = Double.MAX_VALUE;
        int[] nextMove = null;

        for (Integer moveIndex : remainingMoves) {
            int[] move = MOVES[moveIndex];
            getNextPosition(move);
            double distToGoal = goalPosition.getDist(nextPosition);
            if(distToGoal < lowerDistance) {
                nextMove = move;
                lowerDistance = distToGoal;
            }
        }
        
        getNextPosition(nextMove);
    }

    
}