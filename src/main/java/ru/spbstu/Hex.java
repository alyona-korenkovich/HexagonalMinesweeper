package ru.spbstu;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Hex {
    static final int tileSize = 40;
    private static final double s = tileSize / 1.73205; // length of one side
    private static final double r = tileSize / 2.0; //radius of inscribed circle (centre to middle of each side)
    private static final double t = r / 1.73205; //short side of 30o triangle outside of each hex

    static class Tile extends StackPane {
        final int x, y;
        boolean hasBomb;
        boolean isOpen = false;
        Menu m;
        Field f;
        HexNeighbors hN = new HexNeighbors();

        private final Polygon poly = new Polygon();
        final Text text = new Text();

        public Tile(int x, int y, boolean hasBomb, Menu m, Field f) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;
            this.m=m;
            this.f=f;

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
                Menu.scene2.setRoot(m.lost(m.W, m.H));
                return;
            }

            isOpen = true;
            text.setVisible(true);
            poly.setFill(Color.WHITE);

            if (text.getText().isEmpty()) {
                hN.getNeighbors(f, this).forEach(Tile::open);
            }

            f.tilesLeft -= 1;

            if (f.tilesLeft == 0) {
                System.out.println("WIN");
                Menu.scene2.setRoot(m.win(m.W, m.H));
            }
        }
    }
}
