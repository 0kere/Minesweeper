import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m"; //Used to color text in terminal

        Grid grid = new Grid();
        Scanner input = new Scanner(System.in);
        String playerInput;
        boolean play = true;

        System.out.println("Welcome to Minesweeper!");
        while (play)
        {
            boolean inGame = false;
            //Difficulty select
            System.out.println("\nWhat difficulty would you like to play? \n\tb - Beginner\n\ti - Intermediate\n\te - Expert");
            while (!inGame)
            {
                switch (input.nextLine())
                {
                    case "b": grid = new Grid(9,10);
                        inGame = true;
                        break;
                    case "i": grid = new Grid(16,40);
                        inGame = true;
                        break;
                    case "e": grid = new Grid(22, 99);
                        inGame = true;
                        break;
                    default: System.out.println("Please enter a single character of either b, i or e");
                        break;
                }
            }
            //Main Game loop
            while (inGame)
            {
                System.out.println("Mines Remaining: "+grid.nBombsRemain);
                grid.DisplayGrid();
                System.out.println("\n* - live tile\nempty - not live and no bombs\n1-5 - 1-5 live mine neighbours\nF - flagged tile");
                System.out.println("\nInput next grid coord (h,v) followed by C to click the tile or F to flag/un-flag the tile. E.g. 0,0 C");
                //Handle input
                playerInput = input.nextLine();
                playerInput = playerInput.toUpperCase();
                int h = Integer.parseInt(playerInput.substring(0, playerInput.indexOf(',')));;
                int v = Integer.parseInt(playerInput.substring(playerInput.indexOf(',')+1, playerInput.indexOf(' ')));
                if (grid.CheckBounds(h,v) && grid.thisGrid[h][v].isLive)
                {//Valid coords
                    char inputType = playerInput.charAt(playerInput.indexOf(' ')+1);
                    if (inputType == 'F')
                    {
                        grid.TileFlagged(v,h);
                    }
                    else if (inputType == 'C')
                    {
                        if (!grid.TileClicked(v,h))
                        {//Bomb has been clicked
                            inGame = false;
                            grid.DisplayGrid();
                            System.out.println(ANSI_RED + "\nGame Over!" + ANSI_RESET);
                        }
                        else
                        {
                            //Check if player has won
                            if (grid.nTilesRemain == 0)
                            {
                                inGame = false;
                                grid.DisplayGrid();
                                System.out.println(ANSI_GREEN + "All mines swept!" + ANSI_RESET);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Invalid input type. use C or F");
                    }
                }
                else
                {

                    System.out.println(ANSI_RED + "Invalid tile given" + ANSI_RESET);
                }
            }
            //Game over & play again
            System.out.println("Would you like to play again? (y/n)");
            if (input.nextLine() == "n")
            {
                play = false;
            }
            else
            {
                System.out.println("New Game!");
            }
        }
    }
}
