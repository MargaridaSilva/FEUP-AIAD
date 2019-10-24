package launchers;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import agents.PredatorAgent;
import agents.PreyAgent;
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
import uchicago.src.sim.gui.Network2DDisplay;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.gui.OvalNetworkItem;
import uchicago.src.sim.network.DefaultDrawableNode;
import uchicago.src.sim.space.Object2DGrid;
import utils.PositionGenerator;
import utils.RandomPositionGenerator;

/**
 * A class to represent the launcher for the Environment simulation
 */
public class EnvironmentLauncher extends Repast3Launcher {

    /**
     * The dimensions of the Environment (a NxN matrix)
     */
    private int BOARD_DIM;
    private static final boolean BATCH_MODE = true;
    private static final int DENSITY = 20;
    private static final String ENVIRONMENT_NAME = "Predator-Prey Environment";
    private Random random;
    private PositionGenerator positionGenerator;
    private DisplaySurface dsurf;
    private Object2DGrid world;
    private List<PredatorAgent> predators;
    private List<PreyAgent> preys;
    private ContainerController mainContainer;
    private int NUM_PREDATORS;
    private int NUM_PREYS;
    private Map<AID, Agent> agents;

    private static List<DefaultDrawableNode> nodes;

    public EnvironmentLauncher(int BOARD_DIM, int NUM_PREDATORS, int NUM_PREYS) {
        super();
        this.random = new Random(System.currentTimeMillis());
        this.agents = new ConcurrentHashMap<>();
        this.predators = new ArrayList<>();
        this.preys = new ArrayList<>();
        this.BOARD_DIM = BOARD_DIM * DENSITY;
        this.NUM_PREDATORS = NUM_PREDATORS;
        this.NUM_PREYS = NUM_PREYS;
        this.positionGenerator = new RandomPositionGenerator(BOARD_DIM);
        System.gc();
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

    private void setUpAgentsAIDMap() {
        this.predators.forEach((Agent a) -> this.agents.put(a.getAID(), a));
        this.preys.forEach((Agent a) -> this.agents.put(a.getAID(), a));
    }

    private void launchPredators() throws StaleProxyException {
        for (int i = 0; i < this.NUM_PREDATORS; ++i) {
            int[] predatorPosition = positionGenerator.getPosition();
            int[] gridPosition = getGridPosition(predatorPosition);
            PredatorAgent predator = PredatorAgent.generatePredatorAgent(this, predatorPosition);
            this.predators.add(predator);
            this.mainContainer.acceptNewAgent("predator-" + i, predator).start();
            DefaultDrawableNode node = generateNode("predator-" + i, Color.RED, predatorPosition[0]*DENSITY, predatorPosition[1]*DENSITY);
            nodes.add(node);
            predator.setNode(node);
            //this.world.putObjectAt(gridPosition[0], gridPosition[1], predator);
        }
    }

    private void launchPreys() throws StaleProxyException {
        for (int i = 0; i < this.NUM_PREYS; ++i) {
            int[] preyPosition = positionGenerator.getPosition();
            int[] gridPosition = getGridPosition(preyPosition);
            PreyAgent prey = PreyAgent.generatePreyAgent(this, preyPosition);
            this.preys.add(prey);
            this.mainContainer.acceptNewAgent("prey-" + i, prey).start();
            DefaultDrawableNode node = generateNode("prey-" + i, Color.BLUE, preyPosition[0]*DENSITY, preyPosition[1]*DENSITY);
            nodes.add(node);
            prey.setNode(node);
            //this.world.putObjectAt(gridPosition[0], gridPosition[1], predator);
        }
    }

    private void launchAgents() throws StaleProxyException {
        nodes = new ArrayList<DefaultDrawableNode>();
        this.launchPredators();
        this.launchPreys();
        this.setUpAgentsAIDMap();
    }

    /**
     * Agents are launched
     * Display is added to a display surface
     * @throws StaleProxyException
     */

    public void buildSchedule() {
    }


    public void buildDisplay() {
        Network2DDisplay display = new Network2DDisplay(nodes, this.BOARD_DIM, this.BOARD_DIM);
        dsurf.addDisplayableProbeable(display, "Predators");
        dsurf.addZoomable(display);
        addSimEventListener(dsurf);
        dsurf.display();
    }

    public void buildModel() {

        predators = new ArrayList<>();
        this.world = new Object2DGrid(this.BOARD_DIM, this.BOARD_DIM);
    }

    @Override
    public void begin() {
        super.begin();
        this.buildModel();
        this.buildDisplay();
        //this.buildSchedule();
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
     * Launching Repast3
     *
     * @param args
     */

    public static void main(String[] args) throws IOException {

        int BOARD_DIM = Integer.parseInt(args[0]);
        int NUM_PREDATORS = Integer.parseInt(args[1]);
        int NUM_PREYS =  Integer.parseInt(args[2]);

        SimInit init = new SimInit();
        init.setNumRuns(1); // works only in batch mode
        init.loadModel(new EnvironmentLauncher(BOARD_DIM, NUM_PREDATORS, NUM_PREYS), null, EnvironmentLauncher.BATCH_MODE);
    }


    /************ NODES *****************/
    public static DefaultDrawableNode getNode(String label) {
        for(DefaultDrawableNode node : nodes) {
            if(node.getNodeLabel().equals(label)) {
                return node;
            }
        }
        return null;
    }

    private DefaultDrawableNode generateNode(String label, Color color, int x, int y) {
        OvalNetworkItem oval = new OvalNetworkItem(x,y);
        oval.allowResizing(false);
        oval.setHeight(DENSITY);
        oval.setWidth(DENSITY);

        DefaultDrawableNode node = new DefaultDrawableNode(label, oval);
        node.setColor(color);

        return node;
    }

}