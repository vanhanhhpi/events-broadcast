package hpilib.broadcast.portin;

import hpilib.broadcast.events.core.implementaion.EventsBuilderImple;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * ứng dụng nội bộ của HPI để broadcast eventt <br /><br />
 * các event để được broadcast cầ khai báo trong package `com.hpi.backend.adapter.persistence.events`
 * <br><br>
 * class events cần được khai báo annotaion @OmsEvents(name = "name")<br>
 * vả bắt buộc được implements từ `EventsImple`
 */
public interface Events {
  <T> List<EventsBuilderImple.EventResult> publish(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
  <T> List<EventsBuilderImple.EventResult> publishAsync(T object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
