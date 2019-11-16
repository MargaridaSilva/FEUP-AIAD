package elements;

import simulation.PredatorPreyModel;
import simulation.Space;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.RoundRectNetworkItem;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.network.DefaultDrawableNode;
import utils.Configs;
import utils.Position;

import java.awt.*;

import agents.PreyAgent;
import agents.AnimalAgent.Gender;

/**
 * A class to represent an Animal agent
 */
public class Plant implements Drawable{

    protected Position position;
    private PredatorPreyModel model;
    private Space space;

    protected Plant(PredatorPreyModel model, Space space, String id, Position position) {
        this.model = model;
        this.space = space;
        this.position = position;
    }

    public static Plant generatePlant(PredatorPreyModel model, Space space, String id, Position position) {
        return new Plant(model, space, id, position);
    }


    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Position getPosition() {
        return position;
    }

    public void setX(int value) {
        this.position.x = value;
    }

    public void setY(int value) {
        this.position.y = value;
    }

    public void setPosition(Position position) {
        this.position = position.clone();
    }
    
    @Override
    public void draw(SimGraphics g) {
        g.drawFastRoundRect(Configs.PLANT_COLOR);
    }
    
}