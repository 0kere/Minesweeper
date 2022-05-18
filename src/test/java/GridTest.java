import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class GridTest {
    Grid gridTest = new Grid(10,10);

    @Test
    public void testSetGrid()
    {
        int nBombs = 0;
        int tiles = 0;
        for (int i = 0; i < gridTest.gridSize; i++)
        {
            for (int j = 0; j < gridTest.gridSize; j++)
            {
                tiles++;
                if (gridTest.thisGrid[i][j].isBomb) nBombs++;
            }
        }
        Assertions.assertEquals(100, tiles, "The grid size has not been constructed correctly");
        Assertions.assertEquals(10, nBombs, "Mines have not been generated correctly");
    }
}
