package behaviours.animals.move;

import sajas.core.Agent;
import utils.Position;
/*
public class MoveToGoal extends Move {

    private Position goalPosition;

    public MoveToGoal(Agent a, Position goalPosition) {
        super(a, true);
        this.goalPosition = goalPosition;
        this.firstMove();
    }

    @Override
    protected void getMove() {

        double lowerDistance = Double.MAX_VALUE;
        int[] nextMove = null;

        if(remainingMoves.isEmpty())  // all positions are taken
            return;
        
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
}*/