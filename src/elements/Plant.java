package elements;

import launchers.EnvironmentLauncher;
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
public class Plant{

    protected Position position;
    public DefaultDrawableNode node;
    private EnvironmentLauncher model;

    protected Plant(EnvironmentLauncher model, String id, Position position) {
        this.model = model;
        this.position = position;
        this.createNode(position, id);
    }

    public static Plant generatePlant(EnvironmentLauncher model, String id, Position position) {
        return new Plant(model, id, position);
    }

    public void createNode(Position position, String label) {
        int density = model.getBoardDensity();
        RoundRectNetworkItem rect = new RoundRectNetworkItem(position.x*density, position.y*density);
        rect.allowResizing(false);
        rect.setHeight(density/2);
        rect.setWidth(density/2);

        Color color = Configs.PLANT_COLOR;
        DefaultDrawableNode node = new DefaultDrawableNode(label, rect);
        node.setColor(color);
        this.node = node;
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

    /*@Override
    public void draw(SimGraphics simGraphics) {
        simGraphics.setDrawingCoordinates(getX(), getY(), 0);
        //scaling the circles
        //simGraphics.setDrawingParameters(10, 10, 1);
        simGraphics.drawCircle(this.color);
        //simGraphics.fillPolygon(this.color);
    }*/

    public void setNode(DefaultDrawableNode node) {
        this.node = node;
    }

	public static PreyAgent generatePlants(EnvironmentLauncher environmentLauncher, String id, Position preyPosition,
			Gender gender) {
		return null;
	}

    

    
}