package simulation;

import uchicago.src.sim.gui.Displayable;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.space.Object2DGrid;

public class PredatorPreySpace implements Space {

    private Object2DGrid space;
    private int xSize, ySize;

    public PredatorPreySpace(PredatorPreyModel model, int xSize, int ySize) {
        space = new Object2DGrid(xSize, ySize);
        this.xSize = xSize;
        this.ySize = ySize;
    }

    /**
     * Returns the Displayable appropriate for this space. In this case, this
     * returns an Object2DDisplay.
     */
    @Override
    public Displayable getDisplay() {
        return new Object2DDisplay(space);
    }

    @Override
    public int getXSize() {
        
        return ySize;
    }

    @Override
    public int getYSize() {
        
        return xSize;
    }
}