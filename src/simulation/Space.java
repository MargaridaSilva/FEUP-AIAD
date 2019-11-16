package simulation;

import uchicago.src.sim.gui.Displayable;

public interface Space {

    public Displayable getDisplay();
    public int getXSize();
    public int getYSize();
}