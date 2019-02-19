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

import java.util.Map;
import java.util.Optional;

/**
 * Interpreter interface passed to {@link LineMagic} and {@link CellMagic}.
 * <p>
 * This interface is used by magics for side-effects. For example, a SQL magic function may run
 * multiple statements and call {@link #display(Object)} to show the results of intermediate
 * statements. Or a SQL magic function may also store the result of the final statement in a
 * variable.
 */
public interface Interpreter {
  /**
   * Called to display an object.
   * <p>
   * The kernel implementation is responsible for converting the result to a MIME type map.
   *
   * @param result an Object to display
   */
  void display(Object result);

  /**
   * Called to display an object using a MIME type map.
   * <p>
   * The result has already been converted to a map from MIME type to representation.
   *
   * @param mimeResults a map from MIME type to representation
   */
  void display(Map<String, String> mimeResults);

  /**
   * Sets a variable in the interpreter.
   *
   * @param name a variable name
   * @param value a value
   */
  void setVariable(String name, Object value);

  /**
   * Gets a variable from the interpreter.
   *
   * @param name a variable name
   * @return the value of the variable, or Optional.empty if the variable is not set
   */
  Optional<Object> getVariable(String name);

  /**
   * Runs a cell of code in the interpreter.
   * <p>
   * This method can be used to implement wrapper utilities, like %%timer, %%background, and
   * %%suppress_exceptions, that pass a block of code back to the interpreter as-is. This may also
   * be used when the interpreter is known, as in magic functions provided by the interpreter.
   * <p>
   * Note that there is no guarantee about how this method is implemented and how the cell is
   * interpreted because interpreters calling magic functions may use different languages.
   *
   * @param cellText cell text for the interpreter to run, in the language of the interpreter
   * @return the result object produced by the code block, or Optional.empty if there is no result
   */
  Optional<Object> interpret(String cellText);
}
