package PentominosUtils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisGame extends Application {

    private static final int TILE_SIZE = 30;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final Color[] COLORS = {Color.TRANSPARENT, Color.CYAN, Color.YELLOW, Color.PURPLE, Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE};

    private int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];
    private Pane gamePane = new Pane();
    private int currentShape;
    private int currentX;
    private int currentY;
    private Timeline timeline;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        root.setCenter(gamePane);

        Scene scene = new Scene(root, TILE_SIZE * GRID_WIDTH, TILE_SIZE * GRID_HEIGHT);
        scene.setOnKeyPressed(this::handleKeyPress);

        initializeGame();

        primaryStage.setTitle("Tetris Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGame() {
        spawnRandomShape();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::moveDown));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void spawnRandomShape() {
        currentShape = (int) (Math.random() * 7) + 1; // Random shape index (1 to 7)
        currentX = GRID_WIDTH / 2;
        currentY = 0;

        drawShape();
    }

    private void drawShape() {
        gamePane.getChildren().clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Shapes.SHAPES[currentShape][i][j] != 0) {
                    drawBlock(currentX + j, currentY + i, COLORS[currentShape]);
                }
            }
        }
    }

    private void drawBlock(int x, int y, Color color) {
        javafx.scene.shape.Rectangle block = new javafx.scene.shape.Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        block.setFill(color);
        gamePane.getChildren().add(block);
    }

    private void moveDown(ActionEvent event) {
        if (isValidMove(0, 1)) {
            currentY++;
            drawShape();
        } else {
            mergeShape();
            clearLines();
            spawnRandomShape();
        }
    }

    private void mergeShape() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Shapes.SHAPES[currentShape][i][j] != 0) {
                    grid[currentX + j][currentY + i] = currentShape;
                }
            }
        }
    }

    private boolean isValidMove(int offsetX, int offsetY) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Shapes.SHAPES[currentShape][i][j] != 0) {
                    int newX = currentX + j + offsetX;
                    int newY = currentY + i + offsetY;

                    if (newX < 0 || newX >= GRID_WIDTH || newY >= GRID_HEIGHT || grid[newX][newY] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void clearLines() {
        for (int y = GRID_HEIGHT - 1; y >= 0; y--) {
            boolean lineIsFull = true;
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[x][y] == 0) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                removeLine(y);
                y++; // Check the same line again since it's a new line now
            }
        }
    }

    private void removeLine(int lineIndex) {
        for (int y = lineIndex; y > 0; y--) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                grid[x][y] = grid[x][y - 1];
            }
        }
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.LEFT && isValidMove(-1, 0)) {
            currentX--;
            drawShape();
        } else if (code == KeyCode.RIGHT && isValidMove(1, 0)) {
            currentX++;
            drawShape();
        } else if (code == KeyCode.DOWN && isValidMove(0, 1)) {
            currentY++;
            drawShape();
        } else if (code == KeyCode.UP) {
            rotateShape();
            drawShape();
        }
    }

    private void rotateShape() {
        int[][] rotatedShape = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedShape[i][j] = Shapes.SHAPES[currentShape][3 - j][i];
            }
        }

        int[][] originalShape = Shapes.SHAPES[currentShape];
        Shapes.SHAPES[currentShape] = rotatedShape;

        if (!isValidMove(0, 0)) {
            // Revert the rotation if it's an invalid move
            Shapes.SHAPES[currentShape] = originalShape;
        }
    }

    public static class Shapes {
        // Define the shapes as 3D arrays (1s represent filled blocks)
        // Index 0 represents an empty block, and indexes 1-7 represent different colors/shapes.

        // Tetromino I
        static int[][] shapeI = {
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Tetromino J
        static int[][] shapeJ = {
                {1, 0, 0, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Tetromino L
        static int[][] shapeL = {
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Tetromino O
        static int[][] shapeO = {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Tetromino S
        static int[][] shapeS = {
                {0, 1, 1, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Tetromino T
        static int[][] shapeT = {
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Tetromino Z
        static int[][] shapeZ = {
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        // Array to store the shapes
        static int[][][] SHAPES = {shapeI, shapeJ, shapeL, shapeO, shapeS, shapeT, shapeZ};
    }
}
