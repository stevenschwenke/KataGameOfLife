import org.junit.Test;

import static org.junit.Assert.*;

public class GameLogicTest {

  @Test
  public void underpopulationTest() {
    assertTrue(GameLogic.isUnderpopulated(0));
    assertTrue(GameLogic.isUnderpopulated(1));
    assertFalse(GameLogic.isUnderpopulated(2));
    assertFalse(GameLogic.isUnderpopulated(3));
    assertFalse(GameLogic.isUnderpopulated(4));
    assertFalse(GameLogic.isUnderpopulated(5));
    assertFalse(GameLogic.isUnderpopulated(6));
    assertFalse(GameLogic.isUnderpopulated(7));
    assertFalse(GameLogic.isUnderpopulated(8));
  }

  @Test
  public void surviveTest() {
    assertFalse(GameLogic.isSurviving(0));
    assertFalse(GameLogic.isSurviving(1));
    assertTrue(GameLogic.isSurviving(2));
    assertTrue(GameLogic.isSurviving(3));
    assertFalse(GameLogic.isSurviving(4));
    assertFalse(GameLogic.isSurviving(5));
    assertFalse(GameLogic.isSurviving(6));
    assertFalse(GameLogic.isSurviving(7));
    assertFalse(GameLogic.isSurviving(8));
  }

  @Test
  public void overcrowdingTest() {
    assertFalse(GameLogic.isOvercrowded(0));
    assertFalse(GameLogic.isOvercrowded(1));
    assertFalse(GameLogic.isOvercrowded(2));
    assertFalse(GameLogic.isOvercrowded(3));
    assertTrue(GameLogic.isOvercrowded(4));
    assertTrue(GameLogic.isOvercrowded(5));
    assertTrue(GameLogic.isOvercrowded(6));
    assertTrue(GameLogic.isOvercrowded(7));
    assertTrue(GameLogic.isOvercrowded(8));
  }

  @Test
  public void reproductionTest() {
    assertFalse(GameLogic.isReproducing(0));
    assertFalse(GameLogic.isReproducing(1));
    assertFalse(GameLogic.isReproducing(2));
    assertTrue(GameLogic.isReproducing(3));
    assertFalse(GameLogic.isReproducing(4));
    assertFalse(GameLogic.isReproducing(5));
    assertFalse(GameLogic.isReproducing(6));
    assertFalse(GameLogic.isReproducing(7));
    assertFalse(GameLogic.isReproducing(8));
  }
}