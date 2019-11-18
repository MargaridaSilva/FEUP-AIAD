package behaviours.observer;

import agents.ObserverAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sajas.core.Agent;
import sajas.core.behaviours.DataStore;
import sajas.proto.states.MsgReceiver;
import utils.Communication;

public class RemoveAgent extends MsgReceiver {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.INFORM),
            MessageTemplate.MatchOntology(Communication.Ontology.TERMINATE));

    public RemoveAgent(Agent agent) {
        this(agent, template);
    }

    public RemoveAgent(Agent agent, MessageTemplate messageTemplate) {
        super(agent, messageTemplate, sajas.proto.states.MsgReceiver.INFINITE, new DataStore(), "terminate-msg");
    }

    @Override
    protected void handleMessage(ACLMessage msg) {
        ObserverAgent observer = (ObserverAgent)this.myAgent;
        observer.removeAgent(msg.getSender());
        System.out.println(msg.getSender());
    }
}