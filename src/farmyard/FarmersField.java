package farmyard;

/**
 * A FarmersField. Is used to initialize an array of Plants
 */
public class FarmersField {
    /**
     * Construct a FarmersField at the specified (x,y) location with the specified width and height.
     *
     * @param xPosition The X coordinate of the top left corner of the farmers field
     * @param yPosition the Y coordinate of the top left corner of the farmers field
     * @param width     The width of field.
     * @param height    The height of the field.
     */
    public FarmersField(int xPosition, int yPosition, int width, int height) {

        for (int x = xPosition; x < xPosition + width; x += 2) {
            for (int y = yPosition; y < yPosition + height; y += 2) {
                new Plant(x, y);
            }
        }
    }
}
