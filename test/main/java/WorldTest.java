import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

  @Test
  public void notCreatedCellsAreDead() {
    World world = new World();
    assertFalse(world.isLivingCell(1,2));
  }

  @Test
  public void createLivingCellTest() {
    World world = new World();
    assertFalse(world.isLivingCell(1,2));
    world.createLivingCell(1,2);
    assertTrue(world.isLivingCell(1,2));
  }

  @Test
  public void killCellTest() {
    World world = new World();
    assertFalse(world.isLivingCell(1,2));
    world.createLivingCell(1,2);
    assertTrue(world.isLivingCell(1,2));
    world.killCell(1,2);
    assertFalse(world.isLivingCell(1,2));
  }
}