import java.util.HashMap;
import java.util.Map;

public class World {

  private Map<Integer, Map<Integer, Boolean>> world = new HashMap<>();

  public void createLivingCell(int column, int row) {
    Map<Integer, Boolean> rowMap = world.get(column);
    if (rowMap == null) {
      // first living cell in this row
      rowMap = new HashMap<>();
      rowMap.put(row, Boolean.TRUE);
      world.put(column, rowMap);
    } else {
      rowMap.put(row, Boolean.TRUE);
    }
  }

  public void killCell(int column, int row) {
    Map<Integer, Boolean> rowMap = world.get(column);
    if (rowMap != null) {
      rowMap.put(row, Boolean.FALSE);
    }
  }

  public boolean isLivingCell(int column, int row) {
    Map<Integer, Boolean> rowMap = world.get(column);
    if (rowMap == null) {
      return false;
    }
    return rowMap.get(row);
  }
}
