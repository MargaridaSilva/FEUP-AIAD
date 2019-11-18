package behaviours.animals.eat;

import agents.AnimalAgent;

import agents.PredatorAgent;
import agents.PreyAgent;
import behaviours.animals.BehaviourManager;
import behaviours.animals.move.Move;
import behaviours.animals.move.MoveManager;
import jade.lang.acl.ACLMessage;
import sajas.core.behaviours.Behaviour;
import jade.core.AID;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Locator;
import utils.Position;
import utils.RandomPositionGenerator;

import java.util.ArrayList;
import java.util.Random;

public class EatManager extends TickerBehaviour implements MoveManager {

    Position food;
    BehaviourManager behaviourManager;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;
    private double maxEnergy;

    public EatManager(AnimalAgent a, BehaviourManager behaviourManager) {
        super(a, Configs.TICK_PERIOD);
        this.behaviourManager = behaviourManager;
        this.moveCompleted = false;
        Random random = new Random();
        maxEnergy = random.nextDouble() - 0.5;
    }

    public void setFood(Position food) {
        this.food = food;
    }

    @Override
    protected void onTick() {
        
        AnimalAgent animal = (AnimalAgent) myAgent;

        if (animal.getEnergy() <= Configs.MIN_ENERGY_EAT || animal.getEnergy() >= maxEnergy) {
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour();
        }
        //else if(moveCompleted)
          //  this.addNextMove();

        if(this.onFood()){
            this.eatFood();
        }else{
            this.moveTowardsFood();
        }
    }

    private void eatFood(){
        ACLMessage msg = null;
        //Eat
        if(myAgent instanceof PredatorAgent){
            AnimalAgent animal = (AnimalAgent) myAgent;
            AID prey = animal.getModel().getAID(food);
            msg =  EatPrey.prepareRequest(myAgent, prey);
        }else if(myAgent instanceof PreyAgent){
            AID observer = Locator.findObserver(myAgent);
            msg = EatPlant.prepareRequest(myAgent, observer, food);
        }
        this.behaviourManager.addSubBehaviour(new Eat(myAgent, msg));
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
