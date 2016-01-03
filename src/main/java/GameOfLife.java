import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameOfLife extends Application {

  public static final int COLUMNS = 10;
  public static final int ROWS = 10;
  private World world = new World();

  private GridPane gridPane = new GridPane();
  private Map<Integer, Map<Integer, Button>> buttonMap = new HashMap<>();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    gridPane.setGridLinesVisible(true);
    BorderPane borderPane = new BorderPane(gridPane);
    Button nextRoundButton = new Button("Next round");
    nextRoundButton.setOnAction(e -> computeNextRound());
    borderPane.setTop(nextRoundButton);
    Scene scene = new Scene(borderPane, 500, 500);

    for (int column = 0; column <= COLUMNS; column++) {
      for (int row = 0; row <= ROWS; row++) {
        Button button = makeButton(column, row);
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

    stage.setTitle("Game of Life");
    stage.setScene(scene);
    stage.show();
  }

  private Button makeButton(int column, int row) {
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
    contextMenu.getItems().addAll(blinker);
    button.setContextMenu(contextMenu);
    return button;
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
