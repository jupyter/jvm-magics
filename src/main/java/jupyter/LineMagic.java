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
 * Represents a line magic function.
 * <p>
 * Line magics are called by name when a line starts with a single %. For example:
 * <pre>
 *   %sql SELECT * FROM users
 * </pre>
 * <p>
 * The line passed to line magic is always trimmed to remove whitespace.
 */
public interface LineMagic {
  /**
   * Invokes the line magic function.
   *
   * @param line a String line from the magic invocation, always trimmed to remove whitespace
   * @param interp an {@link Interpreter} used to create side-effects
   * @return the result of this magic function call
   */
  Object call(String line, Interpreter interp);
}
