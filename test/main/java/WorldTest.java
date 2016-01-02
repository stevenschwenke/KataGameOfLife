import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

  @Test
  public void notCreatedCellsAreDead() {
    World world = new World();
    assertFalse(world.isLivingCell(1, 2));
  }

  @Test
  public void createLivingCellTest() {
    World world = new World();
    assertFalse(world.isLivingCell(1, 2));
    world.createLivingCell(1, 2);
    assertTrue(world.isLivingCell(1, 2));
  }

  @Test
  public void killCellTest() {
    World world = new World();
    assertFalse(world.isLivingCell(1, 2));
    world.createLivingCell(1, 2);
    assertTrue(world.isLivingCell(1, 2));
    world.killCell(1, 2);
    assertFalse(world.isLivingCell(1, 2));
  }

  @Test
  public void getNeighboursTest() {
    World world = new World();
    world.createLivingCell(2,2);
    assertEquals(0, world.neighboursFrom(2,2));
    world.createLivingCell(2,1); // north
    assertEquals(1, world.neighboursFrom(2,2));
    world.createLivingCell(3,1); // north-east
    assertEquals(2, world.neighboursFrom(2,2));
    world.createLivingCell(3,2); // east
    assertEquals(3, world.neighboursFrom(2,2));
    world.createLivingCell(3,3); // south-east
    assertEquals(4, world.neighboursFrom(2,2));
    world.createLivingCell(2,3); // south
    assertEquals(5, world.neighboursFrom(2,2));
    world.createLivingCell(1,3); // south-west
    assertEquals(6, world.neighboursFrom(2,2));
    world.createLivingCell(1,2); // west
    assertEquals(7, world.neighboursFrom(2,2));
    world.createLivingCell(1,1); // north-west
    assertEquals(8, world.neighboursFrom(2,2));
  }
}