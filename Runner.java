import java.awt.*;
public class Runner
{
	private Color myColor;
	private int x;
	private int y;
	private int radius;
	public Runner()
	{
		x = 200;
		y = 200;
		radius = 10;
		myColor = Color.BLUE;
	}
	public Runner(int rad)
	{
		x = 200;
		y = 200;
		radius = rad;
		myColor = Color.BLUE;
	}
	public Runner(int a, int b, int rad, Color c)
	{
		radius = rad;
		x = a;
		y = b;
		myColor = c;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getRadius()
	{
		return radius;
	}
	public void setPos(int a, int b)
	{
		x = a;
		y = b;
	}
	
	public void moveUp(int move)
	{
		y-=move;
	}
	public void moveDown(int move)
	{
		y+=move;
	}
	public void moveLeft(int move)
	{
		x-=move;
	}
	public void moveRight(int move)
	{
		x+=move;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(myColor);
		g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
	}
}