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

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class TestRegistration {
  @After
  public void clear() {
    MagicRegistration.INSTANCE.clear();
  }

  @Test
  public void testLineRegistration() {
    LineMagic instance = (line, interp) -> Optional.empty();

    Magics.registerLineMagic("test", instance);

    Assert.assertSame("Should return the registered line magic object",
        instance, Magics.registration().findLineMagic("test"));
    Assert.assertNull("Should not have cell magic registered",
        Magics.registration().findCellMagic("test"));
  }

  @Test
  public void testCellRegistration() {
    CellMagic instance = (line, cell, interp) -> Optional.empty();

    Magics.registerCellMagic("test", instance);

    Assert.assertSame("Should return the registered cell magic object",
        instance, Magics.registration().findCellMagic("test"));
    Assert.assertNull("Should not have line magic registered",
        Magics.registration().findLineMagic("test"));
  }

  @Test
  public void testLineCellRegistration() {
    CellMagic instance = (line, cell, interp) -> Optional.empty();

    Magics.registerLineCellMagic("test", instance);

    Assert.assertSame("Should return the registered cell magic object",
            instance, Magics.registration().findCellMagic("test"));
    Assert.assertSame("Should return the registered object wrapped for for line magic",
            instance, ((MagicRegistration.CellAsLineMagic) Magics.registration().findLineMagic("test")).cellMagic);
  }
}
