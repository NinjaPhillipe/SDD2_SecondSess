package root.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui s'occupe de charger le fichier et faire le lien entre la partie 
 * logique et la partie graphique
 */
public class MainClass 
{
	private  PrioritySearchTree tree;
	private WindowingBox box;
	
	/**
	 * Initialise la classe en chargant le fichier depuis la chaine de 
	 * caractere passee en parametre.
	 * @param path Chemin jusqu'au fichier
	 */
	public MainClass(String path) 
	{
		box = new WindowingBox(0, 0, 200, 200);
		try 
		{
			final ArrayList<Segment> segList = loadFromFile(path);

			//trie par ordre croissant selon les y
			Segment.sort(segList);

			this.tree = new PrioritySearchTree(segList);
			
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
	public void computeTree() { tree.queryPrioSearchTree(box); }
	
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
		
		// tant qu il y a des lignes dans le fichier.
		while(sc.hasNextLine()) {
			Segment seg = new Segment(sc.nextLine());
			
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
	
	public PrioritySearchTree getTree()      { return tree;     }
	public WindowingBox getWindowingBox()    { return box;      }
}