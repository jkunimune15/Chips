public class OneThirdPlayer extends ChipsPlayer
{
  public OneThirdPlayer()
  {
    name = "One out of Three";
  }
  
  
  
  public int play(int total, int max)
  {
    if (total <= max)
      return total;
    else if (total - total*2/3 - 1 <= max && total - total*2/3 - 1 >= 1)
      return total - total*2/3 - 1;
    else
      return 1;
  }
}