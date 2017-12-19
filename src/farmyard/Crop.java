package farmyard;

import javafx.scene.paint.Color;

/**
 * A Crop. Is produced by a Plant. Is picked up by Humans to be used as AnimalFood.
 */
public class Crop extends FarmYardItem {
    /**
     * The Plant that produced this Crop.
     */
    private Plant parentPlant;

    /**
     * Constructs a crop at specified (x,y) location.
     *
     * @param x X coordinate of location
     * @param y Y coordinate of location
     */
    Crop(int x, int y) {
        super(x, y);
    }

    /**
     * The setup method for this Crop. Sets the
     */
    @Override
    protected void setupItem() {
        this.setAppearance("#");
        this.setColor(Color.YELLOWGREEN);
    }

    /**
     * Sets the Plant that produced this Crop.
     *
     * @param plant The plant that produced this Crop.
     */
    void setParentPlant(Plant plant) {
        parentPlant = plant;
    }

    /**
     * Harvests the Plant that produced this Crop. Is called when this Crop is acquired by a Human.
     */
    void harvestParentPlant() {
        parentPlant.harvest();
    }


}
