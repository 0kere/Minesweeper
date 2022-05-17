public class Tile {
    char displayChar = '*';
    boolean isBomb = false;
    boolean isLive = true;
    boolean isChecked = false; //Has this tile been checked during a turn? Stops a tile being checked over and over again when there are no bombs around a clicked tile
    Grid myGrid;

    public Tile(Grid myGrid)
    {
        this.myGrid = myGrid;
    }

    public boolean SetBomb()
    {
        if (isBomb) return false;
        isBomb = true;
        return true;
    }

    public void UpdateTile(char newDisplay)
    {
        displayChar = newDisplay;
        isLive = false;
        if (newDisplay == 'F' || newDisplay == '*')
        {
            isLive = true;
        }
        else
        {//one less live tile
            myGrid.nTilesRemain--;
        }
    }

    public boolean isChecked() {
        return isChecked;
    }


}
