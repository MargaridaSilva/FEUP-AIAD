package behaviours.observer;

import agents.ObserverAgent;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.proto.AchieveREResponder;
import utils.Communication;

import java.io.IOException;

public class TellFood extends AchieveREResponder {

    ObserverAgent agent;
    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST)),
            MessageTemplate.MatchOntology(Communication.Ontology.TELL_FOOD));

    public TellFood(Agent a) {
        super(a, template);
        this.agent = (ObserverAgent) myAgent;
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request){

        ACLMessage reply = request.createReply();
        AID sender = request.getSender();

        reply.setPerformative(ACLMessage.INFORM);
        try {
            if (agent.isPrey(sender))
                reply.setContentObject(agent.getPlants());
            else reply.setContentObject(agent.getPreys());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(agent.getPreys());
        return reply;
    }
}
