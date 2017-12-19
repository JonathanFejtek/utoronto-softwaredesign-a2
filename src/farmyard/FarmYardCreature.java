package farmyard;

/**
 * A FarmYardCreature item. A FarmYardCreature is an item that can walk around and move toward target FarmYardItems of
 * a specified type.
 */
public abstract class FarmYardCreature extends FarmYardItem {
    /**
     * Whether or not the creature is moving left or right.
     */
    //initial horizontal direction must correspond to the way the appearance is set (all animals start moving right)
    private boolean goingRight = true;

    /**
     * Whether or not the creature is moving up or down.
     */
    private boolean goingUp = Math.random() < 0.5;

    /**
     * Chance factor that the creature will change its X direction.
     */
    private double turnAroundFactorX;

    /**
     * Chance factor that the creature will change its Y direction.
     */
    private double turnAroundFactorY;

    /**
     * Whether or not this creature can walk vertically.
     */
    private boolean walksVertical = true;

    /**
     * Whether or not this creature can walk horizontally.
     */
    private boolean walksHorizontal = true;

    /**
     * The current target item of the this FarmYardCreature.
     */
    private FarmYardItem target = null;

    /**
     * The type of item creature will move toward.
     */
    private Class targetType;

    /**
     * The reversed appearance of this FarmYardCreature. How this creature appears when it is moving left.
     */
    private String reversedAppearance;

    /**
     * Constructs a new FarmYardCreature at specified (x,y) location.
     *
     * @param x X coordinate of location
     * @param y Y coordinate of location
     */
    public FarmYardCreature(int x, int y) {
        super(x, y);
    }

    /**
     * Returns the reverse of this creature's appearance (the way the creature appears when it is going left).
     *
     * @return The reversed appearance of this creature.
     */
    private String getReverseAppearance() {
        return reversedAppearance;
    }

    /**
     * Creates the reversed appearance of this FarmYardCreature.
     */
    private void buildReverseAppearance() {
        StringBuilder rev = new StringBuilder("");
        for (int i = this.getAppearance().length() - 1; i >= 0; i--) {
            char char_in_appearance = this.getAppearance().charAt(i);
            switch (char_in_appearance) {
                case '\\':
                    rev.append('/');
                    break;
                case '/':
                    rev.append('\\');
                    break;
                case ')':
                    rev.append('(');
                    break;
                case '(':
                    rev.append(')');
                    break;
                case '>':
                    rev.append('<');
                    break;
                case '<':
                    rev.append('>');
                    break;
                case '}':
                    rev.append('{');
                    break;
                case '{':
                    rev.append('}');
                    break;
                case '[':
                    rev.append(']');
                    break;
                case ']':
                    rev.append('[');
                    break;
                default:
                    rev.append(char_in_appearance);
                    break;
            }
        }

        this.reversedAppearance = rev.toString();
    }

    /**
     * Turns this creature around, causing it to reverse its horizontal direction.
     */
    private void turnAroundHorizontal() {
        goingRight = !goingRight;
        this.setAppearance(getReverseAppearance());
    }

    /**
     * Turns this creature around, causing it to reverse its vertical direction.
     */
    private void turnAroundVertical() {
        goingUp = !goingUp;
    }

    /**
     * Determines whether this creature should change its X direction.
     */
    private void randomChangeDirectionHorizontal() {
        if (Math.random() < turnAroundFactorX) {
            this.turnAroundHorizontal();
        }
    }

    /**
     * Determines whether this creature should change its Y direction.
     */
    private void randomChangeDirectionVertical() {
        if (Math.random() < turnAroundFactorY) {
            this.turnAroundVertical();
        }
    }

    /**
     * Causes this creature to walk in its current horizontal direction (left or right).
     */
    private void walkHorizontal() {
        if (goingRight) {
            this.moveRight();
        } else {
            this.moveLeft();
        }
    }

    /**
     * Causes this creature to walk in its current vertical direction (up or down).
     */
    private void walkVertical() {
        if (goingUp) {
            this.moveUp();
        } else {
            this.moveDown();
        }
    }

    /**
     * Causes this creature to move toward a specified FarmYardItem.
     *
     * @param item The item to move toward.
     */
    private void moveTowardItem(FarmYardItem item) {
        if (this.getPositionX() < item.getPositionX()) {
            this.moveRight();
        } else if (this.getPositionX() > item.getPositionX()) {
            this.moveLeft();
        }
        if (this.getPositionY() < item.getPositionY()) {
            this.moveUp();
        } else if (this.getPositionY() > item.getPositionY()) {
            this.moveDown();
        }
    }

    /**
     * Sets the target FarmYardItem that this creature will target and move toward.
     *
     * @param item Target FarmYardItem to move toward.
     */
    private void setTarget(FarmYardItem item) {
        this.target = item;
    }

    /**
     * Causes this creature to acquire/pick up specified FarmYardItem.
     *
     * @param item The FarmYardItem to acquire.
     */
    protected void acquireTargetItem(FarmYardItem item) {
        parentFarmyard.removeFarmYardItem(target);
        target = null;
    }

    /**
     * Sets the chance factor that the creature will change its horizontal direction.
     *
     * @param f The chance factor that the creature will will change its horizontal direction.
     */
    void setTurnAroundFactorX(double f) {
        this.turnAroundFactorX = f;
    }

    /**
     * Sets the chance factor that the creature will change its vertical direction.
     *
     * @param f The chance factor that the creature will will change its vertical direction.
     */
    void setTurnAroundFactorY(double f) {
        this.turnAroundFactorY = f;
    }

    /**
     * Sets the type of FarmYardItem that this FarmYardCreature will target and move toward
     *
     * @param targetType The type of item this FarmYardCreature will target.
     */
    void setTargetType(Class targetType) {
        this.targetType = targetType;
    }

    /**
     * Returns the current target FarmYardItem of this creature.
     *
     * @return The current target FarmYardItem of this creature.
     */
    FarmYardItem getTarget() {
        return this.target;
    }

    /**
     * Sets the (x,y) location of this FarmYardCreature if the (x,y) location is not occupied by another
     * FarmYardCreature
     *
     * @param x X coordinate of location
     * @param y Y coordinate of location.
     */
    @Override
    public void setLocation(int x, int y) {
        if (!parentFarmyard.typeIsAtLocation(x, y, FarmYardCreature.class)) {
            super.setLocation(x, y);
        }
    }

    /**
     * Moves this creature left
     */
    @Override
    void moveLeft() {
        // if going right, creature should face left
        if (goingRight) {
            this.turnAroundHorizontal();
        }
        super.moveLeft();
    }

    /**
     * Moves this creature right
     */
    @Override
    void moveRight() {
        // if going left, creature should face right
        if (!goingRight) {
            this.turnAroundHorizontal();
        }
        super.moveRight();
    }

    /**
     * Sets the String appearance of this FarmYardCreature
     *
     * @param appearance The String appearance of this FarmYardCreature.
     */
    @Override
    public void setAppearance(String appearance) {
        super.setAppearance(appearance);
        this.buildReverseAppearance();
    }

    /**
     * Causes this FarmYardCreature to move/take its turn in the game. A FarmYardCreature will seek out a target
     * FarmYardItem of a specified type and walk around according to its behavioral constraints.
     */
    @Override
    public void move() {
        //if there is no target, get closest item of targetType
        if (target == null) {
            setTarget(parentFarmyard.getClosestItemOfType(this, this.targetType));
        }

        //if there is a target item
        if (target != null) {

            //if it has already been picked up, there is no target
            if (!parentFarmyard.itemExists(target)) {
                target = null;
            }
            //if it is here, pick up item
            else if (isHere(target)) {
                acquireTargetItem(target);
            }

            //seek the target
            else {
                moveTowardItem(target);
            }
        } else {
            //if this creature is capable of walking vertically
            if (walksVertical) {
                this.randomChangeDirectionVertical();
                this.walkVertical();
            }
            //if this creature is capable of walking horizontally
            if (walksHorizontal) {
                this.randomChangeDirectionHorizontal();
                this.walkHorizontal();
            }
        }
    }

    /**
     * Sets whether this creature can walk vertically.
     *
     * @param walksVertical Whether this creature can walk vertically.
     */
    @SuppressWarnings("unused")
    public void setWalksVertical(boolean walksVertical) {
        this.walksVertical = walksVertical;
    }

    /**
     * Sets whether this creature can walk horizontally
     *
     * @param walksHorizontal Whether this creature can walk horizontally.
     */
    @SuppressWarnings("unused")
    public void setWalksHorizontal(boolean walksHorizontal) {
        this.walksHorizontal = walksHorizontal;
    }


}
