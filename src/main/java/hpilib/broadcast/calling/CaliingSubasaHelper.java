package hpilib.broadcast.calling;

import javax.annotation.Nullable;
import java.util.List;

public class CaliingSubasaHelper {

  /**
   * @param <T>
   * @param action      action sẽ thực hiện
   * @param numberRetry số lần retry
   * @param className   class name của result
   * @return
   * @throws Exception
   */
  public static <T> T CallFunction(Function<T> action, int numberRetry, Class<T> className) throws Exception {
    if (numberRetry < 0) {
      throw new Exception("CallFunction: retry cannot < 0!");
    }
    Throwable exG = null;
    for (int i = 0; i <= numberRetry; i++) {
      try {
        var tmp = action.apply();
        return tmp;
      } catch (Throwable e) {
        exG = e;
      }
    }
    if (exG != null){
      exG.printStackTrace();
    }
    return null;
  }

}
