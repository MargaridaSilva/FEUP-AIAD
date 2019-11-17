package behaviours.animals.eat;

import agents.AnimalAgent;
import agents.ObserverAgent;
import agents.PredatorAgent;
import agents.PreyAgent;
import behaviours.animals.BehaviourManager;
import elements.Plant;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import sajas.proto.AchieveREResponder;
import utils.Communication;
import utils.Configs;
import utils.Position;

public class WaitEatPlant extends AchieveREResponder {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM)),
            MessageTemplate.MatchOntology(Communication.Ontology.EAT_PLANT));

    public WaitEatPlant(Agent agent) {
        super(agent, template);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        Position position = null;
        try {
            position = (Position) request.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        ObserverAgent observerAgent = (ObserverAgent) myAgent;
        try {
            observerAgent.getModel().removePlant(position);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        Double energy = Configs.PLANT_ENERGY;
        ACLMessage inform = request.createReply();
        inform.setPerformative(ACLMessage.INFORM);
        inform.setContent(energy.toString());
        return inform;
    }

}