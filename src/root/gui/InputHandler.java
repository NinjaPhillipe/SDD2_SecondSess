package root.gui;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Classe qui s'occupe de gerer les entrees clavier pour la camera
 * 
 */
public class InputHandler implements EventHandler<InputEvent>
{
	private Camera2D cam;	
	private TestProg prog;
	
	public InputHandler(TestProg prog,Camera2D camera) 
	{
		super();
		this.prog = prog;
		this.cam = camera;
	}
	
	@Override
	public void handle(InputEvent event) 
	{
        if (event.getEventType()==KeyEvent.KEY_PRESSED)
        {
            handleKey(((KeyEvent)event).getCode().getName());
		}
	}
	
	/**
	 * Execute des actions en fonction de la touche pressee
	 * @param strEvent Chaine de caractere represantant la touche pressee
	 */
	private void handleKey(String strEvent) 
	{
		switch(strEvent) 
		{
		case "Q":
			System.out.println("Zoom out");
			cam.zooOut();
			prog.setRedraw();
			break;
		case "E":
			System.out.println("Zoom int");
			cam.zoomIn();
			prog.setRedraw();
			break;
		case "W":
			cam.translateYup();
			prog.setRedraw();
			break;
		case "S":
			cam.translateYdown();
			prog.setRedraw();
			break;
		case "A":
			cam.translateXdown();
			prog.setRedraw();
			break;
		case "D":
			cam.translateXup();
			prog.setRedraw();
			break;
		}
	}
}