package root.main;

import java.util.ArrayList;


/**
 * Classe qui represente a arbre de recherche a priorite.
 */
public class PrioritySearchTree 
{
	private Segment root;
	
	private PrioritySearchTree left  = null;
	private PrioritySearchTree right = null;

	public enum Choice {CENTER,LEFT}
	
	/**
	 * Precondition la liste doit etre triee par ordre decroissant selon y 
	 * 
	 * Constructeur permetant de de cree l'arbre de priorite a partir d'une liste de segments
	 * @param seg_list liste triee selon y
	 */
	public PrioritySearchTree(final ArrayList<Segment> seg_list) { this(seg_list,0,seg_list.size()-1); }
	
	/**
	 * Cree un arbre a partir d'un sous ensemble d'une lsite comportant des segments.
	 * @param segList
	 * @param start indice de depart
	 * @param end indice de fin
	 */
	private PrioritySearchTree(final ArrayList<Segment> segList,final int start,final int end) 
	{	
		// cas de base
		if(start == end)  // cas de base
		{
			root = segList.get(start);
			segList.set(start, null);
			return;
		}
		
		final int med = getMed(start,end);
		root    = extractMinx(segList, start, end,med);	
		
		// comme la liste est triee en y on peut la separer en deux au lieu de chercher le min

		if(start <= med-1) // si il reste au moins un element dans la sous liste
			left  = new PrioritySearchTree(segList,start,med-1) ;
		else
			left = null;
		if(med+1 <= end)   // si il reste au moins un element dans la sous liste
			right = new PrioritySearchTree(segList,med+1,end);
		else
			right = null;
		
	}
	
	/**
	 * Calcul la mediane d'une sous liste
	 * @param start indice debut de la liste
	 * @param end indice fin de la liste
	 * @return La mediane 
	 */
	private int getMed(final int start, final int end) 
	{
		final int n_node = end-start +1; //pour avoir le nombre de noeud
		final int med = start + (n_node / 2);
		return med;
	}
	
	/**
	 * Extrait le segment de coordonnee x minimum d'une sous liste.
	 * @param arrayl liste sur laquel on extrait.
	 * @param start indice de depart de la sous liste.
	 * @param end indice de fin de la sous liste.
	 * @param med mediane de la sous liste
	 * @return
	 */
	private Segment extractMinx(final ArrayList<Segment> arrayl, final int start , final int end, final int med)
	{
		int id = start;

		// cherche l'indice du segment de coordonne x minimum.
		for (int i = start+1 ; i <= end ; i++ ) 
		{
			if(arrayl.get(i).getX() < arrayl.get(id).getX()) 
				id = i;
		}

		final Segment res = arrayl.get(id);

		// pour reequilibrer la list en mettant le trou au milieu afin de pouvoir prendre deux sous list sans deep copy:
		if(id < med ) 
		{ // si est en dessous de la moitier shift le trou vers la droite 
			for (int i = id ; i < med ; i++ ) 
				arrayl.set(i, arrayl.get(i+1));
		}else if (med < id ) 
		{ // cas ou le trou est a droite donc shift vers la gauche.
			for (int i =  id ; i >= med ; i-- ) 
				arrayl.set(i, arrayl.get(i-1));
		}
		// mets la mediane a null pour etre sur de ne plus pourvoir acceder au segment que l'on viens d'extraire
		arrayl.set(med, null);
		return res;
	}		

	/**
	 * Execute la recherche dans l'arbre de priorite en fonction d'une fenetre
	 * @param window fenetre sur laquel on execute la recherche
	 */
	public ArrayList<Segment> queryPrioSearchTree(final WindowingBox window)
	{
		ArrayList<Segment> res = new ArrayList<>();

		this.bigMarkQy(window.getYend());
		this.smallMarkQy(window.getYstart()); 
		//recherche centre et gauche car meme borne y

		// recherche ceux qui ont leurs point min dans le centre 
		research(window,Choice.CENTER,res);

		//recherche ceux qui ont une borne en dehors 
		research(window,Choice.LEFT,res);

		this.unBigMarkQy();
		this.unSmallMarkQy();

		return res;
	}

	/**
	 * Execute la recherche pour les points dans le centre de la fenetre ou sur la bande gauche.
	 * @param window fenetre de windowing
	 * @param choice center ou left
	 * @param seg    ArrayList dans laquelle on ajoute les segments accepter.
	 */
	private void research(final WindowingBox window,final Choice choice,ArrayList<Segment> seg)
	{
		if(accepted(root,window,choice))
			seg.add(root);

		// si bigmark et small mark sont a droite 
		if(right!=null && right.root.isBigMarkY() && right.root.isSmallMarkY())
			right.research(window, choice, seg);
		// sinon si big mark et small mark sont a gauche
		else if(left!=null && left.root.isBigMarkY() && left.root.isSmallMarkY())
			left.research(window, choice, seg);
		// sinon si big et small ne sont pas confondu
		else
		{
			if(right !=null && right.root.isBigMarkY()) right.travelBigMarkY(window,choice,seg);
			if(left !=null && left.root.isSmallMarkY()) left.travelSmallMarkY(window,choice,seg);
		}
	}

	/**
	 * Precondition la racine n'a pas big mark et small mark confondu.
	 * 
	 * @param window fenetre de windowing
	 * @param choice choix 
	 * @param seg    ArrayList dans laquelle on ajoute les segments accepter.
	 */
	private void travelBigMarkY(final WindowingBox window,final Choice choice,ArrayList<Segment> seg)
	{
		// si le noeud est accepter dessiner le segment
		if(accepted(root,window,choice))
			seg.add(root);
		
		// si big mark est a droite et que small mark n y est pas 
		if(right != null && right.root.isBigMarkY())
		{ 
			// et que a left n est pas vide 
			if(	left != null ) left.reportInSubTree(window,choice,seg);

			right.travelBigMarkY(window, choice, seg);
		}
		// si big mark est a gauche
		else if(left != null && left.root.isBigMarkY()) left.travelBigMarkY(window, choice, seg);
	}

	/**
	 * Precondition la racine n'a pas big mark et small mark confondu.
	 * 
	 * @param window fenetre de windowing
	 * @param choice choix 
	 * @param seg    ArrayList dans laquelle on ajoute les segments accepter.
	 */
	private void travelSmallMarkY(final WindowingBox window,final Choice choice,ArrayList<Segment> seg)
	{
		// si le noeud est accepter
		if(accepted(root,window,choice))
			seg.add(root);

		// si small mark est a gauche et que small mark n y est pas 
		if(left != null && left.root.isSmallMarkY() )
		{ 
			// et que a left n est pas vide 
			if(	right != null ) right.reportInSubTree(window,choice,seg);
			
			left.travelSmallMarkY(window, choice, seg);
		}
		// si small mark est a droite
		else if(right != null && right.root.isSmallMarkY()) right.travelSmallMarkY(window, choice, seg);
	}

	/**
	 * Precondition Choice non null. Si choice mal choisi retourne 0.
	 * 
	 * Retourne la brone superieur x enf fonction du choix.
	 * 
	 * @param choice  choix
	 * @param win fenetre de windowing
	 * @return borne superieur x
	 */
	private int borneXsup(final Choice choice, final WindowingBox win)
	{
		switch(choice)
		{
			case CENTER: return win.getXend();
			case LEFT:   return win.getXstart();
		}
		//UNREACHABLE
		return 0;
	}

	/**
	 * Report dans le sous arbre les segments qui sont valide selon le choix slectionner.
	 * @param win fenetre de windowing
	 * @param choice choix
	 * @param seg    ArrayList dans laquelle on ajoute les segments accepter.   
	 */
	private void reportInSubTree(final WindowingBox win,final Choice choice,ArrayList<Segment> seg)
	{
		// on doit report dans le sub tree 
		if(accepted(root,win,choice))
			seg.add(root);

		if(right != null
				&& right.root.getX() <= borneXsup(choice, win) ) // pour s arreter a x max 
			right.reportInSubTree(win,choice,seg);
		if(left  != null 
				&& left.root.getX()  <= borneXsup(choice, win) ) // pour s arreter a x max
			left.reportInSubTree(win,choice,seg);
	}

	/**
	 * Regarde si le point est accepter en fonction du cas dans le quel on se retrouve.
	 * Si c'est le cas il l'affiche a l'ecran.
	 * 
	 * @param seg segments a verifier.
	 * @param win fenetre de windowing
	 * @param choice choix
	 */
	public boolean accepted(final Segment seg, final WindowingBox win,final Choice choice)
	{
		switch(choice)
		{
			case CENTER:
				if (win.getXstart() <= seg.getX() && seg.getX() <= win.getXend()
					&& win.getYstart() <= seg.getY() && seg.getY() <= win.getYend())
					{
						return true;
					}break;
			case LEFT:
				if (seg.getX() <= win.getXstart()      // le premier point est a gauche de la fenetre
					&& win.getYstart() <= seg.getY() && seg.getY() <= win.getYend()
					&&win.getXstart() <= seg.getXend() 		 // le second point est dans la fenetre dans la borne y 
					&& win.getYstart() <= seg.getYend() && seg.getYend() <= win.getYend())
					{
						return true;
					}break;
		}
		return false;
	}
	/**
	 * Execute le marquage ymax
	 * @param big ymax
	 */
	public void bigMarkQy(final int big)
	{
		root.setBigMarkY(true);
		
		// on sait left < right par def de l'arbre de priorite

		// si Y a gauche est plus grand que y max alors 
		// on sait que tout les Y right seront plus grand que big par transitivite
		if ( left!= null &&  big < left.root.getY() ) left.bigMarkQy(big);
		else if(right!= null)  right.bigMarkQy(big);
	}
	/**
	 * Enleve le marquage ymax
	 */
	public void unBigMarkQy()
	{
		root.setBigMarkY(false);

		if( left!= null && left.root.isBigMarkY()) left.unBigMarkQy();
		else if(right!= null) right.unBigMarkQy();
	}
	/**
	 * Execute le marquage ymin
	 * @param small ymin
	 */
	public void smallMarkQy(final int small)
	{
		root.setSmallMarkY(true);
		
		// on sait left < right par def de l'arbre de priorite

		// si Y a gauche est plus grand que y max alors 
		// on sait que tout les Y right seront plus grand que big par transitivite
		if ( right!= null && right.root.getY() < small ) right.smallMarkQy(small);
		else if(left!= null) left.smallMarkQy(small);
	}
	/**
	 * Enleve le marquage ymin
	 */
	public void unSmallMarkQy()
	{
		root.setSmallMarkY(false);

		if( left!= null && left.root.isSmallMarkY()) left.unSmallMarkQy();
		else if(right!= null) right.unSmallMarkQy();
	}

	public PrioritySearchTree getLeft()  { return left;  }
	public PrioritySearchTree getRight() { return right; }
	public Segment getRoot() { return root; }
}