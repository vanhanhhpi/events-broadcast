package hpilib.broadcast.events.core.implementaion;

import hpilib.broadcast.annotation.OmsEvents;
import hpilib.broadcast.events.core.EventsBuilder;
import hpilib.broadcast.portin.EventsImple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsBuilderImple implements EventsBuilder {

  // isbBroadCastAll = true thì bắn tới tất cả các events
  private boolean isBroadCastAll = false;

  // nếu isbBroadCastAll = false thì chỉ bắn đến các event eventsName được chỉ định | hoặc các eventname = null
  private List<String> eventsName = new ArrayList<>();
  private String packageName = null;

  public static EventsBuilderImple newBuilder() {
    return new EventsBuilderImple();
  }

  /**
   * isbBroadCastAll = true thì bắn tới tất cả các events
   * @param isBroadCastAll
   * @return
   */
  @Override
  public EventsBuilderImple withBroadCastAll(boolean isBroadCastAll) {
    this.isBroadCastAll = isBroadCastAll;
    return this;
  }

  /**
   * nếu isbBroadCastAll = false thì chỉ bắn đến các event eventsName được chỉ định | hoặc các eventname = null
   * @param concat false thì sẽ làm mới danh sách eventsname
   * @param eventName
   * @return
   */
  public EventsBuilderImple withEventName(boolean concat, String ...eventName) {
    if(!concat){
      this.eventsName = new ArrayList<String>();
    }
    if( eventName != null && eventName.length > 0){
      this.eventsName.addAll(Arrays.asList(eventName));
    }
    return this;
  }

  public EventsBuilderImple withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  @SafeVarargs
  public final <T extends EventsImple> EventsBuilderImple withEventName(boolean concat, Class<T>... eventName) {
    if(!concat){
      this.eventsName = new ArrayList<String>();
    }
    if( eventName != null && eventName.length > 0){
      for (var event : eventName) {
        this.eventsName.add(event.getClass().getAnnotation(OmsEvents.class).name());
      }
    }
    return this;
  }

  public EventsBroadCast build(){
    System.out.println("Build Events BroardCast");
    if(packageName != null){
      return new EventsBroadCast(this.isBroadCastAll, this.eventsName, this.packageName);
    }
    return new EventsBroadCast(this.isBroadCastAll, this.eventsName);
  }

  public static class EventResult{
    public boolean result;
    public String eventsName;
    public Class<?> eventClass;
  }
}
