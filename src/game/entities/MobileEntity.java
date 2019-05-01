package game.entities;

import utilities.Point;

/**
 * Created by itzhak on 07-Mar-19.
 */

public class MobileEntity extends Entity implements IMobileEntity{
    private final double maxSpeed;
    private final double acceleration;

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    private double speed;

    /**
     * Ctor for a mobile entity in the game
     * @param initialSpeed initial speed of the entity
     * @param acceleration entity acceleration
     * @param maxSpeed entity maximum speed
     */
    public MobileEntity( double initialSpeed,double acceleration, double maxSpeed){
        this.setSpeed(initialSpeed);
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
    }


    /**
     * @see IMobileEntity#move(double)
     */
    @Override
    public void move(double friction) {
        this.setSpeed(Math.min(this.maxSpeed,this.speed + this.getAcceleration()* (1-friction)));
        Point newLocation = this.getLocation().offset(0,this.speed);
        this.setLocation(newLocation);
    }


    /**
     * Note: speed can theoretically be negative
     * @param speed the current speed of the entity
     */
    private void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     * @return the acceleration of the entity
     */
    protected double getAcceleration() {
        return acceleration;
    }



}
