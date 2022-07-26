package hpilib.broadcast.calling;

@FunctionalInterface
public interface Function<R> {
  R apply() throws Throwable;
}
