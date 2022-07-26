package hpilib.broadcast.portin;

import java.lang.reflect.InvocationTargetException;

public interface EventsImple {
  <T> boolean publish(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

}
