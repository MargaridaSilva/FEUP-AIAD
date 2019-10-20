package launchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import agents.*;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

import sajas.core.Agent;
import sajas.core.Runtime;
import sajas.sim.repast3.Repast3Launcher;
import sajas.wrapper.ContainerController;

import uchicago.src.sim.engine.SimInit;

/**
 * A class to represent the launcher for the Environment simulation
 */
public class EnvironmentLauncher extends Repast3Launcher {

    private Random random;
    private static final boolean BATCH_MODE = true;
    /**
     * The dimensions of the Environment (a NxN matrix)
     */
    private int BOARD_DIM;
    private List<PredatorAgent> predators;
    private ContainerController mainContainer;
    private int NUM_PREDATORS;
    private Map<AID, Agent> agents;

    /**
     * Launching Repast3
     * 
     * @param args
     */
    public static void main(String[] args) throws IOException {
 
        int BOARD_DIM = Integer.parseInt(args[0]);
        int NUM_PREDATORS = Integer.parseInt(args[1]);

        SimInit init = new SimInit();
        init.setNumRuns(1); // works only in batch mode
        init.loadModel(new EnvironmentLauncher(BOARD_DIM, NUM_PREDATORS), null, EnvironmentLauncher.BATCH_MODE);
    }

    public EnvironmentLauncher(int BOARD_DIM, int NUM_PREDATORS) {
        super();
        this.random = new Random(System.currentTimeMillis());
        this.BOARD_DIM = BOARD_DIM;
        this.NUM_PREDATORS = NUM_PREDATORS;
        System.gc();
    }

    private void launchAgents() throws StaleProxyException {

        this.predators = new ArrayList<>();

        this.launchPredators();

        this.setUpAgentsAIDMap();
    }

    private void launchPredators() throws StaleProxyException {
        for (int i = 0; i < this.NUM_PREDATORS; ++i) {
            PredatorAgent predator = PredatorAgent.generatePredatorAgent(this, this.BOARD_DIM);
            this.predators.add(predator);
            this.mainContainer.acceptNewAgent("predator-" + i, predator).start();
        }
    }

    private void setUpAgentsAIDMap() {
        this.predators.forEach((Agent a) -> agents.put(a.getAID(), a));
    }

    @Override
    public String[] getInitParam() {
        return new String[] {"BOARD_DIM", "NUM_PREDATORS", "NUM_PREYS" };
    }

    @Override
    public String getName() {
        return "Predator-Prey MAS Simulation";
    }

    @Override
    protected void launchJADE() {

        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        mainContainer = rt.createMainContainer(p1);

        try {
            launchAgents();
        } catch (StaleProxyException e) {
            System.err.println("Failed to launch agents on the main container.");
            e.printStackTrace();
        }
    }

}