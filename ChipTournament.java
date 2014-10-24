public class ChipTournament
{
  public static ChipsPlayer[] lineup = {new DivisionPlayer(), new OneThirdPlayer(), new OneFourth(), new OneFifthPlayer(), new OnebyOnePlayer(), new NimPlayer(), new LogarithmicPlayer(), new HardCodePlayer()}; // declares and initializes an array of players
  
  
  public static void main(String[] args)
  {
    for (int i = 0; i < lineup.length; i ++) // Automatically sets names to Player 1, Player 2, etc. if no custom name is given in the class
      lineup[i].setName("Player "+(i+1));
    
    System.out.println("Ladies and gentlemen: let's get ready to rumbllllllllle!\nWelcome to the AP Comp Sci Chips Tournament!\n");
    
    int contender = -1; // memorizes one player class index
    int t = 0;
    while (!winner()) // cycles through the lineup...
    {
      if (lineup[t%lineup.length].playing()) // skips ones that have already lost...
      {
        if (contender > -1)
        {
          battle(contender, t%lineup.length);
          contender = -1;
        }
        else
          contender = t%lineup.length;
      }
      t ++;
    }
    
    for (int i = 0; i < lineup.length; i ++)
      if (lineup[i].playing())
        System.out.println("And the victor is "+lineup[i].name()+"!");
  }
  
  
  public static boolean winner()
  {
    int x = 0;
    for (int i = 0; i < lineup.length; i ++)
      if (lineup[i].playing())
        x ++;
    return x == 1;
  }


  public static void battle(int index1, int index2)
  {
    int index1wins = 0;
    int index2wins = 0;
    
    for (int round = 0; round < 100; round ++)
    {
      int pile = (round/2)*1+10;
      boolean turn = round%2 > 0;
      double move1 = pile/2-.5;
      double move2 = pile/2-.5;
      
      while (pile > 0)
      {
        if (turn)
        {
          move1 = lineup[index1].play(pile, (int)(move2*2));
          if (move1 < 1 || move1 > move2*2)
          {
            pile = 0;
            turn = !turn;
            System.out.println(lineup[index1].name+" has forfeit the game!");
          }
          pile -= move1;
        }
        
        else
        {
          move2 = lineup[index2].play(pile, (int)(move1*2));
          if (move2 < 1 || move2 > move1*2)
          {
            pile = 0;
            turn = !turn;
            System.out.println(lineup[index2].name+" has forfeit the game!");
          }
          pile -= move2;
        }
        
        turn = !turn;
      }
      
      if (turn)
        index2wins ++;
      else
        index1wins ++;
    }
    
    System.out.print(lineup[index1].name()+" won "+index1wins+" times, and "+lineup[index2].name()+" won "+index2wins+" times.  ");
    if (index1wins > index2wins)
    {
      System.out.println(lineup[index1].name()+" wins!\n");
      lineup[index2].loss();
    }
    else if (index1wins < index2wins)
    {
      System.out.println(lineup[index2].name()+" wins!\n");
      lineup[index1].loss();
    }
    else
    {
      System.out.print("It's a perfect tie!  ");
      if (Math.random()<.5)
      {
        System.out.println("I'm giving it to "+lineup[index1].name()+".");
        lineup[index2].loss();
      }
      else
      {
        System.out.println("I'm giving it to "+lineup[index2].name()+".");
        lineup[index1].loss();
      }
    }
  }
}