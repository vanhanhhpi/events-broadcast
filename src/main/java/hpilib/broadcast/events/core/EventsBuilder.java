package hpilib.broadcast.events.core;

import hpilib.broadcast.events.core.implementaion.EventsBroadCast;
import hpilib.broadcast.events.core.implementaion.EventsBuilderImple;
import hpilib.broadcast.portin.EventsImple;

/**
 * ứng dụng nội bộ của HPI để broadcast eventt <br /><br />
 * các event để được broadcast cầ khai báo trong package `com.hpi.backend.adapter.persistence.events`
 * <br><br>
 * class events cần được khai báo annotaion @OmsEvents(name = "name")<br>
 * vả bắt buộc được implements từ `EventsImple`
 */
public interface EventsBuilder  {

  /**
   * khởi tạo instance
   * @return
   */
  static EventsBuilderImple newBuilder(){return new EventsBuilderImple();}

  /**
   * có broad tới tất cả events không?
   * @param isBroadCastAll
   * @return
   */
  EventsBuilderImple withBroadCastAll(boolean isBroadCastAll);

  /**
   * khởi tạo broad tới các event được chỉ định<br >
   * Hàm này chỉ có ý nghĩa khi isBroadCastAll được set = false
   * @param concat
   * @param eventName
   * @return
   */
  EventsBuilderImple withEventName(boolean concat, String ...eventName);

  /**
   * set package chưa events
   * @param packageName
   * @return
   */
  EventsBuilderImple withPackageName(String packageName);

  /**
   * chỉ định events theo Class<?> <br />
   * Hàm này chưa được khuyến kích sử dụng trong phiên bản hiện tại (0.2.x)
   * @param concat
   * @param eventName
   * @param <T>
   * @return
   */
  <T extends EventsImple> EventsBuilderImple withEventName(boolean concat, Class<T> ...eventName);

  /**
   * build EventsBroadCast
   * @return
   */
  EventsBroadCast build();
}
