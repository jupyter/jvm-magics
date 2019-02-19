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

public class Magics {
  public static MagicRegistration registration() {
    return MagicRegistration.INSTANCE;
  }

  public static void registerLineMagic(String name, LineMagic lineMagic) {
    registration().addLineMagic(name, lineMagic);
  }

  public static void registerCellMagic(String name, CellMagic cellMagic) {
    registration().addCellMagic(name, cellMagic);
  }

  public static void registerLineCellMagic(String name, CellMagic cellMagic) {
    registration().addLineCellMagic(name, cellMagic);
  }

  public static Object callLineMagic(String name, String line, Interpreter interp) {
    return registration().findLineMagic(name).call(line.trim(), interp);
  }

  public static Object callCellMagic(String name, String line, String cell, Interpreter interp) {
    return registration().findCellMagic(name).call(line.trim(), cell, interp);
  }
}
