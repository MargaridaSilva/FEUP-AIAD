package simulation;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import agents.AnimalAgent;
import agents.ObserverAgent;
import agents.PredatorAgent;
import agents.PreyAgent;
import agents.AnimalAgent.Gender;
import elements.Plant;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import sajas.core.Runtime;
import sajas.sim.repast3.Repast3Launcher;
import sajas.wrapper.ContainerController;
import uchicago.src.sim.analysis.DataRecorder;
import uchicago.src.sim.analysis.OpenSequenceGraph;
import uchicago.src.sim.engine.BasicAction;
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
    private int width = 30;
    private int height = 30;
    private int malePredators = 0;
    private int femalePredators = 0;
    private int malePreys = 1;
    private int femalePreys = 1;
    private int plants = 10;
    private int nPreys = 0;
    private int nPredators = 0;
    private int nPlants = 0;
    private double ratio_predators = 1;
    private double ratio_preys =     1;
    private double energy_expenditure_predators = 0.0075;
    private double energy_expenditure_preys = 0.0075;
    private int preys = 2;
    private int predators = 2;
    private boolean classified = false;
    private boolean regression = false;
    private double total_life = 0;
    private OpenSequenceGraph graph = null;
    private static ArrayList<ObserverAgent> observers = new ArrayList<ObserverAgent>();
    private ObserverAgent observer2;
    private ObserverAgent observer3;
    private ObserverAgent observer4;
    private ObserverAgent observer5;
    private HashMap<AID, Double> startTime;
    private int totalPreys = 0;

    public PredatorPreyModel() {
        elementsList = new ArrayList<>();
        startTime = new HashMap<>();
        positionGenerator = new RandomPositionGenerator(width, height);
    }

    public PredatorPreyModel(int num_plants, int num_predators, int num_preys, double ratio_predators, double ratio_preys, double energy_expenditure_predators, double energy_expenditure_preys) {
        this();
        this.plants = num_plants;
        this.predators = num_predators;
        this.preys = num_preys;
        this.ratio_predators = ratio_predators;
        this.ratio_preys = ratio_preys;

        this.malePredators =  (int) Math.floor(num_predators * ratio_predators + 0.5d);
        this.femalePredators = num_predators - this.malePredators;

        this.malePreys =  (int) Math.floor(num_preys * ratio_preys + 0.5d);
        this.femalePreys = num_preys - this.malePreys;

        this.energy_expenditure_predators = energy_expenditure_predators;
        this.energy_expenditure_preys = energy_expenditure_preys;

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

    public void writeClassificationResult(String class_result)  {
        if (!this.classified) {
            FileWriter fileWriter = null; //Set true for append mode
            try {
                fileWriter = new FileWriter("classification_dataset.csv", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(this.plants + "," + this.predators + "," + this.preys + "," + this.ratio_predators + "," + this.ratio_preys + "," + this.energy_expenditure_predators + "," + this.energy_expenditure_preys + "," + class_result);
            out.close();
            this.classified = true;
        }

        if (this.regression)
            System.exit(0);
    }

    public void writeRegressionResult() {
        if (!this.regression) {
            FileWriter fileWriter = null; //Set true for append mod
            try {
                fileWriter = new FileWriter("regression_dataset.csv", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(this.plants + "," + this.predators + "," + this.preys + "," + this.ratio_predators + "," + this.ratio_preys + "," + this.energy_expenditure_predators + "," + this.energy_expenditure_preys + "," + (this.total_life / this.totalPreys));
            out.close();
            this.regression = true;
        }
        if (this.classified)
            System.exit(0);
    }

    public void updateLifeExpectancy(AID agentId) {
        this.total_life += (this.getSchedule().getCurrentTime() - this.startTime.get(agentId));
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
        //dsurf.display();

        //buildGraph();
        //DataRecorder graphRecorder = new DataRecorder("./experiments/plants", this);
        //getSchedule().scheduleActionAtInterval(1, dsurf, "updateDisplay", Schedule.LAST);
        //getSchedule().scheduleActionAtInterval(1, graph, "step", Schedule.LAST);
        ////getSchedule().scheduleActionAtInterval(10, new BasicAction() {
        //    public void execute() {
        //        graphRecorder.record();
        //    }
        //});
        //getSchedule().scheduleActionAtEnd(graphRecorder, "writeToFile");
    }

    public void buildGraph(){
        if (graph != null)
            graph.dispose();
        graph = new OpenSequenceGraph("Energies Producers-Brokers", this);
        graph.setAxisTitles("time", "quantity");
        graph.addSequence("Plants", () -> { return observers.get(0).getNumPlants();}, Color.green);
        graph.addSequence("Predators", () -> { return observers.get(0).getNumPredators();}, Color.red);
        graph.addSequence("Preys", () -> { return observers.get(0).getNumPreys();}, Color.blue);

        graph.display();
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
        this.observers.get(0).addAgent(predator);
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
        this.observers.get(0).addAgent(prey);
        this.startTime.put(prey.getAID(), this.getSchedule().getCurrentTime());
        totalPreys++;
    }

    private void launchPlants() throws StaleProxyException {

        int numPlants = plants;

        for (int i = 0; i < numPlants; ++i) {
            Position plantPosition = positionGenerator.getPosition();
            this.addPlant(plantPosition);
        }
    }

    public void addPlant(Position position) throws StaleProxyException {
        nPlants++;
        String id = "plant-" + nPlants;
        Plant plant = Plant.generatePlant(this, space, id, position);
        this.addElement(plant);
        this.observers.get(0).addPlant(plant);
    }

    public void removePlant(Position position) throws StaleProxyException {
        nPlants--;
        for(Drawable drawable : this.elementsList){
            if(drawable instanceof Plant){
                Plant plant = (Plant) drawable;
                if(plant.getPosition().equals(position)){
                    this.removeElement(plant);
                    this.observers.get(0).removePlant(plant);
                    break;
                }
            }
        }
    }

    private void launchObserver() throws StaleProxyException {
        for (int i = 0; i < Math.floor(((this.predators + this.preys) / 4)); i++) {
            this.observers.add(new ObserverAgent(this));
            this.mainContainer.acceptNewAgent("observer"+ i, this.observers.get(i)).start();
        }

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
        int num_plants = Integer.parseInt(args[0]);
        int num_predators = Integer.parseInt(args[1]);
        int num_preys  = Integer.parseInt(args[2]);
        double ratio_predators = Double.parseDouble(args[3]);
        double ratio_preys = Double.parseDouble(args[4]);
        double energy_expenditure_predators = Double.parseDouble(args[5]);
        double energy_expenditure_preys = Double.parseDouble(args[6]);


        SimInit init = new SimInit();
        init.setNumRuns(1); // works only in batch mode

        PredatorPreyModel model = new PredatorPreyModel(num_plants, num_predators, num_preys, ratio_predators, ratio_preys, energy_expenditure_predators,energy_expenditure_preys);
        init.loadModel(model, null, true);
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

    public AID getAID(Position agentPosition){

        for(Drawable drawable : this.elementsList){
            if(drawable instanceof AnimalAgent){
                AnimalAgent animalAgent = (AnimalAgent) drawable;
                if(animalAgent.getPosition().equals(agentPosition)){
                    return animalAgent.getAID();
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {

        return "Predator Prey Simulation";
    }

    public Space getSpace(){
        return space;
    }

    public double getEnergy_expenditure_predators() {
        return energy_expenditure_predators;
    }

    public double getEnergy_expenditure_preys() {
        return energy_expenditure_preys;
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