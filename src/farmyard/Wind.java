package farmyard;

/**
 * Wind class for providing information about wind direction
 */
public class Wind {

    /**
     * Current wind 'vector'
     */
    private static int[] windVector = new int[]{0, 0};
    /**
     * Last non-zero factor for the vertical direction of the wind
     */
    private static int lastVertical = 1;
    /**
     * git
     * Last non-zero factor for the horizontal direction of the wind
     */
    private static int lastHorizontal = -1;

    /**
     * For a given wind factor and a vertical or horizontal plane, compute the new wind factor for that direction.
     *
     * @param windFactor The wind_factor for a given plane (-1 : blowing backward, 0 : not blowing,
     *                   1 : blowing forward)
     * @param plane      Which plane the wind factor corresponds to (Vertical or Horizontal plane)
     * @return The new wind factor for the given plane.
     */
    private static int getWindDirection(int windFactor, String plane) {
        //if the wind is blowing
        if (windFactor != 0) {
            // less than 1% chance that wind will change direction if it is blowing
            if (Math.random() < 0.01) {
                windFactor *= -1;
                //store new non-zero factor for direction
                storeLastBlown(windFactor, plane);
                return windFactor;
            }
            // 40% chance that the wind will keep blowing in its current direction
            else if (Math.random() < 0.4) {
                return windFactor;
            }
            // otherwise, the wind stops blowing
            else {
                return 0;
            }
        }
        // the wind is not blowing
        else {
            // 30% chance it will blow in the direction it was last blowing before it died
            if (Math.random() < 0.3) {
                windFactor = getLastBlownForDirection(plane);
                return windFactor;
            }
            // 5% chance it will blow in opposite direction
            else if (Math.random() < 0.05) {
                windFactor = -1 * getLastBlownForDirection(plane);
                //store new non-zero factor for direction
                storeLastBlown(windFactor, plane);
                return windFactor;
            } else {
                return 0;
            }
        }
    }

    /**
     * Sets the last non-zero factor for the given plane
     *
     * @param windFactor The wind_factor for a given plane (-1 : blowing backward, 0 : not blowing,
     *                   1 : blowing forward)
     * @param plane      Which plane the wind factor corresponds to (Vertical or Horizontal plane)
     */
    private static void storeLastBlown(int windFactor, String plane) {
        if (windFactor != 0) {
            switch (plane) {
                case "Vertical":
                    lastVertical = windFactor;
                    break;

                case "Horizontal":
                    lastHorizontal = windFactor;
                    break;
            }
        }
    }

    /**
     * Gets the last non-zero wind factor for the given plane.
     *
     * @param plane Which plane the wind factor corresponds to (Vertical or Horizontal plane).
     * @return The last non-zero wind factor for the given plane.
     */
    private static int getLastBlownForDirection(String plane) {
        switch (plane) {
            case "Vertical":
                return lastVertical;

            case "Horizontal":
                return lastHorizontal;
        }
        return 0;
    }

    /**
     * Returns the current wind vector
     *
     * @return The wind factors for both the horizontal and vertical planes.
     */
    static int[] getWind() {
        return windVector;
    }

    /**
     * Updates the wind vector
     */
    public static void updateWind() {
        //for this current wind vector, compute the new wind vector
        int wind_x = getWindDirection(windVector[0], "Horizontal");
        int wind_y = getWindDirection(windVector[1], "Vertical");

        windVector[0] = wind_x;
        windVector[1] = wind_y;

    }


}
