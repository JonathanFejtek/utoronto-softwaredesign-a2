package farmyard;

import javafx.scene.paint.Color;

public class Pig extends FarmYardAnimal {

    /**
     * Constructs a Pig at specified (x,y) location.
     *
     * @param x X coordinate of location.
     * @param y Y coordinate of location.
     */
    public Pig(int x, int y) {
        super(x, y);
    }

    /**
     * Setup method for a Pig. Sets a Pig's color, appearance, poop appearance and movement behaviour.
     */
    @Override
    protected void setupItem() {
        this.setColor(Color.PINK.darker().darker().darker());
        this.setAppearance(":(8)");
        this.setTurnAroundFactorX(0.17);
        this.setTurnAroundFactorY(0.14);
        this.setPoopAppearance("*");
        //set the type of FarmYardItem that this pig will move toward
        this.setTargetType(AnimalFood.class);
    }
}
