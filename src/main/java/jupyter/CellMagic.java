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

/**
 * Represents a cell or a line-and-cell magic function.
 * <p>
 * When registered using {@link Magics#registerCellMagic(String, CellMagic)}, a cell magic function
 * can only be called using a line that starts with two % characters. For example:
 * <pre>
 *   %%sql -t
 *   SELECT *
 *   FROM users
 *   LIMIT 100
 * </pre>
 * <p>
 * The invocation line is always passed to cell magic and is trimmed to remove whitespace. When
 * called as cell magic, the cell is passed unchanged.
 * <p>
 * When registered using {@link Magics#registerLineCellMagic(String, CellMagic)}, a cell magic
 * function can be called either as line magic or as cell magic. When called as line magic, the
 * cell text is set to null.
 */
public interface CellMagic {
  /**
   * Invokes the cell magic function.
   *
   * @param line a String line from the magic invocation, always trimmed to remove whitespace
   * @param cell the String cell from the magic invocation, or null when called as line magic
   * @param interp an {@link Interpreter} used to create side-effects
   * @return the result of this magic function call
   */
  Object call(String line, String cell, Interpreter interp);
}
