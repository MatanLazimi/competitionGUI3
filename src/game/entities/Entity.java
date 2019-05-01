package game.entities;

import java.util.Observable;
import utilities.Point;
import utilities.ValidationUtils;

import java.util.Observable;

/**
 * Created by itzhak on 24-Mar-19.
 */
@SuppressWarnings("deprecation")

public abstract class Entity extends Observable implements Runnable {
    private Point location;

    /**
     * Default ctor places entity at (0,0)
     */
    public Entity(){
        this(new Point());
    }

    /**
     * Ctor
     * @param location current location
     */
    public Entity(Point location) {
        this.location = location;
    }

    //region Getters & setters
    /**
     * @return the current location of the entity
     */
    public Point getLocation() {
        return location;
    }
    /**
     * @param location the new location of the entity
     * @throws IllegalArgumentException if argument is null
     */
    public void setLocation(Point location) {
        ValidationUtils.assertNotNull(location);
        this.location = location;
    }


}
