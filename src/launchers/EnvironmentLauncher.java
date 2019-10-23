package launchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import agents.PredatorAgent;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import sajas.core.Agent;
import sajas.core.Runtime;
import sajas.sim.repast3.Repast3Launcher;
import sajas.wrapper.ContainerController;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.space.Object2DGrid;
import utils.PositionGenerator;

/**
 * A class to represent the launcher for the Environment simulation
 */
public class EnvironmentLauncher extends Repast3Launcher {

    /**
     * The dimensions of the Environment (a NxN matrix)
     */
    private int BOARD_DIM;
    private static final boolean BATCH_MODE = true;
    private static final String ENVIRONMENT_NAME = "Predator-Prey Environment";
    private Random random;
    private PositionGenerator positionGenerator;
    private DisplaySurface dsurf;
    private Object2DGrid world;
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
        this.agents = new ConcurrentHashMap<>();
        this.predators = new ArrayList<>();
        this.BOARD_DIM = BOARD_DIM;
        this.NUM_PREDATORS = NUM_PREDATORS;
        this.positionGenerator = new PositionGenerator(BOARD_DIM);
        System.gc();
    }

    @Override
    public void setup() {
        super.setup();

        if(this.dsurf != null) 
            this.dsurf.dispose();
        
        this.dsurf = new DisplaySurface(this, ENVIRONMENT_NAME);
        registerDisplaySurface(ENVIRONMENT_NAME, dsurf);
    }

    /**
     * Agents are launched
     * Display is added to a display surface
     * @throws StaleProxyException
     */
    public void buildModel() {
        
        predators = new ArrayList<>();

        this.world = new Object2DGrid(this.BOARD_DIM, this.BOARD_DIM);
    }

    public void buildDisplay() {
        Object2DDisplay predatorsDisplay = new Object2DDisplay(world);
        predatorsDisplay.setObjectList(predators);
        this.dsurf.addDisplayable(predatorsDisplay, "Predators");
       
        this.dsurf.display();
    }

    public void buildSchedule() {
    }

    @Override
    public void begin() {
        
        this.buildModel();
        this.buildDisplay();
        this.buildSchedule();
        super.begin();
    }

    private void launchAgents() throws StaleProxyException {

        this.launchPredators();
        this.setUpAgentsAIDMap();
    }

    private void launchPredators() throws StaleProxyException {
        for (int i = 0; i < this.NUM_PREDATORS; ++i) {
            int[] predatorPosition = positionGenerator.getPosition();
            int[] gridPosition = getGridPosition(predatorPosition);
            PredatorAgent predator = PredatorAgent.generatePredatorAgent(this, predatorPosition);
            this.predators.add(predator);
            this.mainContainer.acceptNewAgent("predator-" + i, predator).start();
            this.world.putObjectAt(gridPosition[0], gridPosition[1], predator);
        }
    }

    private void setUpAgentsAIDMap() {
        this.predators.forEach((Agent a) -> this.agents.put(a.getAID(), a));
    }

    private int[] getGridPosition(int[] position) {
        int x = position[0];
        int y = position[1];
        return new int[]{x, y};
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