package ru.spbstu;

import javafx.application.Application;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinesweeperAppTest {

    Menu m;
    Field f;
    HexNeighbors hN;

    @BeforeClass
    public static void initJFX() throws InterruptedException {
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(MinesweeperApp.class);
            }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(10);
    }

    @Before
    public void setUp() {
        m = new Menu();
        f = new Field(20, 50, m);
        hN = new HexNeighbors();
    }


    /*
    The bombGenerator method does not start directly, because its use is contained in the createScene class,
    which is necessary to generate the field and the hexes
    */

    @Test
    public void bombGeneratorTest() {
        int expected = f.bombsAmount = 4;
        f.createScene();
        int count = 0;
        for (int y = 0; y < f.fieldSize; y++) {
            for (int x = 0; x < f.fieldSize; x++) {
                Hex.Tile tile = f.grid[x][y];
                if (tile.hasBomb) {
                    count++;
                }
            }
        }
        assertEquals(expected, count);
    }

    /*
    Counts the number of mined neighboring tiles to the selected one
     */

    @Test
    public void getNeighborsWithBombs() {
        f.createScene();
        Hex.Tile tile22 = new Hex.Tile(2,2,false, m, f);

        f.grid[1][1].hasBomb = false;
        f.grid[3][2].hasBomb = false;
        f.grid[2][1].hasBomb = false;

        f.grid[1][2].hasBomb = true;
        f.grid[2][3].hasBomb = true;
        f.grid[3][1].hasBomb = true;

        long expected = 3;
        long actual = hN.getNeighbors(f, tile22).stream().filter(t -> t.hasBomb).count();
        assertEquals(expected, actual);
    }

    /*
    Checks the automatic opening of adjacent tiles:
    if a particular tile is empty, i.e. does not contain neighbors with bombs.
    Since we don't know what field will be generated, we'll check if the number of remaining tiles
    (tilesLeft) is less than the largest expected one.
     */

    @Test
    public void openTest() {
        f.createScene();
        int largestExpectedTilesLeft = f.tilesLeft - 7;

        //selected tile
        f.grid[1][1].hasBomb = false;
        //demining adjacent tiles
        f.grid[0][1].hasBomb = false;
        f.grid[0][2].hasBomb = false;
        f.grid[1][2].hasBomb = false;
        f.grid[2][2].hasBomb = false;
        f.grid[2][1].hasBomb = false;
        f.grid[1][0].hasBomb = false;

        f.grid[1][1].open();
        int actual = f.tilesLeft;
        System.out.println(actual + ", " + largestExpectedTilesLeft);
        assertTrue(actual <= largestExpectedTilesLeft);
    }
}
