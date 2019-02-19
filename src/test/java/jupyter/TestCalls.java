package jupyter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

public class TestCalls {
  private static final Interpreter THROWING_INTERP = new UnsupportedEverythingInterpreter();

  @After
  public void clear() {
    Magics.registration().clear();
  }

  @Test
  public void testLineMagic() {
    Magics.registerLineMagic("test", (line, interp) -> Optional.of(line));

    Optional<Object> result = Magics.callLineMagic("test", "line", THROWING_INTERP);

    Assert.assertTrue(result.isPresent());
    Assert.assertEquals(result.get(), "line");
  }

  @Test(expected = MagicNotFoundException.class)
  public void testLineIsNotCellMagic() {
    Magics.registerLineMagic("test", (line, interp) -> Optional.of(line));

    // should fail because there is no cell magic registered as "test"
    Magics.callCellMagic("test", "line", "cell", THROWING_INTERP);
  }

  @Test
  public void testCellMagic() {
    Magics.registerCellMagic("test", (line, cell, interp) -> Optional.of(line + "/" + cell));

    Optional<Object> result = Magics.callCellMagic("test", "line", "cell", THROWING_INTERP);

    Assert.assertTrue(result.isPresent());
    Assert.assertEquals(result.get(), "line/cell");
  }

  @Test(expected = MagicNotFoundException.class)
  public void testCellIsNotLineMagic() {
    Magics.registerCellMagic("test", (line, cell, interp) -> Optional.of(line + "/" + cell));

    // should fail because there is no line magic registered as "test"
    Magics.callLineMagic("test", "line", THROWING_INTERP);
  }

  @Test
  public void testLineCellMagic() {
    Magics.registerLineCellMagic("test", (line, cell, interp) ->
        Optional.of(line + "/" + (cell != null ? cell : "(null)")));

    Optional<Object> lineResult = Magics.callLineMagic("test", "line", THROWING_INTERP);

    Assert.assertTrue(lineResult.isPresent());
    Assert.assertEquals(lineResult.get(), "line/(null)");

    Optional<Object> cellResult = Magics.callCellMagic("test", "line", "cell", THROWING_INTERP);

    Assert.assertTrue(cellResult.isPresent());
    Assert.assertEquals(cellResult.get(), "line/cell");
  }

  @Test
  public void testLineTrimming() {
    Magics.registerLineCellMagic("test", (line, cell, interp) -> Optional.of(line));

    Optional<Object> lineResult = Magics.callLineMagic("test", " line ", THROWING_INTERP);

    Assert.assertTrue(lineResult.isPresent());
    Assert.assertEquals(lineResult.get(), "line");

    Optional<Object> cellResult = Magics.callCellMagic("test", " line\t", "cell", THROWING_INTERP);

    Assert.assertTrue(cellResult.isPresent());
    Assert.assertEquals(cellResult.get(), "line");
  }

  @Test
  public void testCellIsUnmodified() {
    Magics.registerLineCellMagic("test", (line, cell, interp) -> Optional.ofNullable(cell));

    Optional<Object> lineResult = Magics.callLineMagic("test", "line", THROWING_INTERP);

    Assert.assertFalse(lineResult.isPresent());

    Optional<Object> cellResult = Magics.callCellMagic(
        "test", "line", "\n\n\tcell\n\n", THROWING_INTERP);

    Assert.assertTrue(cellResult.isPresent());
    Assert.assertEquals(cellResult.get(), "\n\n\tcell\n\n");
  }

  private static class UnsupportedEverythingInterpreter implements Interpreter {
    @Override
    public void display(Object result) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void display(Map<String, String> mimeResults) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setVariable(String name, Object value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Object> getVariable(String name) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Object> interpret(String cellText) {
      throw new UnsupportedOperationException();
    }
  }

}
