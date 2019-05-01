package game.entities.sportsman;

import java.util.Observable;
import game.entities.MobileEntity;
import game.enums.Gender;
import utilities.ValidationUtils;

/**
 * Created by itzhak on 24-Mar-19.
 */

@SuppressWarnings("deprecation")

public abstract class Sportsman extends MobileEntity {
    /**
     * Important note:
     * Those fields (and more in this project) are currently final due to them not changing in HW2.
     * If in future tasks you will need to change them you could remove the final modifier and add a setter.
     */
    private final String name;
    private final double age;
    private final Gender gender;
    private String FinishedStatus;
    private double Friction;

    /**
     * Ctor
     * @param name
     * @param age
     * @param gender
     * @param acceleration
     * @param maxSpeed
     */
    public Sportsman(String name, double age, Gender gender, double acceleration, double maxSpeed) {
        super(0, acceleration,maxSpeed);
        ValidationUtils.assertNotNullOrEmptyString(name);
        ValidationUtils.assertPositive(age);
        ValidationUtils.assertNotNull(gender);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.FinishedStatus ="NO";
        this.Friction = 0;
    }

    //region Getters & setters

    /**
     *
     * @return
     */

    public double getFriction() {
        return Friction;
    }


    /**
     *
     * @param friction
     */
    public void setFriction(double friction) {
        Friction = friction;
    }

    /**
     * @return sportsman's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return sportsman's age
     */
    public double getAge() {
        return age;
    }

    /**
     * @return sportsman's gender
     */
    public Gender getGender() {
        return gender;
    }

    //endregion
}
