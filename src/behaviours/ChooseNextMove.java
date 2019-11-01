package behaviours;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import agents.AnimalAgent;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import sajas.domain.DFService;
import utils.Position;

public class ChooseNextMove extends Behaviour {

    private boolean finished;
    private Navigate parentBehaviour;
    private Random random;
    private final int[][] MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    private ArrayList<Integer> remainingMoves;

    public ChooseNextMove(Navigate parentBehaviour, Agent agent, ArrayList<Integer> remainingMoves) {
        super(agent);
        this.finished = false;
        this.parentBehaviour = parentBehaviour;
        this.random = new Random(System.currentTimeMillis());
        this.remainingMoves = remainingMoves;
    }

    @Override
    public void action() {
        
        // Generate a random move
        AnimalAgent agent = (AnimalAgent) this.myAgent;
        if(remainingMoves.size() == 0) 
        { 
            this.finished = true;
            return;
        }
        int randomIndex = random.nextInt(remainingMoves.size());
        int[] move = this.MOVES[remainingMoves.get(randomIndex)];
        
        // Check if the random move is possible
        Position possiblePosition = new Position(agent.getX() + agent.getModel().getBoardDensity() * move[0], agent.getY() + agent.getModel().getBoardDensity() * move[1]);
        
        ACLMessage proposal = this.getProposalMessage(possiblePosition);
        this.remainingMoves.remove(randomIndex);
        RequestMoveApproval requestApprovalBehaviour = new RequestMoveApproval(this.myAgent, proposal, this.parentBehaviour, possiblePosition, remainingMoves);
        this.parentBehaviour.addSubBehaviour(requestApprovalBehaviour);
        this.finished = true;
    }

    public ACLMessage getProposalMessage(Position possiblePosition) {
        
        // locate the Observer agent
        AID observerAgentName =  this.findObserver();
        if(observerAgentName == null) {
            System.out.println("Predator-agent " + this.myAgent.getAID().getName() + " can't find the Observer agent.");
            return null;
        }

        // prepare Propose message
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.addReceiver(observerAgentName);
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
        msg.setOntology("position");
        try {
            msg.setContentObject(possiblePosition);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

    private AID findObserver() {
        DFAgentDescription agentDescription = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("inform-world");
        agentDescription.addServices(serviceDescription);
        DFAgentDescription[] result = new DFAgentDescription[0];
        try {
            result = DFService.search(this.myAgent, agentDescription);
        } catch(FIPAException e) {
            e.printStackTrace();
        }

        if(result.length == 0)
            return null;
        else
            return result[0].getName();
    }

    @Override
    public boolean done() {
        return this.finished;
    }
}