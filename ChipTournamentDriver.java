public class ChipTournamentDriver
{
  public static ChipsPlayer[] lineup = {new DivisionPlayer(), new OneFourth(), new OneFifthPlayer(), new OnebyOnePlayer(), new NimPlayer(), new LogarithmicPlayer(), new RandomPlayer()}; // declares and initializes an array of players
  
  
  public static void main(String[] args)
  {
    for (int i = 0; i < lineup.length; i ++) // Automatically sets names to Player 1, Player 2, etc. if no custom name is given in the class
      lineup[i].setName("Player "+(i+1));
    
    System.out.println("Ladies and gentlemen: let's get ready to rumbllllllllle!\nWelcome to the AP Comp Sci Chips Tournament!\n");
    
    int contender = -1; // memorizes one player class index
    int t = 0;
    while (winnerCount() != 1) // cycles through the lineup until only one winner left (see winnerCount method)
    {
      if (t%lineup.length == 0) // runs at the start of each level (quarter finals, semifinals, etc.)
      {
        contender = -1; // clears memory
                                           // Draws inverted tournament tree diagram
        for (int i = 1; i < Math.pow(2, t/lineup.length); i ++) // indents names
          System.out.print("         ");
        
        int x = 0;
        for (int i = 0; i < lineup.length; i ++) // lists the names   
          if (lineup[i].playing() && x < (lineup.length - (int)(lineup.length - lineup.length/Math.pow(2, t/lineup.length)))/2*2)
            {
              System.out.print(printName(i));
              for (int j = 1; j < Math.pow(2, t/lineup.length+1); j ++) // with proper spacing
                System.out.print("         ");
            
              x ++;
            }
        System.out.print("\n"); // goes to next line
        
        System.out.print("    ");
        for (int i = 1; i < Math.pow(2, t/lineup.length); i ++) // indents diagram
          System.out.print("         ");
        
        for (int i = 1; i < winnerCount(); i += 2) // draws diagram
        {
          System.out.print("|"); // including vertical lines
          for (int j = 1; j < 18*Math.pow(2, t/lineup.length); j ++) // underscores
            System.out.print("_");
          System.out.print("|");
          for (int j = 1; j < 18*Math.pow(2, t/lineup.length); j ++) // and spaces
            System.out.print(" ");
        }
        System.out.print("\n");
      }
        
      if (lineup[t%lineup.length].playing()) // skips ones that have already lost
      {
        if (contender > -1) // if an index is saved in contender, then that class battles whoever is next in line
        {
          battle(contender, t%lineup.length); // see battle method
          contender = -1; // tells next while loop that it needs to get two more contenders for the next battle
        }
        else
          contender = t%lineup.length; // if contender is -1, there is no contender waiting, so the net guy fills that slot
      }
      t ++;
    }
    
    t ++;
    for (int i = 1; i < Math.pow(2, t/lineup.length); i ++) // caps off the tree by reiterating some code from the top of the while loop
      System.out.print("         ");
    for (int i = 0; i < lineup.length; i ++)
      if (lineup[i].playing())
        System.out.println(printName(i));
    
    for (int i = 0; i < lineup.length; i ++) // runs through lineup to see who winner is
      if (lineup[i].playing())
        System.out.println("And the victor is "+lineup[i].name()+"!"); // Congratulates them
  }
  
  
  public static int winnerCount()
  {
    int x = 0;
    for (int i = 0; i < lineup.length; i ++) // count number of players still playing
      if (lineup[i].playing())
        x ++;
    return x; // and return how many there are
  }


  public static void battle(int index1, int index2)
  {
    int index1wins = 0;
    
    for (int round = 0; round < 100; round ++) // run 200 games
    {
      int pile = (round/2)+2; // each one will start with 2 - ??? pieces (use int math to make it go 2, 2, 3, 3, 4, 4, etc.
      boolean turn = round%2 > 0; // who goes first will alternate
      double move1 = pile/2-.5; // the last moves will be initialized such that the starting max is one less than the pile
      double move2 = pile/2-.5;
      
      while (pile > 0)
      {
        if (turn) // if player 1's turn
        {
          move1 = lineup[index1].play(pile, (int)(move2*2)); // take player 1's move
          if (move1 < 1 || move1 > move2*2) // check for legality
          {
            pile = 0; // if illegal move, make player 2 automatically win
            turn = !turn;
          }
          pile -= move1; // otherwise, take according number out of the pile
        }
        
        else // if player 2's turn, same thing but opposite
        {
          move2 = lineup[index2].play(pile, (int)(move1*2));
          if (move2 < 1 || move2 > move1*2)
          {
            pile = 0;
            turn = !turn;
          }
          pile -= move2;
        }
        
        turn = !turn; // switch turn
      }
      
      if (!turn) // change score based on who won
        index1wins ++;
    }
    
    if (index1wins > 50)
      lineup[index2].loss(); // take loser out of running
    
    else if (index1wins < 50)
      lineup[index1].loss();
    
    else // if tie
    {
      if (Math.random()<.5) 
        lineup[index2].loss();
      else
        lineup[index1].loss();
    }
  }
  
  
  public static String printName(int index) // converts the custom name of a class to a version compatible with the tree diagram
  {
    if (lineup[index].name().length() <= 9)                         // if it has length shorter than or equal to 9
      return (lineup[index].name() + "         ").substring(0, 9);  // print the whole thing plus however many spaces makes it 9
    else
      return lineup[index].name().substring(0, 8) + "."; // otherwise, abbreviate the name
  }
}