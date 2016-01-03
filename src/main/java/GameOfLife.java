import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GameOfLife extends Application {

  public static final int COLUMNS = 20;
  public static final int ROWS = 20;
  private World world = new World();

  private Map<Integer, Map<Integer, Button>> buttonMap = new HashMap<>();

  @Override
  public void start(Stage stage) throws Exception {
    GridPane gridPane = new GridPane();
    BorderPane borderPane = new BorderPane(gridPane);
    Scene scene = new Scene(borderPane, 500, 500);

    createControlButtons(borderPane);
    initializeGameField(gridPane);

    stage.setTitle("Game of Life");
    stage.setScene(scene);
    stage.show();
  }

  private void createControlButtons(BorderPane borderPane) {
    Button nextRoundButton = new Button("Next round");
    nextRoundButton.setOnAction(e -> computeNextRound());
    Button playButton = new Button("Play");
    playButton.setOnAction(e -> {
      ExecutorService executor = Executors.newFixedThreadPool(1);
      if("Play".equals(playButton.getText())) {
        playButton.setText("Stop");
      } else {
        playButton.setText("Play");
      }

      Callable<Object> myCallable = () -> {
          while (playButton.getText().equals("Stop")) {
              Thread.sleep(700);
              GameOfLife.this.computeNextRound();
          }
          return null;
        };
        executor.submit(myCallable);
    });
    borderPane.setTop(new HBox(3d, nextRoundButton, playButton));
  }

  private void initializeGameField(GridPane gridPane) {
    for (int column = 0; column <= COLUMNS; column++) {
      for (int row = 0; row <= ROWS; row++) {
        Button button = makeGamefieldButton(column, row);
        gridPane.add(button, column, row);
        if (buttonMap.get(column) != null) {
          buttonMap.get(column).put(row, button);
        } else {
          HashMap<Integer, Button> columnMap = new HashMap<>();
          columnMap.put(row, button);
          buttonMap.put(column, columnMap);
        }
      }
    }
  }

  private Button makeGamefieldButton(int column, int row) {
    Button button = new Button();
    final int finalRow = row;
    final int finalColumn = column;
    button.setOnAction(e -> {
      System.out.println(finalColumn + ", " + finalRow);
      boolean living = world.isLiving(finalColumn, finalRow);
      if (living) {
        world.kill(finalColumn, finalRow);
        button.setStyle(null);
      } else {
        world.create(finalColumn, finalRow);
        button.setStyle("-fx-background-color: #3635ff");
      }

    });
    button.setContextMenu(createContextMenu(column, row));
    return button;
  }

  private ContextMenu createContextMenu(int column, int row) {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem blinker = new MenuItem("Blinker");
    blinker.setOnAction(e -> {
      world.create(column, row);
      displayCellAsLiving(column, row);
      world.create(column, row+1);
      displayCellAsLiving(column, row+1);
      world.create(column, row+2);
      displayCellAsLiving(column, row+2);
    });

    MenuItem glider = new MenuItem("Glider");
    glider.setOnAction(e -> {
      world.create(column +1, row);
      displayCellAsLiving(column+1, row);
      world.create(column+2, row+1);
      displayCellAsLiving(column+2, row+1);
      world.create(column, row+2);
      displayCellAsLiving(column, row+2);
      world.create(column+1, row+2);
      displayCellAsLiving(column+1, row+2);
      world.create(column+2, row+2);
      displayCellAsLiving(column+2, row+2);
    });
    contextMenu.getItems().addAll(blinker, glider);
    return contextMenu;
  }

  private void computeNextRound() {

    List<Cell> cellsToKill = new ArrayList<>();
    List<Cell> cellsToCreate = new ArrayList<>();

    for (int column = 0; column <= COLUMNS; column++) {
      for (int row = 0; row <= ROWS; row++) {
        boolean living = world.isLiving(column, row);
        int neighbours = world.neighbours(column, row);

        if (living && (GameLogic.isOvercrowded(neighbours) || GameLogic
            .isUnderpopulated(neighbours))) {
          cellsToKill.add(new Cell(column,row));
          Button button = buttonMap.get(column).get(row);
          button.setStyle(null);
          continue;
        }
        if (!living && GameLogic.isReproducing(neighbours)) {
          cellsToCreate.add(new Cell(column,row));
          displayCellAsLiving(column, row);
        }
      }
    }

    for(Cell cellToCreate : cellsToCreate) {
      world.create(cellToCreate.column, cellToCreate.row);
    }

    for(Cell cellToKill : cellsToKill) {
      world.kill(cellToKill.column, cellToKill.row);
    }
  }

  private void displayCellAsLiving(int column, int row) {
    Button button = buttonMap.get(column).get(row);
    button.setStyle("-fx-background-color: #3635ff");
  }

  private class Cell {
    public int column;
    public int row;

    public Cell(int column, int row) {
      this.column = column;
      this.row = row;
    }
  }
}
