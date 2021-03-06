package gui.windows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arena.WinterArena;

import game.competition.WinterCompetition;
import game.entities.sportsman.WinterSportsman;
import game.enums.*;

public class builder_singelton {
    private static builder_singelton instance;

    public static builder_singelton getInstance() {
        if (instance == null) {
            instance = new builder_singelton();
        }
        return instance;
    }

    private ClassLoader classLoader;
    private Class<?> classObject;
    private Constructor<?> constructor;

    private builder_singelton() {
        classLoader = ClassLoader.getSystemClassLoader();
    }

    protected WinterArena buildArena(double length, SnowSurface surface, WeatherCondition condition)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.classObject = classLoader.loadClass("game.arena.WinterArena");
        this.constructor = classObject.getConstructor(double.class, SnowSurface.class, WeatherCondition.class);
        return (WinterArena) this.constructor.newInstance(length, surface, condition);

    }

    protected WinterCompetition buildCompetition(WinterArena winterArena, int maxCompetitors,
                                              Discipline discipline, League league, Gender gender, String typePath)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.classObject = classLoader.loadClass(typePath);
        this.constructor = classObject.getConstructor(WinterArena.class, int.class, Discipline.class,
                League.class, Gender.class);
        return (WinterCompetition) this.constructor.newInstance(winterArena, maxCompetitors,
                discipline, league, gender);
    }

    protected WinterSportsman buildCompetitor(String name, double age, Gender competitor_gender, double acceleration,
                                              double maxSpeed, Discipline competitor_discipline,
                                              String competitorPath) throws ClassNotFoundException,
                                           NoSuchMethodException, SecurityException, InstantiationException,
                                           IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.classObject = classLoader.loadClass(competitorPath);
        this.constructor = classObject.getConstructor(String.class, double.class, Gender.class, double.class, double.class,
                                                      Discipline.class);
        return (WinterSportsman) this.constructor.newInstance(name, age, competitor_gender, acceleration, maxSpeed,
                competitor_discipline);
    }

    }


