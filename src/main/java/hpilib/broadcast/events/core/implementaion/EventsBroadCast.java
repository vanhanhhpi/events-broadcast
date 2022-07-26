package hpilib.broadcast.events.core.implementaion;

import hpilib.broadcast.annotation.OmsEvents;
import hpilib.broadcast.calling.CaliingSubasaHelper;
import org.reflections.Reflections;
import hpilib.broadcast.portin.Events;
import hpilib.broadcast.portin.EventsImple;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EventsBroadCast implements Events {

  Reflections reflections = new Reflections("com.hpi.backend.adapter.persistence.events");
  private boolean isBroadCastAll = false;
  private List<String> eventsName = new ArrayList<>();
  public EventsBroadCast(boolean isBroadCastAll, List<String> eventsName){
    this.isBroadCastAll = isBroadCastAll;
    this.eventsName = eventsName;
  }

  public EventsBroadCast(boolean isBroadCastAll, List<String> eventsName, String packageName){
    this.isBroadCastAll = isBroadCastAll;
    this.eventsName = eventsName;
    reflections = new Reflections(packageName);
  }

  @Override
  public <T> List<EventsBuilderImple.EventResult> publish(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

    List<EventsBuilderImple.EventResult> rs = new ArrayList<>();
    Set<Class<?>> challengeClasses = reflections.getTypesAnnotatedWith(OmsEvents.class);
    challengeClasses = challengeClasses.stream().sorted(Comparator.comparingInt( x -> x.getAnnotation(OmsEvents.class).async() ? 1 : 0)).collect(Collectors.toSet());

    for (Class<?> clas : challengeClasses) {
      var amo = clas.getAnnotation(OmsEvents.class);
      if(isBroadCastAll){
        if(amo.async()){
          new RunAsync(amo.name(), () -> run(clas, object));
          rs.add(new EventsBuilderImple.EventResult(){{
            result = true;
            eventsName = amo.name();
            eventClass = clas;
          }});

        }else{
          rs.add(new EventsBuilderImple.EventResult(){{
            result = run(clas, object);
            eventsName = amo.name();
            eventClass = clas;
          }});
        }

      }else{
        if(!eventsName.isEmpty()){
          if(isNullOrEmpty(amo.name()) || (!isNullOrEmpty(amo.name()) && eventsName.contains(amo.name())) ){
            if(amo.async()){
              new RunAsync(amo.name(), () -> run(clas, object));
              rs.add(new EventsBuilderImple.EventResult(){{
                result = true;
                eventsName = amo.name();
                eventClass = clas;
              }});

            }else{

              var boolResult = run(clas, object);
              rs.add(new EventsBuilderImple.EventResult(){{
                result = boolResult;
                eventsName = amo.name();
                eventClass = clas;
              }});

            }
          }
        }
      }
    }
    return rs;
  }
  @Override
  public <T> List<EventsBuilderImple.EventResult> publishAsync(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

    List<EventsBuilderImple.EventResult> rs = new ArrayList<>();
    Set<Class<?>> challengeClasses = reflections.getTypesAnnotatedWith(OmsEvents.class);
    for (Class<?> clas : challengeClasses) {
      var amo = clas.getAnnotation(OmsEvents.class);
      if(isBroadCastAll){

        new RunAsync(amo.name(), () -> run(clas, object));

        rs.add(new EventsBuilderImple.EventResult(){{
          result = true;
          eventsName = amo.name();
          eventClass = clas;
        }});

      }else{
        if(!eventsName.isEmpty()){
          if(isNullOrEmpty(amo.name()) || (!isNullOrEmpty(amo.name()) && eventsName.contains(amo.name())) ){
            var boolResult = true;
            new RunAsync(amo.name(), () -> run(clas, object));
            rs.add(new EventsBuilderImple.EventResult(){{
              result = boolResult;
              eventsName = amo.name();
              eventClass = clas;
            }});
          }
        }
      }
    }
    return rs;
  }
  public <T> boolean run(Class<?> clas, T object){
    try {
      System.out.println("Runing Sync " + clas.getAnnotation(OmsEvents.class).name() );
      EventsImple tmp = (EventsImple) clas.getDeclaredConstructor().newInstance();
      return Boolean.TRUE.equals(CaliingSubasaHelper.CallFunction(() -> tmp.publish(object), 3, Boolean.class));
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return false;
  }

  private  boolean isNullOrEmpty(String vao) {
    return vao == null || vao.isBlank() || vao.isEmpty() || vao.trim().length() <= 0
            || vao.equalsIgnoreCase("null");
  }

  public static class RunAsync implements Runnable {
    Thread t;
    long sleep = 0;
    Supplier<?> func;
    public RunAsync(String evetnsName, Supplier<?> s) {
      t = new Thread(this, evetnsName + "_" + UUID.randomUUID());
      func = s;
      t.start();
    }

    @Override
    public void run() {
      try {
        if (func != null) {
          func.get();
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }

  }

}
