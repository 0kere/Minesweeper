import java.util.Random;

public class Grid {
    Tile[][] thisGrid;
    int gridSize;
    String horizontalGridNumbering = "    ";
    int nBombs;
    int nBombsRemain;//used to keep count of active bombs/flags down
    int nTilesRemain; //amount of safe tiles remaining

    public Grid(){};
    public Grid(int gridSize, int nBombs)
    {
        thisGrid = new Tile[gridSize][gridSize];
        this.gridSize = gridSize;
        this.nBombs = nBombs;
        for (int i = 0; i < gridSize; i++)
        {
            horizontalGridNumbering += (i + "  ").substring(0, 3);
            for (int j = 0; j < gridSize; j++)
            {
                thisGrid[i][j] = new Tile(this);
            }
        }
        Random rand = new Random();
        for (int i = 0; i < nBombs; i++)
        {
            int h = rand.nextInt(gridSize);
            int v = rand.nextInt(gridSize);
            if (!thisGrid[v][h].SetBomb())
            {
                i--; //Ensures the loop will generate the correct amount of bombs if we skip this iteration
            }
        }
        nTilesRemain = gridSize*gridSize - nBombs;
        nBombsRemain = nBombs;
    }

    public void DisplayGrid()
    {
        System.out.println(horizontalGridNumbering+"\n");
        for (int i = 0; i < gridSize; i++)
        {
            String row = i + "   ";
            row = row.substring(0, Math.min(row.length(), 4));
            for (int j = 0; j < gridSize; j++)
            {
                row += thisGrid[j][i].displayChar + "  ";
            }
            System.out.println(row);
        }
    }

    public void TileFlagged(int h, int v)
    {
        if (thisGrid[v][h].displayChar == 'F')
        {
            thisGrid[v][h].UpdateTile('*');
            nBombsRemain++;
        }
        else
        {
            thisGrid[v][h].UpdateTile('F');
            nBombsRemain--;
        }
    }

    //Return false if is a bomb
    public boolean TileClicked(int h, int v)
    {
        if (!CheckBounds(v,h)) return true;
        if (thisGrid[v][h].isChecked() || !thisGrid[v][h].isLive) return true;
        if (thisGrid[v][h].isBomb)
        {
            thisGrid[v][h].displayChar = 'B';
            return false;
        }
        int bombCount = 0; //Check all adjacent tiles
        bombCount += CheckBomb(h+1,v) ? 1 : 0; //increments count if there is a bomb
        bombCount += CheckBomb(h+1,v-1) ? 1 : 0;
        bombCount += CheckBomb(h+1,v+1) ? 1 : 0;
        bombCount += CheckBomb(h-1,v) ? 1 : 0;
        bombCount += CheckBomb(h-1,v+1) ? 1 : 0;
        bombCount += CheckBomb(h-1,v-1) ? 1 : 0;
        bombCount += CheckBomb(h,v+1) ? 1 : 0;
        bombCount += CheckBomb(h,v-1) ? 1 : 0;

        if (bombCount > 0)
        {
            thisGrid[v][h].UpdateTile( (char)(bombCount + '0') );
        }
        else
        {
            thisGrid[v][h].UpdateTile(' ');

            //if there are no bombs on this tile expand to the adjacent tiles
            TileClicked(h+1, v);
            TileClicked(h+1, v-1);
            TileClicked(h+1, v+1);
            TileClicked(h-1, v);
            TileClicked(h-1, v+1);
            TileClicked(h-1, v-1);
            TileClicked(h, v-1);
            TileClicked(h, v+1);
        }
        return true;
    }

    //Return true if there is a bomb, false if not or invalid tile/non-live tile
    public boolean CheckBomb(int h, int v)
    {
        if (CheckBounds(v,h) && thisGrid[v][h].isLive)
        {
            return thisGrid[v][h].isBomb;
        }
        return false;
    }
    public boolean CheckBounds(int h, int v)
    {
        return h >= 0 && h < gridSize && v >= 0 && v < gridSize;
    }

    public void resetChecked()
    {
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                thisGrid[j][i].isChecked = false;
            }
        }
    }
}
