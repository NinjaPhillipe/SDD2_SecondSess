package root.test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import root.main.Segment;
import root.main.WindowingBox;
import root.main.MainClass;
import root.main.PrioritySearchTree;

public class UnitTest {

    @Test
    public void method()
    {
        // org.junit.Assert.assertTrue( new ArrayList().isEmpty() );
        assertEquals( 1,1 );
    }

    private boolean checkInTree(PrioritySearchTree tree,Segment seg)
    {
        if(tree == null)               return false;
        if(tree.getRoot().equals(seg)) return true;

        return checkInTree(tree.getLeft(), seg) || checkInTree(tree.getRight(), seg);
    }

    @Test
    public void buildTree()
    {   
        MainClass mainclass = new MainClass("../testFile/segment.txt",false);

        // assert que les segments horizontal sont bien dans l arbre hori
        assertTrue(checkInTree(mainclass.getTreeHor(),new Segment(0,3,6,3)));
        assertTrue(checkInTree(mainclass.getTreeHor(),new Segment(3,2,6,2)));
        assertTrue(checkInTree(mainclass.getTreeHor(),new Segment(1,6,3,6)));

        // assert que les segments vertical sont bien dans l arbre verti en ayant effectuer la rotation
        assertTrue(checkInTree(mainclass.getTreeVer(),new Segment(3,3,5,3)));
        assertTrue(checkInTree(mainclass.getTreeVer(),new Segment(0,4,6,4)));
        assertTrue(checkInTree(mainclass.getTreeVer(),new Segment(1,1,4,1)));
        

        // test de la recherche 
        mainclass.setWindowingBox(new WindowingBox(2,1,5,4));
        ArrayList<Segment> res = mainclass.computeTree();

        assertTrue(res.contains(new Segment(0,3,6,3)));
        assertTrue(res.contains(new Segment(3,2,6,2)));
        assertTrue(res.contains(new Segment(3,3,3,5)));
        assertTrue(res.contains(new Segment(4,0,4,6)));

        assertFalse(res.contains(new Segment(1,1,1,4)));
        assertFalse(res.contains(new Segment(1,6,3,6)));
    }
}