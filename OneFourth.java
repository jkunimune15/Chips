public class OneFourth extends ChipsPlayer
{
  public OneFourth()
  {
    name = "OneFourth";
  }
  
  
  
  public int play(int total, int max)
  {
    if (total <= max)
      return total;
    else if (total - total*3/4 - 1 <= max && total - total*3/4 - 1 >= 1)
      return total - total*3/4 - 1;
    else
      return max;
  }
}