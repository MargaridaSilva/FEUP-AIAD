package behaviours.animals.mate;

import sajas.core.behaviours.DataStore;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sajas.core.Agent;
import sajas.proto.AchieveREResponder;
import sajas.proto.ProposeResponder;
import sajas.proto.states.MsgReceiver;
import utils.Communication;

public class WaitMale extends MsgReceiver {

    private FemaleMateManager mateManager;

    private static final MessageTemplate template =
            MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                    MessageTemplate.or(
            MessageTemplate.MatchOntology(Communication.Ontology.PREDATOR_REACHED_FEMALE),
            MessageTemplate.MatchOntology(Communication.Ontology.WITHOUT_ENERGY)));

    public WaitMale(Agent agent, FemaleMateManager mateManager) {
        this(agent, template, mateManager);
    }

    public WaitMale(Agent agent, MessageTemplate messageTemplate, FemaleMateManager mateManager) {
        super(agent, messageTemplate, sajas.proto.states.MsgReceiver.INFINITE, new DataStore(), "mate-uptate-msg");
        this.mateManager = mateManager;
    }

    @Override
    protected void handleMessage(ACLMessage msg) {

        System.out.println("-------------- received mate message");
        
        String ontology = msg.getOntology();

        switch(ontology) {
            case Communication.Ontology.PREDATOR_REACHED_FEMALE:
                this.mateManager.setMateState();
                break;
            case Communication.Ontology.WITHOUT_ENERGY:
                this.mateManager.setRandomMoveCFPState();
                break;
            default:
                break;
        }
    }
}