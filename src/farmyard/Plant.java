package farmyard;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A Plant. A Plant grows and produces a Crop when it is mature. A Plant is contained in a FarmersField.
 */
public class Plant extends FarmYardItem {
    /**
     * The age of this plant
     */
    private int age = 0;
    /**
     * the length of time in a plant's life stage
     */
    private int lifeStageLength;
    /**
     * Current stage of life the plant is in
     */
    private int stageOfLife = 0;
    /**
     * Whether or not this plant has produced a crop for its current life cycle.
     */
    private boolean hasProducedCrop = false;
    /**
     * A list of appearances the plant will have throughout its life. The plant will appear differently at each stage
     * of its life.
     */
    private ArrayList<String> lifeStageAppearances = new ArrayList<>();

    /**
     * Constructs a plant at the specified (x,y) location.
     *
     * @param x X coordinate of location.
     * @param y Y coordinate of location.
     */
    Plant(int x, int y) {
        super(x, y);
        addLifeStageAppearance("_");
        addLifeStageAppearance("~");
        addLifeStageAppearance("i");
        addLifeStageAppearance("%");
        //a plant goes into its next stage of life every 10 cycles of the game
        lifeStageLength = 10;
    }

    /**
     * The setup method for this plant. Sets the initial appearance and color of the Plant.
     */
    @Override
    protected void setupItem() {
        this.setAppearance("_");
        this.setColor(Color.GREEN.brighter());
    }

    /**
     * Adds another way the Plant will appear at a stage in its life. (Also adds another stage of life for the Plant)
     *
     * @param stageOfLifeAppearance A new way the Plant will appear during its life-cycle.
     */
    private void addLifeStageAppearance(String stageOfLifeAppearance) {
        lifeStageAppearances.add(stageOfLifeAppearance);
    }

    /**
     * Returns true if this plant is ready to yield a Crop.
     *
     * @return True if this plant is ready to harvest.
     */
    private boolean isReadyForHarvest() {
        return stageOfLife == lifeStageAppearances.size() - 1;
    }

    /**
     * Resets the plant so that it can grow again and yield a new crop.
     */
    void harvest() {
        this.age = 0;
        this.stageOfLife = 0;
        hasProducedCrop = false;
        this.setAppearance(lifeStageAppearances.get(stageOfLife));
    }

    /**
     * Causes this Plant to take its turn/move in the game. The Plant will age every turn it takes. When it reaches
     * maturity, it will yield a crop.
     */
    public void move() {
        age++;

        //if the plant has aged past the length of a life stage
        if (age % lifeStageLength == 0) {
            stageOfLife++;
            // catch cases where the plants age exceeds its number of life stages
            if (stageOfLife >= lifeStageAppearances.size()) {
                stageOfLife = lifeStageAppearances.size() - 1;
            }
            //set the plant's appearance for the appropriate stage of life
            this.setAppearance(lifeStageAppearances.get(stageOfLife));
        }

        if (isReadyForHarvest() && !hasProducedCrop) {
            produceCrop();
        }
    }

    /**
     * Causes this Plant to produce a Crop at its location.
     */
    private void produceCrop() {
        Crop c = new Crop(getPositionX(), getPositionY());
        c.setParentPlant(this);
        hasProducedCrop = true;
    }

}
