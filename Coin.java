import java.awt.*;
public class Coin extends Runner
{
	private int value;
	private Color color = Color.YELLOW;
	
	public Coin()
	{
		super(100,100,5,Color.YELLOW);
		value = 1;	
	}
	public Coin(int val, int rad, int a, int b)
	{
		super(a,b,rad,Color.YELLOW);
		value = val;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setRandPos(int frame)
   {
      // moves location to random (x, y) within the edges
      setPos((int)(Math.random()* (frame-2*getRadius()) + getRadius()),
					(int)(Math.random()* (frame-2*getRadius()-30) + getRadius()+30));
   }
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(getX()-getRadius(), getY()-getRadius(), 2*getRadius(), 2*getRadius());
	}
}