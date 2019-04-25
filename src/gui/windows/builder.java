package gui.windows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arena.WinterArena;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;

public class builder {
   private static builder instance;

   public static builder getInstance() {
       if (instance == null) {
           instance = new builder();
       }
       return instance;
   }

   private ClassLoader classLoader;
   private Class<?> classObject;
   private Constructor<?> constructor;

   private builder() {
       classLoader = ClassLoader.getSystemClassLoader();
   }

   public WinterArena buildArena(double length, SnowSurface surface, WeatherCondition condition)
           throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
           IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       this.classObject = classLoader.loadClass("game.arena.WinterArena");
       this.constructor = classObject.getConstructor(double.class, SnowSurface.class, WeatherCondition.class);
       return (WinterArena) this.constructor.newInstance(length, surface, condition);

   }
/*
   public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration,
                           utilities.EnumContainer.Color color)
           throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
           IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       this.classObject = classLoader.loadClass(racerType);
       this.constructor = classObject.getConstructor(String.class, double.class, double.class,
               utilities.EnumContainer.Color.class);
       return (Racer) this.constructor.newInstance(name, maxSpeed, acceleration, color);
   }

   public Racer buildWheeledRacer(String racerType, String name, double maxSpeed, double acceleration, Color color,
                                  int numOfWheels) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
           InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       this.classObject = classLoader.loadClass(racerType);
       this.constructor = classObject.getConstructor(String.class, double.class, double.class,
               utilities.EnumContainer.Color.class, int.class);
       return (Racer) this.constructor.newInstance(name, maxSpeed, acceleration, color, numOfWheels);
   }

    }
    */
}
