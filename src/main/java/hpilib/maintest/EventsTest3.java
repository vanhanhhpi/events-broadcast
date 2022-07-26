package hpilib.maintest;

import hpilib.broadcast.annotation.OmsEvents;
import hpilib.broadcast.portin.EventsImple;

import java.lang.reflect.InvocationTargetException;

@OmsEvents( name = "hihi")
public class EventsTest3 implements EventsImple {
  @Override
  public <T> boolean publish(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


    System.out.println("EventsTest3 RUNING");

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return false;
  }
}
