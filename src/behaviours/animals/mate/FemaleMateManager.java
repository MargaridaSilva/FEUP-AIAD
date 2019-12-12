package behaviours.animals.mate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import agents.AnimalAgent;
import agents.PredatorAgent;
import behaviours.animals.BehaviourManager;
import behaviours.animals.move.Move;
import behaviours.animals.move.MoveManager;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Communication;
import utils.Configs;
import utils.Locator;

public class FemaleMateManager extends TickerBehaviour implements MoveManager {

    private static enum State {
        RANDOM_MOVE_CFP, WAIT_MALE, MATE, END_MATE
    }

    private State state;
    private BehaviourManager behaviourManager;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;
    private WaitMale waitMale;

    public FemaleMateManager(AnimalAgent a, BehaviourManager behaviourManager) {

        super(a, Configs.TICK_PERIOD);
        this.state = State.RANDOM_MOVE_CFP;
        this.behaviourManager = behaviourManager;
        this.setRandomMoveCFPState();
        this.waitMale = null;
    }

    @Override
    protected void onTick() {

        AnimalAgent animal = (AnimalAgent) myAgent;
        
        if (animal.getEnergy() < Configs.MIN_ENERGY_MATE) {
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour(); 
            return; 
        }
         
        switch (state) {
        case RANDOM_MOVE_CFP:
            break;
        case WAIT_MALE:
            break;
        case MATE:
            mate();
            break;
        case END_MATE:
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour();
            break;
        }
    }

    public void mate() {

        if(waitMale != null) {
            this.removeSubBehaviour(waitMale);
            waitMale = null;
        }
        AnimalAgent animal = (AnimalAgent)myAgent;
        animal.decreaseEnergy(1 - Configs.MIN_ENERGY_MATE);
        Random random = new Random();
        //int maxNumChildren = Configs.PREY_NUM_CHILDREN;
        int numChildren;

        //if (myAgent instanceof PredatorAgent)
        //    maxNumChildren = Configs.PREDATOR_NUM_CHILDREN;

        numChildren = 1;
        this.giveBirth(numChildren);

        setEndMateState();
    }

    public void resendMateRequest() {
        
        launchCFP();
    }

    public void giveBirth(int numChildren) {

        AID observerAgent = Locator.findObserver(myAgent);
        ACLMessage birthMessage = new ACLMessage(ACLMessage.INFORM);
        String ontology = Communication.Ontology.GIVE_BIRTH_PREYS;
        
        if (myAgent instanceof PredatorAgent)
            ontology = Communication.Ontology.GIVE_BIRTH_PREDATORS;

        birthMessage.setOntology(ontology);
        birthMessage.addReceiver(observerAgent);
        
        try {
            birthMessage.setContentObject(numChildren);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.myAgent.send(birthMessage);
    }

    public void setRandomMoveCFPState() {

        this.state = State.RANDOM_MOVE_CFP;
        this.launchCFP();
    }

    public void setMateState() {

        this.state = State.MATE;
    }

    public void setEndMateState() {

        this.state = State.END_MATE;
    }

    public void setWaitMaleState(AID male) {
        
        this.state = State.WAIT_MALE;
        this.waitMale = new WaitMale(myAgent, this);
        this.addSubBehaviour(waitMale);
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

    public void launchCFP() {

        if(waitMale != null) {
            this.removeSubBehaviour(waitMale);
            waitMale = null;
        }

        CallToMate callToMate = new CallToMate(myAgent, this);
        this.addSubBehaviour(callToMate);
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
