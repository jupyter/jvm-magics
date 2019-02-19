package jupyter;

public class MagicNotFoundException extends RuntimeException {
  public MagicNotFoundException(String template, Object... args) {
    super(String.format(template, args));
  }
}
