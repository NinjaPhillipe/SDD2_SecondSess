package root.main;

/**
 * Classe qui represente la fenetre de windowing
 */
public class WindowingBox
{
	public final int MAX_VALUE = 2147483647;
	public final int MIN_VALUE = -2147483648;
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	
	/*
	 * Constructeur de la classe windowing
	 * 
	 */
	public WindowingBox(int startX,int startY, int endX,int endY)
	{
		this.startX = startX;		
		this.startY = startY;
		this.endX   = endX; 
		this.endY   = endY; 
	}
	
	public String toString() { return "window("+startX+ "|" + startY+") X ("+ endX + "|" + endY +")"; }
	
	// set 
	public void set_start_x(int startX) { this.startX = startX; }
	public void set_start_y(int startY) { this.startY = startY; }
	public void set_end_x(int endX)     { this.endX = (endX>startX) ? endX : startX; }
	public void set_end_y(int endY)     { this.endY = (endY>startX) ? endY : startX; }

	public void setStartXinfty() { this.startX = MIN_VALUE; }
	public void setStartYinfty() { this.startY = MIN_VALUE; }
	public void setEndXinfty()   { this.endX   = MAX_VALUE; }
	public void setEndYinfty()   { this.endY   = MAX_VALUE; }
	
	// get
	public int getXstart() { return startX; }
	public int getYstart() { return startY; }
	public int getXend()   { return endX;   }
	public int getYend()   { return endY;   }
}