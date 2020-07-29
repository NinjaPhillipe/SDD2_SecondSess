package root.gui;

import javafx.scene.canvas.GraphicsContext;

/**
 * Classe qui represente une camera 2d permetant de 
 * dessiner sur un graphics context avec un decalage 
 * et un zoom
 */
public class Camera2D {
    private float zoom =1;
	private float posx =0;
    private float posy =0;
    
    private float speedCam;
    private float speedZoom;
    
    /**
     * Methode qui initialise la Camera
     */
    public Camera2D(float speedCam, float speedZoom)
    {
        this.speedCam  = speedCam;
        this.speedZoom = speedZoom;
    }

    /**
     * Applique les proprietes de la camera sur un GraphicContext
     * @param gc GraphicContext sur lequel s'applique les proprietes de la camera
     */
    public void applyOnGraphicsContext(GraphicsContext gc)
    {
        gc.scale( zoom, zoom);
        gc.translate(posx,posy);
    }

    public void translateXup()   { this.posx += speedCam; }
    public void translateXdown() { this.posx -= speedCam; }
    public void translateYup()   { this.posy -= speedCam; }
    public void translateYdown() { this.posy += speedCam; }

    public void zoomIn() { this.zoom *= 1+speedZoom; }
    public void zooOut() { this.zoom *= 1-speedZoom; }

    public void setPos(float x,float y)
    {
        this.posx = x;
        this.posy = y;
    }
}