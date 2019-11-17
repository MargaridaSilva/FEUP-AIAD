package behaviours.animals.eat;

import agents.AnimalAgent;

import behaviours.animals.BehaviourManager;
import behaviours.animals.move.Move;
import behaviours.animals.move.MoveManager;
import behaviours.animals.move.MoveToGoal;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.SequentialBehaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Communication;
import utils.Configs;
import utils.Locator;
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
        if(onFood()){
            //Eat
            this.eatFood();

        }else{
            behaviourManager.addSubBehaviour(new FindFood(myAgent, FindFood.prepareRequest(myAgent), this));
        }
    }

    private void eatFood(){
        AID observerAgent = Locator.findObserver(myAgent);
        ACLMessage terminateMsg = new ACLMessage(ACLMessage.INFORM);
        terminateMsg.setOntology(Communication.Ontology.HANDLE_EAT);
        terminateMsg.addReceiver(observerAgent);
        try {
            terminateMsg.setContentObject(food);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAgent.send(terminateMsg);
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
