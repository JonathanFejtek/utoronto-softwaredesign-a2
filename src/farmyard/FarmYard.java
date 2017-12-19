package farmyard;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A FarmYard. Stores FarmYardItems and their locations. Provides access to FarmYardItems and their positions for
 * movement and rendering.
 */
public class FarmYard {
    /**
     * 3d array to store location of game objects. The z dimension corresponds to a list of multiple FarmYardItems that
     * may occupy an (x,y) position in the array.
     */
    private ArrayList<FarmYardItem>[][] farmYardItems;
    /**
     * Field to store FarmYardItems by type.
     */
    private HashMap<Class, ArrayList<FarmYardItem>> itemMap;
    /**
     * Static Singleton instance of FarmYard for "global" access.
     */
    private static FarmYard instance;
    /**
     * The width of a cell in the FarmYard array.
     */
    private float cellWidth;
    /**
     * The height of a cell in the FarmYard array.
     */
    private float cellHeight;
    /**
     * The number of width divisions or columns in the FarmYard array.
     */
    private int widthDivisions;
    /**
     * The number of height divisions or rows in the FarmYard array.
     */
    private int heightDivisions;

    /**
     * Constructs a new FarmYard of a given width, height and width and height divisions. The width and height of a
     * given cell/square in a FarmYard is determined by the ratio of width to width divisions, and height to height
     * divisions.
     *
     * @param w          The width of the FarmYard.
     * @param h          The height of the FarmYard.
     * @param wDivisions The number of divisions along the width of the FarmYard. (Equivalent to number of columns)
     * @param hDivisions The number of divisions along the height of the FarmYard. (Equivalent to number of rows)
     */
    @SuppressWarnings("unchecked")
    private FarmYard(int w, int h, int wDivisions, int hDivisions) {
        this.widthDivisions = wDivisions;
        this.heightDivisions = hDivisions;
        // can't technically create a generic array, so we have unchecked annotation
        this.farmYardItems = new ArrayList[wDivisions][hDivisions];

        this.cellWidth = w / wDivisions;
        this.cellHeight = h / hDivisions;
        itemMap = new HashMap<>();

        //initialize array
        for (int x = 0; x < wDivisions; x++) {
            for (int y = 0; y < hDivisions; y++) {
                // variable list per position in array
                this.farmYardItems[x][y] = new ArrayList<>();
            }
        }
    }

    /**
     * Returns all items of specified type that are in the FarmYard
     *
     * @param itemType The specific subclass of FarmYardItem
     * @return An ArrayList of FarmYardItems of specified subclass.
     */
    private ArrayList<FarmYardItem> getItemsOfType(Class itemType) {
        return itemMap.get(itemType);
    }

    /**
     * For a set of FarmYardItems, returns the closest item to a specified item.
     *
     * @param item       Item to check against set.
     * @param setToCheck Set of items to check.
     * @return The FarmYardItem from the set that is closest to the specified item.
     */
    private FarmYardItem getClosestItemInSet(FarmYardItem item, ArrayList<FarmYardItem> setToCheck) {
        if (!setToCheck.isEmpty()) {
            FarmYardItem closest = setToCheck.get(0);

            for (FarmYardItem fyi : setToCheck) {
                //if this item is closer and not the self-same item
                if ((distanceBetweenItems(item, fyi) < distanceBetweenItems(item, closest)) && (fyi != item)) {
                    closest = fyi;
                }
            }
            return closest;
        } else {
            return null;
        }
    }

    /**
     * Gets the distance between two FarmYardItems
     *
     * @param item1 First item
     * @param item2 Second item
     * @return The distance between the first and second item.
     */
    private double distanceBetweenItems(FarmYardItem item1, FarmYardItem item2) {
        double x1 = item1.getPositionX();
        double x2 = item2.getPositionX();
        double y1 = item1.getPositionY();
        double y2 = item2.getPositionY();

        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    /**
     * Gets all the FarmYardObjects that occupy a specified (x,y) position.
     *
     * @param x X coordinate of position.
     * @param y Y coordinate of position.
     * @return An ArrayList of items at the specified location.
     */
    private ArrayList<FarmYardItem> getItemsAtLocation(int x, int y) {
        return this.farmYardItems[x][y];
    }

    /**
     * Returns the width of a FarmYard cell.
     *
     * @return The width of a cell in the FarmYard.
     */
    float getCellWidth() {
        return this.cellWidth;
    }

    /**
     * Returns the height of a FarmYard cell.
     *
     * @return The height of a cell in the FarmYard.
     */
    float getCellHeight() {
        return this.cellHeight;
    }

    /**
     * Adds an item to the FarmYard
     *
     * @param item The item to add to the FarmYard.
     */
    void addFarmYardItem(FarmYardItem item) {
        int item_x = item.getPositionX();
        int item_y = item.getPositionY();
        if (isInsideFarmYard(item_x, item_y)) {
            Class itemClass = item.getClass();
            //if no entry for this item type
            if (!itemMap.containsKey(itemClass)) {
                itemMap.put(itemClass, new ArrayList<>());
            }

            itemMap.get(itemClass).add(item);

            //add to 3d array

            this.farmYardItems[item_x][item_y].add(item);
        }
    }

    /**
     * Removes an item from the FarmYard
     *
     * @param item The item to remove from the FarmYard
     */
    void removeFarmYardItem(FarmYardItem item) {
        Class item_class = item.getClass();

        if (itemMap.containsKey(item_class)) {
            ArrayList<FarmYardItem> item_set = itemMap.get(item_class);
            if (item_set.contains(item)) {
                //remove both from 3d array and map
                resetItemLocation(item);
                item_set.remove(item);
            }
        }
    }

    /**
     * Updates the position of a FarmYardItem in the FarmYard. After a FarmYardItem moves, this method is called so
     * that the FarmYard stores the new location of the item.
     *
     * @param item The item to update.
     */
    void updateItemLocation(FarmYardItem item) {
        int item_x = item.getPositionX();
        int item_y = item.getPositionY();
        this.farmYardItems[item_x][item_y].add(item);
    }

    /**
     * Resets the position of a FarmYardItem in the FarmYard. When a FarmYardItem moves, this method is called so that
     * the FarmYard removes the old stored location of the item.
     *
     * @param item The item to reset.
     */
    void resetItemLocation(FarmYardItem item) {
        int item_x = item.getPositionX();
        int item_y = item.getPositionY();

        ArrayList<FarmYardItem> farm_yard_cell = this.farmYardItems[item_x][item_y];
        if (farm_yard_cell.contains(item)) {
            farm_yard_cell.remove(item);
        }
    }

    /**
     * Returns true if the specified type of FarmYardItem is at the cursor (x,y) location
     *
     * @param x        The x coordinate of position to check.
     * @param y        The y coordinate of position to check.
     * @param itemType The type of item to check for.
     * @return True iff the specified type of FarmYardItem is at the cursor (x,y) location.
     */
    boolean typeIsAtLocation(int x, int y, Class itemType) {
        //if location is inside of FarmYard area
        if (isInsideFarmYard(x, y)) {
            ArrayList<FarmYardItem> items_at_location = getItemsAtLocation(x, y);
            for (FarmYardItem item : items_at_location) {
                // if item corresponds to specified class
                if (itemType.isInstance(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if this FarmYard is storing a FarmYardItem on the 'play field'.
     *
     * @param item Item to check.
     * @return True iff item exists
     */
    boolean itemExists(FarmYardItem item) {
        int itemX = item.getPositionX();
        int itemY = item.getPositionY();
        //if this item exists, it should be stored here and return true
        return (getItemsAtLocation(itemX, itemY).contains(item));
    }

    /**
     * Returns true if the specified (x,y) location is contained with the play area specified by this FarmYard.
     *
     * @param x X coordinate of location to check.
     * @param y Y coordinate of location to check.
     * @return True iff (x,y) location is within the play area specified by this FarmYard.
     */
    boolean isInsideFarmYard(int x, int y) {
        return (x >= 0 && x < widthDivisions) && (y >= 0 && y < heightDivisions);
    }

    /**
     * For a given item, returns the nearest/closest (distance-wise) item of a specified type.
     *
     * @param item     Item to check against.
     * @param itemType Type of item to check for.
     * @return The closest item of specified type.
     */
    @SuppressWarnings("unchecked")
    FarmYardItem getClosestItemOfType(FarmYardItem item, Class itemType) {
        //all items of specified type in this FarmYard
        ArrayList itemsToCheck = this.getItemsOfType(itemType);

        if (itemsToCheck != null) {
            return this.getClosestItemInSet(item, itemsToCheck);
        } else {
            return null;
        }
    }

    /**
     * Creates a static Singleton instance of FarmYard.
     *
     * @param w          The width of the FarmYard.
     * @param h          The height of the FarmYard.
     * @param wDivisions The number of divisions along the width of the FarmYard. (Equivalent to number of columns)
     * @param hDivisions The number of divisions along the height of the FarmYard. (Equivalent to number of rows)
     */
    public static void createInstance(int w, int h, int wDivisions, int hDivisions) {
        instance = new FarmYard(w, h, wDivisions, hDivisions);
    }

    /**
     * Returns the static Singleton instance of FarmYard
     *
     * @return A static Singleton instance of FarmYard.
     */
    public static FarmYard getInstance() {
        return instance;
    }

    /**
     * Draw all FarmYard items in the FarmYard to specified GraphicsContext
     *
     * @param gc GraphicsContext to draw to.
     */
    public void drawToGraphicsContext(GraphicsContext gc) {
        for (ArrayList<FarmYardItem> itemSet : this.itemMap.values()) {
            for (FarmYardItem item : itemSet) {
                item.draw(gc);
            }
        }
        //display total eggs collected by Humans
        Human.displayEggsCollected(gc);
    }

    /**
     * Updates the game. This method moves every FarmYardItem that it stores.
     */
    public void updateGame() {
        //to avoid concurrent modification, avoid 'for each' syntax despite IntelliJ's concerns.
        List<ArrayList<FarmYardItem>> all_items = new ArrayList<>(this.itemMap.values());
        for (int j = 0; j < all_items.size(); j++) {
            ArrayList<FarmYardItem> itemSet = all_items.get(j);
            for (int i = 0; i < itemSet.size(); i++) {
                FarmYardItem item = itemSet.get(i);
                item.move();
            }
        }
    }

}
