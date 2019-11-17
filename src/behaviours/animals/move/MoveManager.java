package behaviours.animals.move;

import java.util.ArrayList;

import sajas.core.behaviours.Behaviour;

public interface MoveManager {

    public void setMoveCompleted(ArrayList<Integer> nextPossibleMoves);
    public void addNextMove();
    public void removeSubBehaviour(Behaviour b);
    public void addSubBehaviour(Behaviour b);
}