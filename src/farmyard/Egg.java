package farmyard;

import javafx.scene.paint.Color;

/**
 * An Egg item. Eggs are created by Chickens and collected by Humans.
 */
public class Egg extends FarmYardItem {

    /**
     * Construct a new Egg at the specified (x,y) location.
     *
     * @param x the x coordinate of the Egg's (x,y) location.
     * @param y the y coordinate of the Egg's (x,y) location.
     */
    public Egg(int x, int y) {
        super(x, y);
    }

    /**
     * The setup method for this Egg item. Sets the appearance and color of an Egg.
     */
    @Override
    protected void setupItem() {
        this.setAppearance("O");
        this.setColor(Color.ROSYBROWN);
    }

}
