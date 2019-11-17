package behaviours.animals.mate;

import agents.AnimalAgent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.core.AID;
import sajas.core.Agent;
import sajas.proto.ContractNetResponder;
import utils.Communication;
import utils.Position;

public class AnswerMateProposal extends ContractNetResponder {

    private MaleMateManager mateManager;
    private AID female;
    private Position femalePosition;

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                    MessageTemplate.MatchPerformative(ACLMessage.CFP)),
            MessageTemplate.MatchOntology(Communication.Ontology.PREDATOR_FIND_MATE));

    public AnswerMateProposal(Agent maleAgent, MaleMateManager mateManager) {
        this(maleAgent, template);
        this.mateManager = mateManager;
        this.female = null;
        this.femalePosition = null;
    }

    public AnswerMateProposal(Agent maleAgent, MessageTemplate messageTemplate) {
        super(maleAgent, messageTemplate);
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {

        try {
            this.female = cfp.getSender();
            this.femalePosition = ((Position)cfp.getContentObject());
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        ACLMessage propose = cfp.createReply();
        propose.setPerformative(ACLMessage.PROPOSE);
        propose.setContent(String.valueOf(((AnimalAgent) myAgent).getEnergyExpenditure()));
        return propose;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept)
            throws FailureException {

        ACLMessage inform = accept.createReply();
        inform.setPerformative(ACLMessage.INFORM);
        ((AnimalAgent)this.myAgent).setMateColor();
        this.mateManager.setMoveToFemaleState(this.female, this.femalePosition);
        return inform;
    }



}