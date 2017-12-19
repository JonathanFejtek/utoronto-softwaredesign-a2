package farmyard;

import javafx.scene.paint.Color;

/**
 * An AnimalManure item. Animals will create this item after they eat AnimalFood.
 */
public class AnimalManure extends FarmYardItem {


    /**
     * Constructs a new AnimalManure item at the specified (x,y) location.
     *
     * @param x the x coordinate of the AnimalManure's (x,y) location.
     * @param y the y coordinate of the AnimalManure's (x,y) location.
     */
    public AnimalManure(int x, int y) {
        super(x, y);
    }

    /**
     * The setup method for an AnimalManure item. Sets the appearance and color of an AnimalManure.
     */
    @Override
    protected void setupItem() {
        this.setColor(Color.BLACK.darker().darker().darker());
        this.setAppearance(".");
    }
}
