package behaviours.animals.eat;

import agents.AnimalAgent;

import agents.PredatorAgent;
import agents.PreyAgent;
import behaviours.animals.BehaviourManager;
import behaviours.animals.move.Move;
import behaviours.animals.move.MoveManager;
import behaviours.animals.move.MoveToGoal;
import jade.lang.acl.ACLMessage;
import sajas.core.behaviours.Behaviour;
import sajas.core.AID;
import sajas.core.behaviours.SequentialBehaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

import java.io.IOException;
import java.util.ArrayList;

public class EatManager extends TickerBehaviour implements MoveManager {

    Position food;
    BehaviourManager behaviourManager;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;

    public EatManager(AnimalAgent a, BehaviourManager behaviourManager) {
        super(a, Configs.TICK_PERIOD);
        this.behaviourManager = behaviourManager;
        this.moveCompleted = false;
    }

    public void setFood(Position food) {
        this.food = food;
    }

    @Override
    protected void onTick() {
        System.out.println("AGENT " + myAgent.getName() + "- EATING " + ((AnimalAgent) myAgent).getEnergy());
        AnimalAgent animal = (AnimalAgent) myAgent;

        if (animal.getEnergy() <= 0) {
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour();
        }
        //else if(moveCompleted)
          //  this.addNextMove();
          
        if(onFood()){
            this.eatFood();
        }else{
            this.moveTowardsFood();
        }
    }

    private void eatFood(){
        //Eat
        if(myAgent instanceof PredatorAgent){
            //TODO: Get AID from prey
            AID aid = null;
        }else if(myAgent instanceof PreyAgent){

        }
    }

    private void moveTowardsFood(){
        // Find Food
        this.behaviourManager.addSubBehaviour(new FindFood(myAgent, FindFood.prepareRequest(myAgent), this));
    }

    private boolean onFood(){
        AnimalAgent animalAgent = (AnimalAgent) this.myAgent;

        if(animalAgent == null || this.food == null) return false;

        return animalAgent.getPosition().equals(this.food);
    }

    public void setMoveCompleted(ArrayList<Integer> nextPossibleMoves) {

        this.moveCompleted = true;
        this.possibleMoves = nextPossibleMoves;
    }

    @Override
    public void addNextMove() {

        moveCompleted = false;
        Move randomMoveBehaviour = new Move(this, myAgent, possibleMoves);
        this.addSubBehaviour(randomMoveBehaviour);
    }

    @Override
    public void removeSubBehaviour(Behaviour b) {

        this.behaviourManager.removeSubBehaviour(b);
    }

    @Override
    public void addSubBehaviour(Behaviour b) {

        this.behaviourManager.addSubBehaviour(b);
    }


}
