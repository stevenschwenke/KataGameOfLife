public class GameLogic {


  public static boolean isUnderpopulated(int livingNeighbours) {
    return livingNeighbours < 2;
  }

  public static boolean isSurviving(int livingNeighbours) {
    return livingNeighbours == 2 || livingNeighbours == 3;
  }

  public static boolean isOvercrowded(int livingNeighbours) {
    return livingNeighbours > 3;
  }

  public static boolean isReproducing(int livingNeighbours) {
    return livingNeighbours == 3;
  }
}
