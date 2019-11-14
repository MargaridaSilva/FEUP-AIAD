package behaviours.mate;

import agents.AnimalAgent;
import behaviours.BehaviourManager;
import behaviours.MoveTowardsFemale;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.proto.ContractNetResponder;
import utils.Communication;
import utils.Position;

public class AnswerMateRequest extends ContractNetResponder {

    private BehaviourManager parentBehaviour;
    private Position femalePosition;

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                    MessageTemplate.MatchPerformative(ACLMessage.CFP)),
            MessageTemplate.MatchOntology(Communication.Ontology.PREDATOR_FIND_MATE));

    public AnswerMateRequest(Agent maleAgent, BehaviourManager parentBehaviour) {
        this(maleAgent, template);
        this.parentBehaviour = parentBehaviour;
    }

    public AnswerMateRequest(Agent maleAgent, MessageTemplate messageTemplate) {
        super(maleAgent, messageTemplate);
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {

        try {
            this.femalePosition = ((Position)cfp.getContentObject());
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        if (((AnimalAgent) myAgent).getEnergy() < 0.8) {
            throw new RefuseException("not-enough-energy");
        } else {
            ACLMessage propose = cfp.createReply();
            propose.setPerformative(ACLMessage.PROPOSE);
            propose.setContent(String.valueOf(((AnimalAgent) myAgent).getEnergyExpenditure()));
            return propose;
        }
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept)
            throws FailureException {
                
                ACLMessage inform = accept.createReply();
                inform.setPerformative(ACLMessage.INFORM);
                ((AnimalAgent)this.myAgent).setMateColor();
                //this.parentBehaviour.stayStill(); // TODO: fix
                MoveTowardsFemale moveTowardsFemaleBehaviour = new MoveTowardsFemale(this.myAgent, this.parentBehaviour, this.femalePosition);
                this.parentBehaviour.addSubBehaviour(moveTowardsFemaleBehaviour);
                return inform;
    }



}