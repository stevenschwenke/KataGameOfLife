import java.util.HashMap;
import java.util.Map;

public class World {

  private Map<Integer, Map<Integer, Boolean>> world = new HashMap<>();

  public void create(int column, int row) {
    Map<Integer, Boolean> columnMap = world.get(column);
    if (columnMap == null) {
      // first living cell in this column
      columnMap = new HashMap<>();
      columnMap.put(row, Boolean.TRUE);
      world.put(column, columnMap);
    } else {
      columnMap.put(row, Boolean.TRUE);
    }
  }

  public void kill(int column, int row) {
    Map<Integer, Boolean> columnMap = world.get(column);
    if (columnMap != null) {
      columnMap.put(row, Boolean.FALSE);
    }
  }

  public boolean isLiving(int column, int row) {
    Map<Integer, Boolean> columnMap = world.get(column);
    if (columnMap == null) {
      return false;
    }
    Boolean cellLiving = columnMap.get(row);
    return cellLiving != null && cellLiving;
  }

  public int neighbours(int column, int row) {
    int neighbours = 0;
    Map<Integer, Boolean> westColumn = world.get(column-1);
    if(westColumn != null) {
      Boolean northWest = westColumn.get(row - 1);
      Boolean west = westColumn.get(row);
      Boolean southWest = westColumn.get(row + 1);

      if(northWest != null && northWest)
        neighbours ++;
      if(west != null && west)
        neighbours ++;
      if(southWest != null && southWest)
        neighbours ++;
    }

    Map<Integer, Boolean> centerColumn = world.get(column);
    if(centerColumn != null) {
      Boolean north = centerColumn.get(row - 1);
      Boolean south = centerColumn.get(row + 1);

      if(north != null && north)
        neighbours ++;
      if(south != null && south)
        neighbours ++;
    }

    Map<Integer, Boolean> eastColumn = world.get(column+1);
    if(eastColumn != null) {
      Boolean northEast = eastColumn.get(row - 1);
      Boolean east = eastColumn.get(row);
      Boolean southEast = eastColumn.get(row + 1);

      if(northEast != null && northEast)
        neighbours ++;
      if(east != null && east)
        neighbours ++;
      if(southEast != null && southEast)
        neighbours ++;
    }
    return neighbours;
  }
}
