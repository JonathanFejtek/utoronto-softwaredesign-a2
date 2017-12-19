package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A FarmYardItem. A FarmYardItem is a basic game object that stores and edits an item's location, appearance, color.
 */
public abstract class FarmYardItem {
    /**
     * The String appearance of the item on the screen
     */
    private String appearance;
    /**
     * The color of the item
     */
    private Color itemColor;
    /**
     * The x position of the the item
     */
    private int xPosition;
    /**
     * The y position of the item
     */
    private int yPosition;

    /**
     * The FarmYard Singleton instance that stores this FarmYardItem
     */
    // all items get global access to the game area and each other
    FarmYard parentFarmyard = FarmYard.getInstance();

    /**
     * The width of the item when rendered to the screen
     */
    // the width of an item is relative to its parent FarmYard
    private float itemWidth = parentFarmyard.getCellWidth();
    /**
     * the height of the item when rendered to the screen
     */
    // the height of an item is relative to its parent FarmYard
    private float itemHeight = parentFarmyard.getCellHeight();

    /**
     * Construct a farmyard item at specified (x,y) location.
     *
     * @param x X coordinate of location
     * @param y Y coordinate of location
     */
    public FarmYardItem(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        //setupItem should be called in the constructor for every descendant of FarmYardItem
        setupItem();
        //add item to FarmYard instance
        parentFarmyard.addFarmYardItem(this);

    }

    /**
     * Sets up the FarmYardItem. This method is called every time a FarmYardItem is constructed and must be implemented
     * in subclasses of FarmYardItem to define details of an item's behavior and appearance.
     */
    protected abstract void setupItem();

    /**
     * Sets the String appearance of the item on the screen.
     *
     * @param appearance The String appearance of the item.
     */
    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    String getAppearance() {
        return this.appearance;
    }

    /**
     * Sets the (x,y) location of the item if the specified coordinates are inside the play area.
     *
     * @param x X coordinate of location.
     * @param y Y coordinate of location.
     */
    public void setLocation(int x, int y) {
        if (parentFarmyard.isInsideFarmYard(x, y)) {
            parentFarmyard.resetItemLocation(this);
            this.xPosition = x;
            this.yPosition = y;
            parentFarmyard.updateItemLocation(this);
        }
    }

    /**
     * Sets the color of this FarmYardItem
     *
     * @param color The new color of this FarmYardItem.
     */
    void setColor(Color color) {
        this.itemColor = color;
    }

    /**
     * Draws a String at a specified (x,y) location to a graphics context.
     *
     * @param graphicsContext Graphics context to draw to.
     * @param string          String to draw.
     * @param x               X coordinate of location.
     * @param y               Y coordinate of location.
     */
    private void drawString(GraphicsContext graphicsContext, String string, int x, int y) {
        graphicsContext.setFill(itemColor);
        graphicsContext.fillText(string, ((x + 1) * this.itemWidth), ((y + 1) * this.itemHeight));
    }

    /**
     * Draws this FarmYardItem to a graphics context.
     *
     * @param graphicsContext Graphics context to draw to.
     */
    void draw(GraphicsContext graphicsContext) {
        drawString(graphicsContext, appearance, xPosition, yPosition);
    }

    /**
     * Returns the x coordinate of this FarmYardItem's position.
     *
     * @return The X coordinate of this FarmYardItem's position.
     */
    int getPositionX() {
        return xPosition;
    }

    /**
     * Returns the y coordinate of this FarmYardItem's position.
     *
     * @return The Y coordinate of this FarmYardItem's position.
     */
    int getPositionY() {
        return yPosition;
    }

    /**
     * Moves this FarmYardItem up one unit. (down on the screen)
     */
    void moveUp() {
        this.setLocation(this.xPosition, this.yPosition + 1);
    }

    /**
     * Moves this FarmYardItem down one unit. (up on the screen)
     */
    void moveDown() {
        this.setLocation(this.xPosition, this.yPosition - 1);
    }

    /**
     * Moves this FarmYardItem left one unit.
     */
    void moveLeft() {
        this.setLocation(this.xPosition - 1, this.yPosition);
    }

    /**
     * Moves this FarmYardItem right one unit.
     */
    void moveRight() {
        this.setLocation(this.xPosition + 1, this.yPosition);
    }

    /**
     * Returns true iff the provided item occupies the same location as this FarmYardItem
     *
     * @param item Item to check for.
     * @return True iff the specified item occupies the same location as this FarmYardItem.
     */
    boolean isHere(FarmYardItem item) {
        //this FarmYardItem shares the same (x,y) position as specified item
        return (this.getPositionX() == item.getPositionX() && this.getPositionY() == item.getPositionY());
    }

    /**
     * This FarmYardItem takes its turn/moves in the game. Empty non-abstract method by default.
     */
    public void move() {
        // empty method : every FarmYardItem should have a move method, which does not necessarily need to overwritten
        // or implemented
    }


}
