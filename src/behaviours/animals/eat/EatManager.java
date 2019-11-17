package behaviours.animals.eat;

import agents.AnimalAgent;

import agents.PredatorAgent;
import agents.PreyAgent;
import behaviours.animals.BehaviourManager;
import sajas.core.AID;
import sajas.core.behaviours.SequentialBehaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class EatManager extends TickerBehaviour {

    Position food;
    BehaviourManager parentBehaviour;

    public EatManager(AnimalAgent a, BehaviourManager parentBehaviour) {
        super(a, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
    }

    public void setFood(Position food) {
        this.food = food;
    }

    @Override
    protected void onTick() {

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
            this.parentBehaviour.addSubBehaviour(new Eat(myAgent, Eat.prepareRequest(myAgent, aid)));
        }else if(myAgent instanceof PreyAgent){

        }
    }

    private void moveTowardsFood(){
        SequentialBehaviour eat = new SequentialBehaviour(myAgent);

        // Find Food
        eat.addSubBehaviour(new FindFood(myAgent, FindFood.prepareRequest(myAgent), this));

        // Move Towards Food
        //eat.addSubBehaviour(new MoveToGoal(myAgent, food));

        this.parentBehaviour.addSubBehaviour(eat);
    }

    private boolean onFood(){
        AnimalAgent animalAgent = (AnimalAgent) this.myAgent;

        if(animalAgent == null || this.food == null) return false;

        return animalAgent.getPosition().equals(this.food);
    }


}
