package root.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class qui represente un segment
 */
public class Segment 
{
	private int x;
	private int y;
	private int xEnd;
	private int yEnd;
	private boolean bigMarkY;
	private boolean smallMarkY;

	/**
	 * Comparator qui premettant de trier par rapport a y
	 */
	private static class SortYstart implements Comparator<Segment>
	{
		/**
		 * retourn un entier positif si a est plus grand que b et inversement.
		 */
		public int compare(Segment a, Segment b)
		{
			return a.getY()-b.getY();
		}
		
	}

	/**
	 * Methode pour trier les segments en fonction de leur Y
	 * effet de bord : trie la liste
	 * @param segList list a trier.
	 */
	public static void sort(ArrayList<Segment> segList) { Collections.sort(segList,new SortYstart()); }

	/**
	 * Initialise un segment avec 4 valeurs
	 * @param x valeur x du premier point 
	 * @param y valeur y du premier point 
	 * @param xEnd valeur x du second point 
	 * @param yEnd valeur y du second point 
	 */
	public Segment(int x, int y, int xEnd, int yEnd) 
	{
		this.x = x;
		this.y = y;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.bigMarkY   = false;
		this.smallMarkY = false;
	} 
	/**
	 * Initialise un segment avec une chaine de charactere
	 * @param segment_info un string composer des 4 valeurs d'un segment avec un espace entre
	 */
	public Segment(String segment_info) 
	{
		String[] ok = segment_info.split(" ");
		x    = (int) Float.parseFloat(ok[0].trim());
		y    = (int) Float.parseFloat(ok[1].trim());
		xEnd = (int) Float.parseFloat(ok[2].trim());
		yEnd = (int) Float.parseFloat(ok[3].trim());
	} 
	
	@Override
	public String toString() 
	{
		return "( "+x+" | "+y+" ) X ( " +xEnd+" | "+yEnd+" )" ;
	}

	// set get
	public void setX(int x)    {this.x = x;}
	public void setY(int y )   {this.y = y;}
	public void setXend(int x) {this.xEnd = x;}
	public void setYend(int y) {this.yEnd = y;}
	
	public void setBigMarkY(boolean b)   { this.bigMarkY = b; }
	public void setSmallMarkY(boolean b) { this.smallMarkY = b; }

	public int getX()    {return x;}
	public int getY()    {return y;}
	public int getXend() {return xEnd;}
	public int getYend() {return yEnd;}

	public boolean isBigMarkY()   { return bigMarkY;   }
	public boolean isSmallMarkY() { return smallMarkY; }
}