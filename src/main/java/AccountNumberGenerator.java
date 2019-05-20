import java.util.concurrent.atomic.AtomicLong;

public class AccountNumberGenerator {
  private static AtomicLong number = new AtomicLong(0);

  public static String generate() {
    return Long.toString(number.getAndIncrement());
  }
}
