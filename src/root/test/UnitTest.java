package root.test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import root.main.Segment;
import root.main.WindowingBox;
import root.main.PrioritySearchTree;

public class UnitTest {

    @Test
    public void method()
    {
        // org.junit.Assert.assertTrue( new ArrayList().isEmpty() );
        assertEquals( 1,1 );
    }

    @Test
    public void buildTree()
    {
        ArrayList<Segment> al = new ArrayList<Segment>();

        // precondition plus petit d'abord et trie en fonction d y respecter
        Segment seg1 = new Segment(8,-2,8,3);
        Segment seg2 = new Segment(5,3,7,3);
        Segment seg3 = new Segment(6,4,7,3);
        Segment seg4 = new Segment(4,5,6,5);
        Segment seg5 = new Segment(3,6,9,6);
        Segment seg6 = new Segment(2,7,8,7);
        Segment seg7 = new Segment(23,8,2,12);


        al.add(seg1);
        al.add(seg2);
        al.add(seg3);
        al.add(seg4);
        al.add(seg5);
        al.add(seg6);
        al.add(seg7);

        // PrioritySearchTree tree = new PrioritySearchTree(al);
        
        // assertEquals(tree.getRoot(),seg6);

        // //sous arbre gauche 
        // assertEquals(tree.getLeft().getRoot(),seg2);
        // assertEquals(tree.getLeft().getLeft().getRoot(),seg1);
        // assertEquals(tree.getLeft().getRight().getRoot(),seg3);

        // //sous arbre droit
        // assertEquals(tree.getRight().getRoot(),seg5);
        // assertEquals(tree.getRight().getLeft().getRoot(),seg4);
        // assertEquals(tree.getRight().getRight().getRoot(),seg7);


        // // test small mark Qy
        // tree.smallMarkQy(6);

        // assertTrue(tree.getRoot().isSmallMarkY());
        // assertTrue(tree.getLeft().getRoot().isSmallMarkY());
        // assertTrue(tree.getLeft().getRight().getRoot().isSmallMarkY());

        // assertFalse(tree.getRight().getRoot().isSmallMarkY());
        // assertFalse(tree.getRight().getLeft().getRoot().isSmallMarkY());
        // assertFalse(tree.getRight().getRight().getRoot().isSmallMarkY());

        // assertFalse(tree.getLeft().getLeft().getRoot().isSmallMarkY());

        // // test unmark Qy
        // tree.unSmallMarkQy();

        // assertFalse(tree.getRoot().isSmallMarkY());
        // assertFalse(tree.getLeft().getRoot().isSmallMarkY());
        // assertFalse(tree.getLeft().getRight().getRoot().isSmallMarkY());

        // assertFalse(tree.getRight().getRoot().isSmallMarkY());
        // assertFalse(tree.getRight().getLeft().getRoot().isSmallMarkY());
        // assertFalse(tree.getRight().getRight().getRoot().isSmallMarkY());

        // assertFalse(tree.getLeft().getLeft().getRoot().isSmallMarkY());

        // //test big mark Qy

        // tree.bigMarkQy(2);
        // assertTrue(tree.getRoot().isBigMarkY());
        // assertTrue(tree.getLeft().getRoot().isBigMarkY());
        // assertTrue(tree.getLeft().getRight().getRoot().isBigMarkY());

        // assertFalse(tree.getRight().getRoot().isBigMarkY());
        // assertFalse(tree.getRight().getLeft().getRoot().isBigMarkY());
        // assertFalse(tree.getRight().getRight().getRoot().isBigMarkY());

        // assertFalse(tree.getLeft().getLeft().getRoot().isBigMarkY());


        // tree.unBigMarkQy();

        // assertFalse(tree.getRoot().isBigMarkY());
        // assertFalse(tree.getLeft().getRoot().isBigMarkY());
        // assertFalse(tree.getLeft().getRight().getRoot().isBigMarkY());

        // assertFalse(tree.getRight().getRoot().isBigMarkY());
        // assertFalse(tree.getRight().getLeft().getRoot().isBigMarkY());
        // assertFalse(tree.getRight().getRight().getRoot().isBigMarkY());

        // assertFalse(tree.getLeft().getLeft().getRoot().isBigMarkY());


        // // test accepted 
        // WindowingBox box = new WindowingBox(10, 30, 20, 40);
        // Segment segCenter = new Segment(12,32,18,32);
        // assertTrue(tree.accepted(segCenter,box,PrioritySearchTree.Choice.CENTER));
        // assertFalse(tree.accepted(segCenter,box,PrioritySearchTree.Choice.LEFT));
        // // assertFalse(tree.accepted(segCenter,box,PrioritySearchTree.Choice.DOWN));

        // // test de segments etant dans la bande gauche avec un deuxieme point qui a traverser la fenetre
        // Segment segLeftOK = new Segment(7,32,30,32);
        // assertFalse(tree.accepted(segLeftOK,box,PrioritySearchTree.Choice.CENTER));
        // assertTrue(tree.accepted(segLeftOK,box,PrioritySearchTree.Choice.LEFT));
        // // assertFalse(tree.accepted(segLeftOK,box,PrioritySearchTree.Choice.DOWN));

        // //test segment trop petit pour avoir le deuxieme pouint dans la fenetre
        // Segment segLeftShort = new Segment(7,32,9,32);
        // assertFalse(tree.accepted(segLeftShort,box,PrioritySearchTree.Choice.CENTER));
        // assertFalse(tree.accepted(segLeftShort,box,PrioritySearchTree.Choice.LEFT));
        // // assertFalse(tree.accepted(segLeftShort,box,PrioritySearchTree.Choice.DOWN));

        // //test segment dans la bande left mais vertical
        // Segment segLeftVert = new Segment(9,32,9,68);
        // assertFalse(tree.accepted(segLeftVert,box,PrioritySearchTree.Choice.CENTER));
        // assertFalse(tree.accepted(segLeftVert,box,PrioritySearchTree.Choice.LEFT));
        // // assertFalse(tree.accepted(segLeftVert,box,PrioritySearchTree.Choice.DOWN));

        // //test segment dans la bande down avec un deuxieme point qui a traverser la fenetre
        // Segment segDownOK = new Segment(12,15,12,40);
        // assertFalse(tree.accepted(segDownOK,box,PrioritySearchTree.Choice.CENTER));
        // assertFalse(tree.accepted(segDownOK,box,PrioritySearchTree.Choice.LEFT));
        // // assertTrue(tree.accepted(segDownOK,box,PrioritySearchTree.Choice.DOWN));

        // //test segment dans la bande down mais trop petit
        // Segment segDownShort = new Segment(12,15,12,19);
        // assertFalse(tree.accepted(segDownShort,box,PrioritySearchTree.Choice.CENTER));
        // assertFalse(tree.accepted(segDownShort,box,PrioritySearchTree.Choice.LEFT));
        // // assertFalse(tree.accepted(segDownShort,box,PrioritySearchTree.Choice.DOWN));

        // //test segment dans la bande down mais trop petit
        // Segment segDownHor= new Segment(12,19,90,19);
        // assertFalse(tree.accepted(segDownHor,box,PrioritySearchTree.Choice.CENTER));
        // assertFalse(tree.accepted(segDownHor,box,PrioritySearchTree.Choice.LEFT));
        // assertFalse(tree.accepted(segDownHor,box,PrioritySearchTree.Choice.DOWN));

    }
}