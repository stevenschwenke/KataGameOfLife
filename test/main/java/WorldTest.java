import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

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