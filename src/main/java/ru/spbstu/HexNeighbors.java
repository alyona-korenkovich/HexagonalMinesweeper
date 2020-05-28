package ru.spbstu;

import java.util.ArrayList;
import java.util.List;

public class HexNeighbors {
    private final int[] pointsOdd = new int[]{
            0, -1,
            -1, 0,
            -1, 1,
            0, 1,
            1, 1,
            1, 0
    }; //X-Y coordinate pairs of adjacent tiles, if the tile is located in an odd column
    private final int[] pointsEven = new int[]{
            0, -1,
            -1, -1,
            -1, 0,
            0, 1,
            1, 0,
            1, -1
    }; //X-Y coordinate pairs of adjacent tiles, if the tile is located in an even column

    //getting neighbours for the actual tile
    List<Hex.Tile> getNeighbors(Field f, Hex.Tile tile) {
        final int fieldSize = f.fieldSize;

        List<Hex.Tile> neighbors = new ArrayList<>();

        int[] points;
        points = (tile.x % 2 == 1) ? pointsOdd : pointsEven;

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < fieldSize
                    && newY >= 0 && newY < fieldSize) {
                neighbors.add(f.grid[newX][newY]);
            }
        }

        return neighbors;
    }
}
