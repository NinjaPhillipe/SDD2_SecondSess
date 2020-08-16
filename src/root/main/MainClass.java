package root.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import jdk.jfr.Unsigned;

import java.lang.NumberFormatException;

/**
 * Classe qui s'occupe de charger le fichier et faire le lien entre la partie 
 * logique et la partie graphique
 */
public class MainClass 
{
	private  PrioritySearchTree treeHor;
	private  PrioritySearchTree treeVer;

	private WindowingBox box;

	// TEST TEST 
	public ArrayList<Segment> ALLSEG;

	private boolean DEBUG_MODE = false;
	
	/**
	 * Initialise la classe en chargant le fichier depuis la chaine de 
	 * caractere passee en parametre.
	 * @param path Chemin jusqu'au fichier
	 */
	public MainClass(String path,boolean debug) 
	{
		DEBUG_MODE = debug;
		box = new WindowingBox(0, 0, 200, 200);
		try 
		{
			// final ArrayList<Segment> segList = loadFromFile(path);

			// //trie par ordre croissant selon les y
			// Segment.sort(segList);

			ArrayList<Segment> segHor = new ArrayList<>();
			ArrayList<Segment> segVer = new ArrayList<>();

			if(DEBUG_MODE)
				ALLSEG = new ArrayList<>();

			for(Segment s: loadFromFile(path))
			{
				if(DEBUG_MODE)
					ALLSEG.add(s);

				if(s.getX() == s.getXend()) // si vertical
					// rotation du referenciel
					// s.getY() etant le minimum car le poit minimum est devant
					// et que s.getX() est egale a s.getXend()
					segVer.add(new Segment(s.getY(),s.getX(),s.getYend(),s.getX()));
				else
					segHor.add(s);	
			}

			Segment.sort(segHor);
			Segment.sort(segVer);
			
			if(segHor.size() > 0)
				this.treeHor = new PrioritySearchTree(segHor);

			if(segVer.size() > 0)
				this.treeVer = new PrioritySearchTree(segVer);
			
		} catch (FileNotFoundException e) 
		{
			System.out.println("Le fichier n'a pas pu etre charger");
			e.printStackTrace();
		}
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
	}

	/**
	 * Methode qui permet de lancer la recherche dans l'arbre 
	 * ainsi que d'afficher les segments resultant de cette recherche
	 */
	public ArrayList<Segment> computeTree() 
	{ 
		ArrayList<Segment> res,resv = null;
		// si l'arbre hori n est pas vide 
		if(treeHor != null) 
			res = treeHor.queryPrioSearchTree(box);
		else
			res = new ArrayList<>();

		// si l'arbre verti n est pas vide 
		if(treeVer != null)
		{
			resv = treeVer.queryPrioSearchTree(new WindowingBox(box.getYstart(), box.getXstart(), box.getYend(), box.getXend()));
			// change le referenciel de la fenetre de windowing
			for(Segment s : resv )
				res.add(new Segment(s.getY(),s.getX(),s.getYend(),s.getXend()));
		}

		return res;
	}
	
	/**
	 * Methode qui charge un fichier pour le transformer 
	 * en une liste de segments
	 * 
	 * @param file_name Fichier a charger
	 * 
	 * @throw FileNotFoundException Fichier inexistant
	 */
	private ArrayList<Segment> loadFromFile(String file_name) throws FileNotFoundException {
		ArrayList<Segment> alSeg = new ArrayList<Segment>();
		File file = new File(file_name);
		Scanner sc = new Scanner(file);
		
		int lineCounter = 0; 

		// tant qu il y a des lignes dans le fichier.
		while(sc.hasNextLine()) {
			lineCounter++;
			Segment seg;
			try {
				seg = new Segment(sc.nextLine());
			} catch (java.lang.NumberFormatException e) {
				// passe a la ligne suivante
				System.out.println("Wrong format line number :"+lineCounter);
				break;
			}

			
			// Modifie les segments facon a ce que la coordonnees min
			// se trouve en premier grace a la propriete que les segments
			// soient uniquement horizontaux ou verticaux qui implique 
			// que soit les x sont egaux ou soit les y
			if(seg.getX() > seg.getXend())
			{
				// pas besoin de changer y car egaux car segement horizontaux ou verticaux
				int tmp = seg.getXend();
				seg.setXend(seg.getX());
				seg.setX(tmp);
			}
			else if(seg.getY() > seg.getYend())
			{
				// pas besoin de changer x car egaux car segement horizontaux ou verticaux
				int tmp = seg.getYend();
				seg.setYend(seg.getY());
				seg.setY(tmp);
			}
			alSeg.add(seg);
		}
		sc.close();
		return alSeg;
	}

	// set et get
	public void setWindowingBox(WindowingBox box) { this.box = box; }
	
	public PrioritySearchTree getTreeHor()  { return treeHor; }
	public PrioritySearchTree getTreeVer()  { return treeVer; }
	public WindowingBox getWindowingBox()   { return box;     }
}