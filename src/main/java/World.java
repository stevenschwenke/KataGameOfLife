import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.map.LazyMap;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class World {

  private Map<Integer, LazyMap<Integer, Boolean>> world;

  public World() {
    Factory<Boolean> factory1 = () -> Boolean.FALSE;
    Factory<LazyMap<Integer, Boolean>> factory2 = () -> LazyMap.lazyMap(new HashMap<>(), factory1);
    world = LazyMap.lazyMap(new HashMap<>(), factory2);
  }

  public void create(int column, int row) {
    world.get(column).put(row, Boolean.TRUE);
  }

  public void kill(int column, int row) {
    world.get(column).put(row, Boolean.FALSE);
  }

  public boolean isLiving(int column, int row) {
    Boolean cellLiving = world.get(column).get(row);
    return cellLiving != null && cellLiving;
  }

  public int neighbours(int column, int row) {
    Map<Integer, Boolean> westColumn = world.get(column - 1);
    Map<Integer, Boolean> centerColumn = world.get(column);
    Map<Integer, Boolean> eastColumn = world.get(column + 1);

    BitSet bitSet = new BitSet();
    bitSet.set(1, centerColumn.get(row - 1));
    bitSet.set(2, eastColumn.get(row - 1));
    bitSet.set(3, eastColumn.get(row));
    bitSet.set(4, eastColumn.get(row + 1));
    bitSet.set(5, centerColumn.get(row + 1));
    bitSet.set(6, westColumn.get(row + 1));
    bitSet.set(7, westColumn.get(row));
    bitSet.set(8, westColumn.get(row - 1));
    return bitSet.cardinality();
  }
}
