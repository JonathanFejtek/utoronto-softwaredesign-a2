package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A Human. A Human is a FarmYardCreature that moves toward and acquires Eggs. A Human also spreads AnimalFood. A
 * Human will also harvest Crops for AnimalFood.
 */

public class Human extends FarmYardCreature {

    /**
     * The number of all eggs collected by all Humans.
     */
    private static int totalEggsCollected = 0;

    /**
     * The amount of animal feed this human has.
     */
    private int animalFeed = 0;

    /**
     * Constructs a new Human at specified (x,y) location.
     *
     * @param x X coordinate of location.
     * @param y Y coordinate of location.
     */
    public Human(int x, int y) {
        super(x, y);
    }

    /**
     * Setup method for a Human. Sets the appearance, color, and movement behaviour of a Human.
     */
    @Override
    protected void setupItem() {
        this.setAppearance("-A-");
        this.setColor(Color.SANDYBROWN.darker());
        this.setTurnAroundFactorX(0.1);
        this.setTurnAroundFactorY(0.2);
        // humans will seek out eggs
        this.setTargetType(Egg.class);
    }

    /**
     * Causes this Human to acquire a specified FarmYardItem (typically an Egg).
     *
     * @param item The FarmYardItem to acquire.
     */
    @Override
    protected void acquireTargetItem(FarmYardItem item) {
        if (item instanceof Egg) {
            super.acquireTargetItem(item);
            totalEggsCollected++;
        } else if (item instanceof Crop) {
            animalFeed++;
            ((Crop) item).harvestParentPlant();

            super.acquireTargetItem(item);
        }
    }

    /**
     * Causes human to drop down 4 piece s of food all around.
     */
    private void dropFood() {
        if (Math.random() < 0.2) {
            animalFeed--;
            int x = this.getPositionX();
            int y = this.getPositionY();
            new AnimalFood(x - 1, y - 1);
            new AnimalFood(x - 1, y + 1);
            new AnimalFood(x + 1, y - 1);
            new AnimalFood(x + 1, y + 1);
        }
    }

    /**
     * Displays the total number of Eggs collected by all instances of Human to a specified graphics context.
     *
     * @param graphicsContext The graphics context to draw to.
     */
    static void displayEggsCollected(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText("Eggs: " + totalEggsCollected, 2 * 10, 2 * 6);
    }

    /**
     * Causes this Human to move/take its turn in the game. A Human will seek Eggs to collect and occasionally drop
     * AnimalFood if it hasn't targeted an Egg. If the Human is out of animalFeed, it will target Crops until it has
     * replenished its feed stocks.
     */
    @Override
    public void move() {
        if (animalFeed == 0) {
            this.setTargetType(Crop.class);
        } else if (animalFeed == 20) {
            this.setTargetType(Egg.class);
        }
        super.move();
        //if not seeking a target and has animalFeed
        if (this.getTarget() == null && animalFeed > 0) {
            dropFood();
        }
    }
}
