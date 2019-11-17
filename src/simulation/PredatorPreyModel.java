package simulation;

import java.util.ArrayList;

import agents.ObserverAgent;
import agents.PredatorAgent;
import agents.PreyAgent;
import agents.AnimalAgent.Gender;
import elements.Plant;
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
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.Object2DDisplay;
import utils.Position;
import utils.PositionGenerator;
import utils.RandomPositionGenerator;

public class PredatorPreyModel extends Repast3Launcher {

    private ArrayList<Drawable> elementsList;

    private ObserverAgent observer;

    private PositionGenerator positionGenerator;
    private ContainerController mainContainer;
    
    private Space space;
    private DisplaySurface dsurf;
    private Displayable display;
    private int width = 20;
    private int height = 20;
    private int malePredators = 0;
    private int femalePredators = 0;
    private int malePreys = 1;
    private int femalePreys = 1;
    private int plants = 5;
    private int nPreys = 0;
    private int nPredators = 0;

    public PredatorPreyModel() {
        elementsList = new ArrayList<>();
        positionGenerator = new RandomPositionGenerator(width, height);
    }

    public void launchJade() {
        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        mainContainer = rt.createMainContainer(p1);
    }

    @Override
    public String[] getInitParam() {
        String[] params = {"Width", "Height", "MalePredators", "FemalePredators", "MalePreys", "FemalePreys", "Plants"};
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
        
        ((Object2DDisplay) display).setObjectList(elementsList);
        addSimEventListener(dsurf);
        dsurf.display();

        getSchedule().scheduleActionAtInterval(1, dsurf, "updateDisplay", Schedule.LAST);
    }

    public void removeElement(Drawable elem) {
        this.elementsList.remove(elem);
    }

    public void addElement(Drawable elem) {
        this.elementsList.add(elem);
    }

    private void launchPredators() throws StaleProxyException {

        int numPredators = malePredators + femalePredators;

        for (int i = 0; i < numPredators; ++i) {

            Position predatorPosition = positionGenerator.getPosition();
            Gender gender = Gender.MALE;

            if(i >= malePredators) {
                gender = Gender.FEMALE;
            }

            addPredator(predatorPosition, gender);
        }
    }

    public void addPredator(Position position, Gender gender) throws StaleProxyException {
        nPredators++;
        String id = "predator-" + nPredators;
        PredatorAgent predator = PredatorAgent.generatePredatorAgent(this, space, id, position, gender);
        this.addElement(predator);
        this.mainContainer.acceptNewAgent(id, predator).start();
        this.observer.addAgent(predator);
    }

    private void launchPreys() throws StaleProxyException {

        int numPreys = malePreys + femalePreys;

        for (int i = 0; i < numPreys; ++i) {

            Position preyPosition = positionGenerator.getPosition();
            Gender gender = Gender.MALE;

            if(i >= malePreys) {
                gender = Gender.FEMALE;
            }
            
            addPrey(preyPosition, gender);
        }
    }

    public void addPrey(Position position, Gender gender) throws StaleProxyException {
        nPreys++;
        String id = "preys-" + nPreys; 
        PreyAgent prey = PreyAgent.generatePreyAgent(this, space, id, position, gender);
        this.addElement(prey);
        this.mainContainer.acceptNewAgent(id, prey).start();
        this.observer.addAgent(prey);
    }

    private void launchPlants(){

        int numPlants = plants;

        for (int i = 0; i < numPlants; ++i) {

            String id = "plants-" + i;
            Position plantsPosition = positionGenerator.getPosition();

            Plant plant = Plant.generatePlant(this, space, id, plantsPosition);
            this.addElement(plant);
        }
    }

    private void launchObserver() throws StaleProxyException {
        
        this.observer = new ObserverAgent(this);
        this.mainContainer.acceptNewAgent("observer", this.observer).start();
    }

    private void launchAgents() throws StaleProxyException {

        launchObserver();
        launchPlants();
        launchPreys();
        launchPredators();
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

    
    public int getPlants() {
        return plants;
    }

    public void setPlants(int plants) {
        this.plants = plants;
    }

    @Override
    public String getName() {
        
        return "Predator Prey Simulation";
    }

    public Space getSpace(){
        return space;
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