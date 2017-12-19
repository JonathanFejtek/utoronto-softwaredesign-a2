package farmyard;

import javafx.scene.paint.Color;

/**
 * An AnimalFood item. FarmYardAnimals will target and eat this food.
 */

public class AnimalFood extends FarmYardItem {

    /**
     * Constructs a new AnimalFood item at the specified (x,y) location.
     *
     * @param x the x coordinate of the AnimalFood's (x,y) location.
     * @param y the y coordinate of the AnimalFood's (x,y) location.
     */
    public AnimalFood(int x, int y) {
        super(x, y);
    }

    /**
     * The setup method for an AnimalFood item. Sets the color and appearance of an AnimalFood.
     */
    @Override
    protected void setupItem() {
        this.setColor(Color.GRAY.darker().darker().darker());
        this.setAppearance(".:.");
    }

    /**
     * Causes this AnimalFood item to move/take its turn in the game.
     */
    @Override
    public void move() {
        int blownX = this.getPositionX() + Wind.getWind()[0];
        int blownY = this.getPositionY() + Wind.getWind()[1];
        this.setLocation(blownX, blownY);
    }

}
