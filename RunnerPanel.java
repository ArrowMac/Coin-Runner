import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;
public class RunnerPanel extends JPanel
{
	private static final int FRAME = 400;
	private static final Color BACKGROUND = new Color(204, 204, 204);
	private Runner run;
	private ArrayList<Coin> coins;
	private Timer t;
	private Graphics graphic;
	private Image myImage;
	
	private static final int MOVE = 3; //speed of the runner
	private boolean upKey = false, downKey = false, leftKey = false, rightKey = false;
	
	private int myMoney = 0;
	private int high = 0;
	private double time = 90;
	
	public RunnerPanel()
	{
		myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      graphic = myImage.getGraphics();
		graphic.setColor(BACKGROUND);
		graphic.fillRect(0, 0,FRAME,FRAME);
		
		run = new Runner();
		run.draw(graphic);
		
		coins = new ArrayList();
		Coin coin = new Coin();
		coin.setRandPos(FRAME);
		coins.add(coin);
		coin.draw(graphic);
		
		try
      {
         File inputFile = new File("HighScore.dat");
         Scanner in = new Scanner(inputFile);
			         
         while(in.hasNextLine())
         {
            String line = in.nextLine();
            high = Integer.parseInt(line);
         }
         in.close();
      }
      catch(Exception e)
      {
         System.out.println("Unable to Obtain High Score");
      }
		
		t = new Timer(5, new Listener());
      t.start();
		setFocusable(true);
		addKeyListener(new Key());
	}
	public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
	private class Key extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP: 
					upKey = true;
					break;
				case KeyEvent.VK_DOWN:
					downKey = true;
					break;
				case KeyEvent.VK_LEFT:
					leftKey = true;
					break;
				case KeyEvent.VK_RIGHT:
					rightKey = true;
			}
		}
		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP: 
					upKey = false;
					break;
				case KeyEvent.VK_DOWN:
					downKey = false;
					break;
				case KeyEvent.VK_LEFT:
					leftKey = false;
					break;
				case KeyEvent.VK_RIGHT:
					rightKey = false;
			}
		}
	}
	private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
			if(upKey)
				run.moveUp(MOVE);
			if(downKey)
				run.moveDown(MOVE);
			if(leftKey)
				run.moveLeft(MOVE);
			if(rightKey)
				run.moveRight(MOVE);
			if(run.getX() > FRAME)
				run.setPos(FRAME,run.getY());
			if(run.getX() < 0)
				run.setPos(0,run.getY());
			if(run.getY() > FRAME)
				run.setPos(run.getX(),FRAME);
			if(run.getY() < 30)
				run.setPos(run.getX(),30);
			time-=0.0156;
         myRepaint();
			if(time <= 0)
				t.stop();
		}
	}
	
	private void myRepaint()
	{
         graphic.setColor(BACKGROUND);
     	   graphic.fillRect(0,0,FRAME,FRAME);
			for(int x = 0; x < coins.size(); x++)
			{
				Coin coin = coins.get(x);
				collide(run,coin,x);
				coin.draw(graphic);
			}
			run.draw(graphic);
			graphic.setColor(new Color(255,255,200));
			graphic.fillRect(0,0,FRAME,30);
			graphic.setColor(Color.RED);
         graphic.setFont(new Font("Monospaced", Font.BOLD, 20));
			graphic.drawString("Highscore:$" + high, 10, 20);
			graphic.setFont(new Font("Monospaced", Font.BOLD, 20));
			graphic.drawString("Score:$" + myMoney, FRAME/2, 20);
			graphic.drawString(":" + (int)(time+1), FRAME-50, 20);
			repaint();
	}
	
	private void addCoin()
	{
		if(myMoney % 5 == 0)
		{
			if((int)(Math.random()*101) < 10)
			{
				Coin coin = new Coin(5,10,0,0);
				coin.setRandPos(FRAME);
				coins.add(coin);
			}
			else
			{
				Coin coin = new Coin();
				coin.setRandPos(FRAME);
				coins.add(coin);
			}
		}
	}
	
	private void collide(Runner r, Coin c, int index)
   {
      double d = distance(r.getX(), r.getY(), c.getX(), c.getY());
      if(d<=r.getRadius()+c.getRadius())
      {
			myMoney+= c.getValue();
			Coin coin = new Coin();
			coin.setRandPos(FRAME);
			coins.set(index,coin);
			addCoin();
			
			if(myMoney > high)
				high = myMoney;
			
			try
         {
            BufferedWriter outPut = new BufferedWriter(new FileWriter("HighScore.dat"));
            outPut.write(""+ high);
            outPut.close();
         }
            
         catch(IOException a)
         {
            System.err.println("Unable to Save High Score");
         }
      }
   }
   private double distance(double x1, double y1, double x2, double y2)
   {
      return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));	 // enter the calculation here.
   }
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Runner");
		frame.setSize(700, 700);
		frame.setLocation(200, 25);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new RunnerPanel());
		frame.setVisible(true);
	}
}