package hpilib.maintest;

import hpilib.broadcast.annotation.OmsEvents;
import hpilib.broadcast.portin.EventsImple;

import java.lang.reflect.InvocationTargetException;

@OmsEvents( name = "hihi")
public class EventsTest implements EventsImple {
  @Override
  public <T> boolean publish(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


    System.out.println("EventsTest RUNING");

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return false;
  }
}
