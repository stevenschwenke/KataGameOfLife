import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.map.LazyMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WorldTest {

  /**
   * This tests the world which is implemented using a LazyMap.
   */
  @Test
  public void lazyMapTest() {
    Factory<Boolean> factory1 = new Factory<Boolean>() {
      public Boolean create() {
        return Boolean.FALSE;
      }
    };
    Map<Integer, Boolean> innerMap = LazyMap.lazyMap(new HashMap<Integer, Boolean>(), factory1);

    Factory<LazyMap<Integer, Boolean>> factory2 = new Factory<LazyMap<Integer, Boolean>>() {
      @Override
      public LazyMap<Integer, Boolean> create() {
        return LazyMap.lazyMap(new HashMap<Integer, Boolean>(), factory1);
      }
    };
    Map<Integer, LazyMap<Integer, Boolean>> outerMap = LazyMap.lazyMap(new HashMap<Integer, LazyMap<Integer, Boolean>>(), factory2);

    assertFalse(innerMap.get(1));
    assertFalse(innerMap.get(500));

    assertFalse(outerMap.get(0).get(1));
    assertFalse(outerMap.get(1).get(500));
    assertFalse(outerMap.get(500).get(500));
  }

  @Test
  public void notCreatedCellsAreDead() {
    World world = new World();
    assertFalse(world.isLiving(1, 2));
  }

  @Test
  public void createTest() {
    World world = new World();
    assertFalse(world.isLiving(1, 2));
    world.create(1, 2);
    assertTrue(world.isLiving(1, 2));
  }

  @Test
  public void killTest() {
    World world = new World();
    assertFalse(world.isLiving(1, 2));
    world.create(1, 2);
    assertTrue(world.isLiving(1, 2));
    world.kill(1, 2);
    assertFalse(world.isLiving(1, 2));
  }

  @Test
  public void neighboursTest() {
    World world = new World();
    world.create(2, 2);
    assertEquals(0, world.neighbours(2, 2));
    world.create(2, 1); // north
    assertEquals(1, world.neighbours(2, 2));
    world.create(3, 1); // north-east
    assertEquals(2, world.neighbours(2, 2));
    world.create(3, 2); // east
    assertEquals(3, world.neighbours(2, 2));
    world.create(3, 3); // south-east
    assertEquals(4, world.neighbours(2, 2));
    world.create(2, 3); // south
    assertEquals(5, world.neighbours(2, 2));
    world.create(1, 3); // south-west
    assertEquals(6, world.neighbours(2, 2));
    world.create(1, 2); // west
    assertEquals(7, world.neighbours(2, 2));
    world.create(1, 1); // north-west
    assertEquals(8, world.neighbours(2, 2));
  }
}