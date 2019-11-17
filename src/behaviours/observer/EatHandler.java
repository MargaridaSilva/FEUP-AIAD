package behaviours.observer;

import agents.AnimalAgent;
import agents.ObserverAgent;
import agents.PredatorAgent;
import agents.PreyAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.core.behaviours.DataStore;
import sajas.proto.states.MsgReceiver;
import utils.Communication;
import utils.Position;

public class EatHandler extends MsgReceiver {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.INFORM),
            MessageTemplate.MatchOntology(Communication.Ontology.HANDLE_EAT));

    public EatHandler(Agent agent) {
        this(agent, template);
    }

    public EatHandler(Agent agent, MessageTemplate messageTemplate) {
        super(agent, messageTemplate, sajas.proto.states.MsgReceiver.INFINITE, new DataStore(), "terminate-msg");
    }

    @Override
    protected void handleMessage(ACLMessage msg) {
        ObserverAgent observer = (ObserverAgent)this.myAgent;

        try {
            Position position = (Position) msg.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
    }
}