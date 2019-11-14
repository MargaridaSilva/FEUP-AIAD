package behaviours;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import agents.AnimalAgent;
import sajas.core.Agent;
import sajas.proto.IteratedAchieveREInitiator;
import utils.Position;

public class Move extends IteratedAchieveREInitiator {

    protected static final int[][] MOVES = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    protected Position goal;
    protected ArrayList<Integer> remainingMoves;

    public Move(Agent a) {
        super(a, null);
        this.remainingMoves = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
    }

    @Override
    protected void handleAllResultNotifications(Vector resultNotifications, Vector nextRequests) {
        
        System.out.println("TODO");
    }

    protected Position getMove() {
        Random random = new Random();
        int randomIndex = random.nextInt(remainingMoves.size());
        int[] move = MOVES[remainingMoves.get(randomIndex)];
        this.remainingMoves.remove(randomIndex);
        return getNextPosition(move);
    }

    protected Position getNextPosition(int[] move) {
        AnimalAgent agent = (AnimalAgent) this.myAgent;
        return new Position(agent.getX() + move[0], agent.getY() + move[1]);
    }
}