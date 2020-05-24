package ru.spbstu;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Hex {
    //private static final int tileSize = Field.tileSize;
    private static final int tileSize = 40;
    private static final int fieldSize = Field.fieldSize;
    private static final double s = tileSize / 1.73205; // length of one side
    private static final double r = tileSize / 2.0; //radius of inscribed circle (centre to middle of each side)
    private static final double t = r / 1.73205; //short side of 30o triangle outside of each hex

    //getting neighbours for the actual tile
    static List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        int[] points;

        if (tile.x % 2 == 1) {
            points = new int[]{
                    0, -1,
                    -1, 0,
                    -1, 1,
                    0, 1,
                    1, 1,
                    1, 0
            };
        } else {
            points = new int[]{
                    0, -1,
                    -1, -1,
                    -1, 0,
                    0, 1,
                    1, 0,
                    1, -1
            };
        }

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < fieldSize
                    && newY >= 0 && newY < fieldSize) {
                neighbors.add(Field.grid[newX][newY]);
            }
        }

        return neighbors;
    }

    static class Tile extends StackPane {
        private final int x, y;
        boolean hasBomb;
        private boolean isOpen = false;

        private final Polygon poly = new Polygon();
        final Text text = new Text();

        public Tile(int x, int y, boolean hasBomb) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            double x0 = x * (s + (r / 1.73205));
            double y0 = y * tileSize + (x % 2) * r;

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);

            poly.getPoints().addAll(x * 1.0, y * 1.0,
                    x + s, y * 1.0,
                    x + s + t, y + r,
                    x + s, y + r + r,
                    x * 1.0, y + r + r,
                    x - t, y + r);

            poly.setStroke(Color.LIGHTBLUE);

            setTranslateX(x0);
            setTranslateY(y0);

            getChildren().addAll(poly, text);

            setOnMouseClicked(e -> open());
        }

        public void open() {
            if (isOpen)
                return;

            if (hasBomb) {
                System.out.println("GAME OVER");
                Menu.scene2.setRoot(Menu.lost());
                return;
            }

            isOpen = true;
            text.setVisible(true);
            poly.setFill(Color.WHITE);

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile::open);
            }

            Field.tilesLeft -= 1;

            if (Field.tilesLeft == 0) {
                System.out.println("WIN");
                Menu.scene2.setRoot(Menu.win());
            }
        }
    }
}
