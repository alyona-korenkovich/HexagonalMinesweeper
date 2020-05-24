package ru.spbstu;

import org.junit.Test;
import static org.junit.Assert.*;

public class MinesweeperAppTest {

    /*The bombGenerator method does not start directly, because its use is contained in the createScene class,
    which is necessary to generate the field and the hexes*/
    @Test
    public void bombGeneratorTest() {
        Field f = new Field();
        int expected = Field.bombsAmount = 3;
        f.createScene();
        int count = 0;
        for (int y = 0; y < Field.fieldSize; y++) {
            for (int x = 0; x < Field.fieldSize; x++) {
                Hex.Tile tile = Field.grid[x][y];
                if (tile.hasBomb) {
                    count++;
                }
            }
        }
        assertEquals(expected, count);
    }
}
