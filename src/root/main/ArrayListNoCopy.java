package root.main;

import java.util.ArrayList;

/**
 * Class qui encapsule une arraylist afin de pouvoir en faire une sub list sans pour autant en faire une copie.
 */
public class ArrayListNoCopy<T>
{
	private ArrayList<T> arrayList;
	
	private int startID;
	private int endID;
	
	/**
	 * Initialise l'arraylist
	 * @param indice de debut 
	 * @param indice de fin
	 */
	public ArrayListNoCopy(ArrayList<T> arrayL, int start, int end) 
	{
		arrayList = arrayL;
		startID   = start;
		endID     = end;
	}

	/**
	 * Pose l object dans l arraylist en prenant compte des indices
	 */
	public void setObj(int i,T e) 
	{ 
		if(i>=0 ) 
		{
			arrayList.set(startID+i,e); 
		}
	}
	public void setStartID(int start) { startID = start; }
	public void setEndID(int end)     { endID = end;     }

	/**
	 * Retourne l object dans l arraylist en prenant compte des indices
	 * 
	 * @return T retourne l'objet si il est dans les bornes sinon retourne null
	 */
	public T getObj(int i) 
	{ 
		if(i>=0 && this.getStartID()+i <=this.getEndID()) 
		{
			return arrayList.get(startID+i); 
		}
		System.out.println("NULL POINTER borne("+startID+"|"+endID+")->"+(startID+i));
		return null; // mange ta null pointer exception
	}
	public int           getStartID()   { return startID;       }
	public int           getEndID()     { return endID;         }
	public int           getInterval()  { return endID-startID; }
	public ArrayList<T>  getArrayList() { return arrayList;     }
}