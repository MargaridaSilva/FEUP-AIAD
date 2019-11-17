package behaviours.animals.eat;

import agents.AnimalAgent;

import behaviours.animals.BehaviourManager;
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

public class EatManager extends TickerBehaviour {
    public Position food = new Position(0,0);
    BehaviourManager parentBehaviour;

    public EatManager(AnimalAgent a, BehaviourManager parentBehaviour) {
        super(a, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
    }

    @Override
    protected void onTick() {


        if(onFood()){
            //Eat
            this.eatFood();

        }else{
            SequentialBehaviour eat = new SequentialBehaviour(myAgent);

            // Find Food
            eat.addSubBehaviour(new FindFood(myAgent, FindFood.prepareRequest(myAgent), this));

            // Move Towards Food
            eat.addSubBehaviour(new MoveToGoal(myAgent, food));

            this.parentBehaviour.addSubBehaviour(eat);
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


}
