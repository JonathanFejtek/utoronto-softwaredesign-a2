package farmyard;

/**
 * A FarmYardAnimal item. A FarmYardAnimal is a FarmYardCreature that can both eat AnimalFood and poop (create AnimalManure).
 */
public abstract class FarmYardAnimal extends FarmYardCreature {
    /**
     * The amount of the AnimalFood that this animal has eaten.
     */
    private int food_eaten = 0;
    /**
     * Appearance of this FarmYardAnimal's poop.
     */
    private String poopAppearance;

    /**
     * Constructs a new FarmYardAnimal at specified (x,y) location.
     *
     * @param x X coordinate of the location
     * @param y Y coordinate of the location
     */
    public FarmYardAnimal(int x, int y) {
        super(x, y);
    }

    /**
     * Causes this FarmYardAnimal to create a AnimalManure at its current location. The FarmYardAnimal will only poop
     * if it has eaten food.
     */
    private void defecate() {
        if (food_eaten > 0 && Math.random() < 0.2) {
            AnimalManure poop = new AnimalManure(this.getPositionX(), this.getPositionY());
            if (poopAppearance != null) {
                poop.setAppearance(poopAppearance);
            }
            food_eaten--;
        }
    }

    /**
     * Causes this FarmYardAnimal to pick up/acquire specified target FarmYardItem. A FarmYardAnimal will acquire (eat)
     * AnimalFood.
     *
     * @param item Item to pick up/acquire
     */
    @Override
    protected void acquireTargetItem(FarmYardItem item) {
        super.acquireTargetItem(item);
        food_eaten++;
    }

    /**
     * Sets the appearance of this FarmYardAnimal's poop.
     *
     * @param appearance The new String appearance of this FarmYardAnimal's poop.
     */
    void setPoopAppearance(String appearance) {
        this.poopAppearance = appearance;
    }

    /**
     * Causes this FarmYardAnimal to move/take its turn in the game. A Chicken will occasionally defecate on its turn.
     */
    @Override
    public void move() {
        super.move();
        defecate();
    }

}
