package agents;

import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import launchers.EnvironmentLauncher;
import sajas.core.behaviours.CyclicBehaviour;
import sajas.domain.DFService;
import utils.Position;

import java.util.Random;

public class PreyAgent extends AnimalAgent{

    private PreyAgent(EnvironmentLauncher model, Position position, float energyExpenditure) {
        super(model, position, energyExpenditure);
        //this.node.setColor(this.color);
    }

    public static PreyAgent generatePreyAgent(EnvironmentLauncher model, Position position) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PreyAgent(model, position, energyExpenditure);
    }

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hallo! Prey-agent "+ getAID().getName()+" is ready.");

        this.registerService("prey-service", "prey-name", new String[]{},  new String[]{});
        addBehaviour(new HelloRequestsServer());
    }

    /**
     */
    private class HelloRequestsServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            System.out.println("Prey " + getAID() + "received message: ");
            if (msg != null) {
                // CFP Message received. Process it
                String title = msg.getContent();
                System.out.println(title);

                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("Hello everyone");
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }  // End of inner class OfferRequestsServer
}
