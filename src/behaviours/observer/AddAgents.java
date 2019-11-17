package behaviours.observer;

import agents.ObserverAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.core.behaviours.DataStore;
import sajas.proto.states.MsgReceiver;
import utils.Communication;

public class AddAgents extends MsgReceiver {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.INFORM),
            MessageTemplate.or(MessageTemplate.MatchOntology(Communication.Ontology.GIVE_BIRTH_PREDATORS),
                    MessageTemplate.MatchOntology(Communication.Ontology.GIVE_BIRTH_PREYS)));

    public AddAgents(Agent agent) {
        this(agent, template);
    }

    public AddAgents(Agent agent, MessageTemplate messageTemplate) {
        super(agent, messageTemplate, sajas.proto.states.MsgReceiver.INFINITE, new DataStore(), "add.agents");
    }

    @Override
    protected void handleMessage(ACLMessage msg) {

        int numChildren = 0;

        try {
            numChildren = (int) msg.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
        ObserverAgent observer = (ObserverAgent)myAgent;

        if(msg.getOntology() == Communication.Ontology.GIVE_BIRTH_PREDATORS)
            observer.addPredators(numChildren);
        else
            observer.addPreys(numChildren);
    }
}