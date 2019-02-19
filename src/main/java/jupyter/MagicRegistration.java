/*
 * Copyright 2017 Netflix
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jupyter;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles registration of {@link LineMagic} and {@link CellMagic} instances.
 * <p>
 * Callers should use the singleton instance of this class, which is available
 * from {@link Magics#registration()}.
 */
public class MagicRegistration {

  static final MagicRegistration INSTANCE = new MagicRegistration();

  private final Map<String, LineMagic> lineMagics = new HashMap<>();
  private final Map<String, CellMagic> cellMagics = new HashMap<>();

  /**
   * Add a function that can be called as line magic or as cell magic.
   * <p>
   * When called as line magic, the cell will be null.
   *
   * @param name a String name for the magic function
   * @param cellMagic a {@link CellMagic} instance
   */
  public void addLineCellMagic(String name, CellMagic cellMagic) {
    cellMagics.put(name, cellMagic);
    lineMagics.put(name, new CellAsLineMagic(cellMagic));
  }

  /**
   * Add a function that can be called (only) as line magic.
   *
   * @param name a String name for the magic function
   * @param lineMagic a {@link LineMagic} instance
   */
  public void addLineMagic(String name, LineMagic lineMagic) {
    lineMagics.put(name, lineMagic);
  }

  /**
   * Add a function that can be called (only) as cell magic.
   *
   * @param name a String name for the magic function
   * @param cellMagic a {@link CellMagic} instance
   */
  public void addCellMagic(String name, CellMagic cellMagic) {
    cellMagics.put(name, cellMagic);
  }

  /**
   * Find by name a magic function that can be called as line magic.
   *
   * @param name a String name
   * @return a {@link LineMagic} function
   */
  public LineMagic findLineMagic(String name) {
    return lineMagics.get(name);
  }

  /**
   * Find by name a magic function that can be called as cell magic.
   *
   * @param name a String name
   * @return a {@link CellMagic} function
   */
  public CellMagic findCellMagic(String name) {
    return cellMagics.get(name);
  }

  // Visible for testing
  void clear() {
    lineMagics.clear();
    cellMagics.clear();
  }

  private static class CellAsLineMagic implements LineMagic {
    private final CellMagic cellMagic;

    private CellAsLineMagic(CellMagic cellMagic) {
      this.cellMagic = cellMagic;
    }

    @Override
    public Object call(String line, Interpreter interp) {
      return cellMagic.call(line, null, interp);
    }
  }
}
