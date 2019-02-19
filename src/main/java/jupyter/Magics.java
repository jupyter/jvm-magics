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

import java.util.Optional;

public class Magics {

  /**
   * @return the JVM singleton registration instance.
   */
  public static MagicRegistration registration() {
    return MagicRegistration.INSTANCE;
  }

  /**
   * Registers a function as line magic.
   * <p>
   * Line magic functions are called from a line that starts with a single %. When called, the
   * invocation line is passed to the function with whitespace trimmed.
   *
   * @param name a String name for the magic function
   * @param lineMagic a {@link LineMagic} implementation
   */
  public static void registerLineMagic(String name, LineMagic lineMagic) {
    registration().addLineMagic(name, lineMagic);
  }

  /**
   * Registers a function as cell magic.
   * <p>
   * Cell magic functions are called from a cell that starts with a double %. When called, the cell
   * is passed unchanged to the function and the invocation line is passed to the function with
   * whitespace trimmed.
   *
   * @param name a String name for the magic function
   * @param cellMagic a {@link CellMagic} implementation
   */
  public static void registerCellMagic(String name, CellMagic cellMagic) {
    registration().addCellMagic(name, cellMagic);
  }

  /**
   * Registers a function as both line and cell magic.
   * <p>
   * Line/Cell magic functions are called either as line magic or as cell magic. When called as
   * line magic, the cell is set to null. When called as cell magic, the cell is passed unchanged.
   * The invocation line is always passed to the function with whitespace trimmed.
   *
   * @param name a String name for the magic function
   * @param cellMagic a {@link CellMagic} implementation
   */
  public static void registerLineCellMagic(String name, CellMagic cellMagic) {
    registration().addLineCellMagic(name, cellMagic);
  }

  /**
   * Find and call a registered function as line magic.
   *
   * @param name the line magic name
   * @param line the invocation line
   * @param interp an interpreter for creating side-effects
   * @return the result of the magic invocation, or Optional.empty if there is no result
   */
  public static Optional<Object> callLineMagic(String name, String line, Interpreter interp) {
    LineMagic magic = registration().findLineMagic(name);
    if (magic != null) {
      return magic.call(line.trim(), interp);
    }

    throw new MagicNotFoundException("Unknown line magic function: %s", name);
  }

  /**
   * Find and call a registered function as cell magic.
   *
   * @param name the cell magic name
   * @param line the invocation line
   * @param cell the cell
   * @param interp an interpreter for creating side-effects
   * @return the result of the magic invocation, or Optional.empty if there is no result
   */
  public static Optional<Object> callCellMagic(String name, String line, String cell, Interpreter interp) {
    CellMagic magic = registration().findCellMagic(name);
    if (magic != null) {
      return magic.call(line.trim(), cell, interp);
    }

    throw new MagicNotFoundException("Unknown cell magic function: %s", name);
  }
}
