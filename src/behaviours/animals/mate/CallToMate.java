package behaviours.animals.mate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import agents.AnimalAgent;
import agents.PredatorAgent;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import utils.Communication;
import utils.Locator;
import utils.MessageConstructor;

public class CallToMate extends Behaviour {

    private boolean finished;
    private FemaleMateManager mateManager;

    public CallToMate(Agent femaleAgent, FemaleMateManager mateManager) {
        super(femaleAgent);
        this.finished = false;
        this.mateManager = mateManager;
    }
    @Override
    public void action() {
        ArrayList<AID> males = null;
        String service = Communication.ServiceType.PREY_MATE;
        if(myAgent instanceof PredatorAgent)
            service = Communication.ServiceType.PREDATOR_MATE;
        males = Locator.locate(this.myAgent, service);
        ACLMessage msg = MessageConstructor.getMessage(males, 
                                                    ACLMessage.CFP, 
                                                    FIPANames.InteractionProtocol.FIPA_CONTRACT_NET, 
                                                    Communication.Ontology.FIND_MATE, 
                                                    Communication.Language.MATE, 
                                                    ((AnimalAgent)this.myAgent).getPosition());

        Date date = new Date(System.currentTimeMillis() + 5000);    // wait 1 second for proposals
        msg.setReplyByDate(date);                                     
        this.mateManager.addSubBehaviour(new MateProposal(this.myAgent, msg, this.mateManager));
        this.finished = true;
    }

    @Override
    public boolean done() {
        return this.finished;
    }


    

}