package agents;

import java.awt.*;
import java.util.Random;


import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import behaviours.Move;
import behaviours.Navigate;
import launchers.EnvironmentLauncher;
import jade.core.AID;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.TickerBehaviour;
import sajas.domain.DFService;
import uchicago.src.sim.gui.Drawable;
import utils.Position;

/**
 * A class to represent a Predator agent
 */
public final class PredatorAgent extends AnimalAgent {

    private AID[] preyAgentList;

    private PredatorAgent(EnvironmentLauncher model, Position position, float energyExpenditure) {
        super(model, position, energyExpenditure);
        //this.node.setColor(this.color);
    }

    public static PredatorAgent generatePredatorAgent(EnvironmentLauncher model, Position position) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PredatorAgent(model, position, energyExpenditure);
    }

    @Override
    protected void setup() {
        super.addBehaviour(new UpdatePreyList(this, 10000));
        super.setup();

        super.addBehaviour(new Navigate(this,100));

		System.out.println("Predator-agent "+ this.getAID().getName()+" is ready.");
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Predator-agent " + this.getAID() + " terminating");
    }


    private class UpdatePreyList extends TickerBehaviour{

        public UpdatePreyList(PredatorAgent predatorAgent, int i) {
            super(predatorAgent, i);
        }

        @Override
        protected void onTick() {

            // Update the list of prey agents
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("prey-service");
            template.addServices(sd);

            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);


                System.out.println("Found the following prey agents:");

                preyAgentList = new AID[result.length];

                for (int i = 0; i < result.length; ++i) {
                    preyAgentList[i] = result[i].getName();
                    System.out.println(preyAgentList[i].getName());
                }
               System.out.println();

            }
            catch (FIPAException fe) {
                fe.printStackTrace();
            }

            // Perform the request
            myAgent.addBehaviour(new SayHelloPerformer());
        }
    }

    private class SayHelloPerformer extends Behaviour {

        @Override
        public void action() {

            System.out.println("Sending Hello message to :");

            // Send the cfp to all sellers
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            for (int i = 0; i < preyAgentList.length; ++i) {
                cfp.addReceiver(preyAgentList[i]);
                System.out.println(preyAgentList[i]);
            }
            cfp.setContent("Hello Prey");
            cfp.setConversationId("comm");
            cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
            myAgent.send(cfp);
            System.out.println("Sent");
        }

        @Override
        public boolean done() {
            return false;
        }
    }




}