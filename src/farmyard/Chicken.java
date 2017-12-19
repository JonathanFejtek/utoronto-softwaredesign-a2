package farmyard;

import javafx.scene.paint.Color;

/**
 * A Chicken item. A Chicken will target AnimalFood, poop and lay eggs.
 */
public class Chicken extends FarmYardAnimal {

    /**
     * Constructs a new Chicken item at the specified (x,y) location.
     *
     * @param x the x coordinate of the Chicken's (x,y) location.
     * @param y the y coordinate of the Chicken's (x,y) location.
     */
    public Chicken(int x, int y) {
        super(x, y);
    }

    /**
     * The setup method for this Chicken item. Sets the appearance, color, movement behaviour and target type for a
     * Chicken. Also sets the appearance of this Chicken's poop.
     */
    @Override
    protected void setupItem() {
        this.setAppearance("(:|>");
        this.setColor(Color.RED);
        this.setTurnAroundFactorX(0.12);
        this.setTurnAroundFactorY(0.15);
        this.setTargetType(AnimalFood.class);
        this.setPoopAppearance(".");
    }

    /**
     * Causes this chicken to lay an egg. There is only a chance that a chicken will lay an egg when this method is
     * called.
     */
    private void layEgg() {
        //lay egg 10% of time
        if (Math.random() < 0.05) {
            new Egg(this.getPositionX(), this.getPositionY());
        }
    }

    /**
     * Causes this Chicken to move/take its turn in the game. A Chicken will occasionally lay an egg on its turn.
     */
    @Override
    public void move() {
        super.move();
        layEgg();
    }
}
