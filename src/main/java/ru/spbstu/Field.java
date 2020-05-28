package ru.spbstu;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Field {

    Hex.Tile[][] grid;
    int fieldSize;
    int bombsAmount;
    int tilesLeft;
    Menu m;

    public Field(int fieldSize, int bombsAmount, Menu m) {
        this.fieldSize = fieldSize;
        this.bombsAmount = bombsAmount;
        this.m = m;
    }

    //creating a scene with a game
    public Pane createScene() {
        grid = new Hex.Tile[fieldSize][fieldSize];
        if (bombsAmount > fieldSize * fieldSize) {
            bombsAmount = fieldSize * fieldSize - 1;
        }

        tilesLeft = fieldSize * fieldSize - bombsAmount;

        final int W = Hex.tileSize * fieldSize;
        final int H = Hex.tileSize * (fieldSize + 1) + 30;

        Pane root = new Pane();
        root.setPrefSize(W, H);
        root.setMinSize(800, 600);

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                Hex.Tile tile = new Hex.Tile(x, y, false, m, this);
                grid[x][y] = tile;
            }
        }

        bombGenerator(bombsAmount);

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                root.getChildren().add(grid[x][y]);
            }
        }

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                Hex.Tile tile = grid[x][y];

                if (tile.hasBomb) {
                    continue;
                }

                HexNeighbors hN = new HexNeighbors();
                long bombs = hN.getNeighbors(this, tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0) {
                    tile.text.setText("" + bombs);
                }
            }
        }

        Text bombsLeft = new Text();
        bombsLeft.setText("Tiles left to open: " + tilesLeft);
        bombsLeft.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 16));
        bombsLeft.setTranslateX(10);
        bombsLeft.setTranslateY(Hex.tileSize * (fieldSize + 1));

        root.getChildren().add(bombsLeft);
        root.setOnMouseClicked(e -> bombsLeft.setText("Tiles left to open: " + tilesLeft));

        return root;
    }

    //placing bombs on the grid
    public void bombGenerator(int n) {
        int randX;
        int randY;

        while (n != 0) {
            randX = (int) (Math.random() * fieldSize);
            randY = (int) (Math.random() * fieldSize);
            if (!grid[randX][randY].hasBomb) {
                grid[randX][randY].hasBomb = true;
                n--;
            }
        }
    }
}
