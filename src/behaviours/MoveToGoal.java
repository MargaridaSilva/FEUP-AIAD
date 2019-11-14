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
    protected Position getMove() {

        double lowerDistance = Double.MAX_VALUE;
        int[] nextMove = null;

        for (Integer moveIndex : remainingMoves) {
            int[] move = MOVES[moveIndex];
            Position possiblePosition = this.getNextPosition(move);
            double distToGoal = goalPosition.getDist(possiblePosition);
            if(distToGoal < lowerDistance) {
                nextMove = move;
                lowerDistance = distToGoal;
            }
        }
        
        return getNextPosition(nextMove);

    }

    
}