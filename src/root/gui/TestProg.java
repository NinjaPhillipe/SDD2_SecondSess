package root.gui;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;

import root.main.MainClass;
import root.main.WindowingBox;
import root.main.Segment;

/*
 * Fenetre principale de l'application
 */
public class TestProg extends Application 
{
	private final int HEIGHT = 600;
	private final int WIDTH  = 800;
	
	private VBox vbox_menu;
	private HBox hbox_win_settings;
	
	// text field
	private TextField textfield_x_start;
    private TextField textfield_y_start;
    private TextField textfield_x_end;
    private TextField textfield_y_end;
    
	private boolean redraw = true;
	
	private boolean debugMode = false;
    
    private MainClass mainClass;
    private Stage stage;
	private Camera2D camera;

	// TEST TEST
	public boolean drawALL = false;
	
	@Override
	public void start(final Stage stage) {
		this.stage = stage;
		// initialise les deux racines 
        final Group root = new Group();		
		final Group sub_root = new Group();
        // affecte une scene a chaque racine pour permettre l'affichage de plusieurs couche
        final Scene sceneRoot = new Scene(root, WIDTH, HEIGHT);
		final SubScene sub_scene = new SubScene(sub_root, WIDTH, HEIGHT);

		// initialisation du canvas
		final Canvas canvas = new Canvas(WIDTH, HEIGHT);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(0.1f);
		
		sub_root.getChildren().add(canvas);

        // initialise la camera pour pouvoir se deplacer dans le canvas
		camera = new Camera2D(20,0.2f);
       
 		// assoscie la racine avec la sub_scene
        root.getChildren().add(sub_scene);
        
        stage.setTitle("Windowing Segment");
        
        // initialise la bar de menu
        vbox_menu = new VBox(initMenuBar()); 
        
        // initialise la boite pour parametrer le fenetrage et la rend invisible
        hbox_win_settings = initVboxWindowingSettings();
        hbox_win_settings.setVisible(false);
 		
 		// ajoute la vbox du menu a la racine de la scene
        root.getChildren().add(vbox_menu);  
        root.getChildren().add(hbox_win_settings); 

        // affecte la scene principale au stage 
		stage.setScene(sceneRoot);
		
		// affecte le systeme d'input handler a la scene principale
		sceneRoot.setOnKeyPressed(new InputHandler(this,camera));
		
		// affiche le stage
		stage.show();
		
		// creation d'un AnimationTimer afin de redessiner la scene lorsque cela est necessaire
        final AnimationTimer animator = new AnimationTimer(){
            @Override
            public void handle(final long arg0) {
				// redessine la scene si elle en a besoin

				// pour pouvoir clear le canvas
				if(redraw && mainClass == null)
				{
					// clear la scene pour ne pas avoir de superposition
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					gc.save();
				}else if(redraw && mainClass != null) {
					
					// clear la scene pour ne pas avoir de superposition
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					gc.save();
					
					camera.applyOnGraphicsContext(gc);

					gc.setStroke(Color.BLACK   );
					
					System.out.println("\nDraw windowed Segments");
					long startTime = System.currentTimeMillis();
					// cherche les segments a afficher.
					
					WindowingBox box = mainClass.getWindowingBox();

					if(debugMode && drawALL)
					{
						for (Segment seg : mainClass.ALLSEG)
						{
							gc.strokeLine(seg.getX(), seg.getY() ,
								seg.getXend(), seg.getYend() );
						
						}
					}
					else
					{
						for (Segment seg : mainClass.computeTree())
						{
							gc.strokeLine(Math.max(seg.getX(),box.getXstart() ), Math.max(seg.getY(),box.getYstart()) ,
								Math.min(seg.getXend(),box.getXend() ), Math.min(seg.getYend(),box.getYend() ));
						}
					}



					long elapsed = System.currentTimeMillis()-startTime;
					System.out.println("Temps de recherche dans l'arbre plus draw "+elapsed+" ms");
					
					// affiche les bordure de la fenetre
					gc.setStroke(Color.RED);
					gc.strokeLine(box.getXstart(),box.getYstart(),box.getXend()  ,box.getYstart());
					gc.strokeLine(box.getXstart(),box.getYend()  ,box.getXend()  ,box.getYend());
					gc.strokeLine(box.getXstart(),box.getYstart(),box.getXstart(),box.getYend());
					gc.strokeLine(box.getXend()  ,box.getYstart(),box.getXend()  ,box.getYend());
					
					
        			/// END DRAW BOX
					redraw = false;

					gc.restore();
				}
            }      
        };
        animator.start(); 
	}
	
	/**
	 * Methode qui initialise la barre de menu.
	 * @return MenuBar 
	 */
	private MenuBar initMenuBar() {
		final MenuBar res = new MenuBar();

		final Menu m = new Menu("Menu");
		
		// initialisation du bouton permettant l'affichage de la fenetre de parametrage
		final MenuItem m1 = new MenuItem("Parametre Boite de fenetrage");
		m1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent t) {
				if(mainClass != null)
				{
					System.out.println("WINDOWING SETTINGS");
					hbox_win_settings.setVisible(true);
				}else
				{
					System.out.println("NO FILE LOADED");

					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Missing file");
					alert.setContentText("please choose a correct file!");

					alert.showAndWait();
				}
			}
		} );
		
		final MenuItem m2 = new MenuItem("Centrer sur la boite de fenetre");
		m2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent t) {
				System.out.println("Center ");
				if(mainClass != null)
				{
					WindowingBox tmp = mainClass.getWindowingBox();
					if(tmp.getXstart() != tmp.MIN_VALUE && tmp.getXstart() != tmp.MIN_VALUE )
						camera.setPos(-tmp.getXstart(), -tmp.getYstart());
					else
						camera.setPos(0, 0);
					setRedraw();
				}else
				{
					System.out.println("NO FILE LOADED");

					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Missing file");
					alert.setContentText("please choose a correct file!");

					alert.showAndWait();

				}	
			}
		} );

		final MenuItem m3 = new MenuItem("Choisir fichier");
		m3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent t) {
				System.out.println("Choose file");
				final FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
				fileChooser.setTitle("Selectionner un fichier");
				final File file = fileChooser.showOpenDialog(stage);
				if(file != null) // si aucun fichier n est choisi
				{
					mainClass = new MainClass(file.getAbsolutePath(),debugMode);
					redraw = true;
				}
			}
		} );

		final CheckMenuItem m4 = new CheckMenuItem("DebugMode");
		m4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				debugMode = !debugMode;
				if(debugMode)
				{
					System.out.println("Debug Mode Enabled!");
					mainClass = null;
					redraw = true;
				}
				else 
					System.out.println("Debug Mode Disabled!");

				// reset
				drawALL = false;

				if(mainClass != null)
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					if(debugMode)
						alert.setHeaderText("Debug mode nabled");
					else
						alert.setHeaderText("Debug mode disabled");
					alert.setContentText("you need to reload the file to change debug mode state!");

					alert.showAndWait();
					mainClass = null;
					redraw = true;
				}
			}
		});
		
		// ajoute les boutons au menu
		m.getItems().add(m1);
		m.getItems().add(m2);
		m.getItems().add(m3);
		m.getItems().add(m4);

		// ajoute le menu a la MenuBar
		res.getMenus().add(m);
		
		return res;
	}	
	
	/*
	 * Initialise la boite pour parametrer le fenetrage des segments
	 */
	private HBox initVboxWindowingSettings() {
		final GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: bisque;");
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
	    grid.setAlignment(Pos.CENTER); 
		
		final HBox res = new HBox();
		
		// initialise les champs de nombre
		textfield_x_start = initNumberField();
        textfield_y_start = initNumberField();
        textfield_x_end   = initNumberField();
		textfield_y_end   = initNumberField();
        
        final Button btnAdd = new Button("OK");
        
        // Decrit l'action lors d'un appui sur le bouton ok
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent t) {
				
				final WindowingBox tmpBox = mainClass.getWindowingBox();

				System.out.println("Validate windowing settings");
				String tmp = textfield_x_start.getText();
				if(tmp.isEmpty()) // si le champ est vide mettre a l'infini
					tmpBox.setStartXinfty();
				else // sinon mettre la valeur contenue	
					tmpBox.set_start_x(Integer.parseInt(tmp));

				tmp = textfield_x_end.getText();
				if(tmp.isEmpty()) // si le champ est vide mettre a l'infini
					tmpBox.setEndXinfty();
				else // sinon mettre la valeur contenue	
					tmpBox.set_end_x(Integer.parseInt(tmp));

				tmp = textfield_y_start.getText();
				if(tmp.isEmpty()) // si le champ est vide mettre a l'infini
					tmpBox.setStartYinfty();
				else // sinon mettre la valeur contenue	
					tmpBox.set_start_y(Integer.parseInt(tmp));

				tmp = textfield_y_end.getText();
				System.out.println(tmp);
				if(tmp.isEmpty()) // si le champ est vide mettre a l'infini
					tmpBox.setEndYinfty();
				else // sinon mettre la valeur contenue	
					tmpBox.set_end_y(Integer.parseInt(tmp));

				hbox_win_settings.setVisible(false);
				redraw = true;
			}
		} );
        
		grid.add(new Label("X start :"),0,0);
		grid.add(textfield_x_start,1,0);
		
		grid.add(new Label("Y start :"),2,0);
		grid.add(textfield_y_start,3,0);
		
		grid.add(new Label("X end  :"),0,1);
		grid.add(textfield_x_end,1,1);
		
		grid.add(new Label("Y end  :"),2,1);
		grid.add(textfield_y_end,3,1);
		
		grid.add(btnAdd,0,2);
		
		res.getChildren().add(grid);
		
		return res;
	}
	
	/**
	 * Methode qui initialise un textfield pour qu il ne puisse
	 * accepter que les entiers signes.
	 * 
	 * @Return TextField un textfield qui n'accepte que les chiffres
	 */
	private TextField initNumberField() {
		final TextField res = new TextField();
        res.setPrefWidth(60);
        
        // utilise une expression reguliere afin de n autoriser que les chiffres a l'interieur du champ de texte
        res.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                // utilisation d'une expression reguliere afin de n'accepter que les entiers signes
            	if (!newValue.matches("^-?[0-9]*$"))
                	res.setText(oldValue);
            }
        });
        return res;
	}
	
	public boolean getRedraw()    { return this.redraw; }
	public void setRedraw()       { this.redraw = true; }

	public boolean getDebugMode() { return this.debugMode; }
	
	public static void main(final String[] args) { launch(args); }
}