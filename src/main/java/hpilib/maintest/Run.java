package hpilib.maintest;

import hpilib.broadcast.events.core.EventsBuilder;

import java.lang.reflect.InvocationTargetException;

public class Run {

  public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    EventsBuilder.newBuilder()
            .withPackageName("hpilib.maintest")
//            .withEventName(true, "hihi")
            .withBroadCastAll(true).build()
//            .publishAsync("hihi");
            .publish("hihi");

    System.out.println("oki");
  }

}
