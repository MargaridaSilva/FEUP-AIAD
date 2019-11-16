package simulation;

import java.util.ArrayList;

import agents.AnimalAgent;
import agents.ObserverAgent;
import agents.PredatorAgent;
import agents.PreyAgent;
import agents.AnimalAgent.Gender;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import sajas.core.Runtime;
import sajas.sim.repast3.Repast3Launcher;
import sajas.wrapper.ContainerController;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.Displayable;
import uchicago.src.sim.gui.Object2DDisplay;
import utils.Position;
import utils.PositionGenerator;
import utils.RandomPositionGenerator;

public class PredatorPreyModel extends Repast3Launcher {

    private ArrayList<AnimalAgent> agentList;

    private ObserverAgent observer;

    private PositionGenerator positionGenerator;
    private ContainerController mainContainer;
    
    private Space space;
    private DisplaySurface dsurf;
    private Displayable display;
    private int width = 20;
    private int height = 20;

    private int malePredators = 1;
    private int femalePredators = 1;
    private int malePreys = 1;
    private int femalePreys = 1;

    public PredatorPreyModel() {
        
        agentList = new ArrayList<AnimalAgent>();
        positionGenerator = new RandomPositionGenerator(width, height);
    }

    public void launchJade() {
        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        mainContainer = rt.createMainContainer(p1);
    }

    public String[] getInitParams() {
        String[] params = {"Width", "Height", "MalePredators", "FemalePredators", "MalePreys", "FemalePreys"};
        return params;
    }

    public void buildAndScheduleDisplay() {
        
        // display surface
        if (dsurf != null) 
            dsurf.dispose();

        System.gc();
        dsurf = new DisplaySurface(this, "Predator Prey Display");
        registerDisplaySurface("Main", dsurf);

        space = new PredatorPreySpace(this, width, height);
        display = space.getDisplay();
        
        dsurf.addDisplayableProbeable(display, "Predator Prey Display");
        ((Object2DDisplay) display).setObjectList(agentList);
        addSimEventListener(dsurf);
        dsurf.display();

        getSchedule().scheduleActionAtInterval(1, dsurf, "updateDisplay", Schedule.LAST);
    }

    public void removeAgent(AnimalAgent agent) {
        this.agentList.remove(agent);
    }

    public void addAgent(AnimalAgent agent) {
        this.agentList.add(agent);
    }

    private void launchPredators() throws StaleProxyException {

        int numPredators = malePredators + femalePredators;

        for (int i = 0; i < numPredators; ++i) {

            String id = "predator-" + i;
            Position predatorPosition = positionGenerator.getPosition();
            Gender gender = Gender.MALE;

            if(i >= malePredators) {
                gender = Gender.FEMALE;
            }

            PredatorAgent predator = PredatorAgent.generatePredatorAgent(this, space, id, predatorPosition, gender);
            this.agentList.add(predator);
            this.mainContainer.acceptNewAgent(id, predator).start();
            this.observer.addAgent(predator);
        }
    }

    private void launchPreys() throws StaleProxyException {

        int numPreys = malePreys + femalePreys;

        for (int i = 0; i < numPreys; ++i) {

            String id = "prey-" + i;
            Position preyPosition = positionGenerator.getPosition();
            Gender gender = Gender.MALE;

            if(i >= malePreys) {
                gender = Gender.FEMALE;
            }

            PreyAgent prey = PreyAgent.generatePreyAgent(this, space, id, preyPosition, gender);
            this.agentList.add(prey);
            this.mainContainer.acceptNewAgent(id, prey).start();
        }
    }

    private void launchObserver() throws StaleProxyException {
        
        this.observer = new ObserverAgent(this);
        this.mainContainer.acceptNewAgent("observer", this.observer).start();
    }

    private void launchAgents() throws StaleProxyException {

        launchObserver();
        launchPredators();
        launchPreys();
    }

    @Override
	public void begin() {
		super.begin();
        buildAndScheduleDisplay();
	}

    public static void main(String[] args) {
        SimInit init = new SimInit();
        init.setNumRuns(1); // works only in batch mode
        PredatorPreyModel model = new PredatorPreyModel();
        init.loadModel(model, null, false);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int val) {
        width = val;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int val) {
        height = val;
    }

    public int getMalePredators() {
        return malePredators;
    }

    public int getFemalePredators() {
        return femalePredators;
    }

    public int getMalePreys() {
        return malePreys;
    }

    public int getFemalePreys() {
        return femalePreys;
    }

    public void setMalePredators(int malePredators) {
        this.malePredators = malePredators;
    }

    public void setFemalePredators(int femalePredators) {
        this.femalePredators = femalePredators;
    }

    public void setMalePreys(int malePreys) {
        this.malePreys = malePreys;
    }

    public void setFemalePreys(int femalePreys) {
        this.femalePreys = femalePreys;
    }

    @Override
    public String[] getInitParam() {

        return new String[]{"MalePredators", "Height", "Width", "FemalePredators", "MalePreys", "FemalePreys"};
    }

    @Override
    public String getName() {
        
        return "Predator Prey Simulation";
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