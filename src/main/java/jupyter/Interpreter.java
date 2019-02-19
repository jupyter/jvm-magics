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

/**
 * Interpreter interface passed to {@link LineMagic} and {@link CellMagic}.
 */
public interface Interpreter {
  void display(Map<String, String>   mimeResults);

  void setVariable(String name, Object value);

  Object getVariable(String name);

  Object interpret(String cellText);
}
