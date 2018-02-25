   import javafx.scene.media.MediaPlayer;
   import javafx.scene.media.MediaView;
   import javafx.scene.media.Media;
   import javafx.stage.Stage;
   import javafx.scene.Scene;
   import javafx.scene.control.MenuBar;
   import javafx.scene.control.MenuItem;
   import javafx.scene.control.Menu;
   import javafx.event.ActionEvent;
   import javafx.event.EventHandler;
   import javafx.stage.FileChooser;
   import java.io.File;
   import java.net.URL;
   import java.net.MalformedURLException;


   public class MenuControl extends MenuBar { 
      String chemin;
      private MediaPlayer mp;
   	//Scene scene;
      private Menu menuFichier=new Menu("Fichier");
      private Menu menuLecture = new Menu("Lecture");
   
      private MenuItem menuOuvrir=new MenuItem("Ouvrir");
      private MenuItem menuOptions = new MenuItem("Options");
      private MenuItem menuQuitter = new MenuItem("Quitter");
   
      public MenuControl(final MediaPlayer mp,final Scene scene) {
         this.mp = mp;
         menuFichier.getItems().addAll(menuOuvrir, menuOptions, menuQuitter);
         getMenus().addAll(menuFichier, menuLecture); 
      
         menuQuitter.setOnAction(
                                 new EventHandler<ActionEvent>(){
                                    public void handle(ActionEvent e) {
                                       mp.stop();
                                       Stage st= (Stage) scene.getWindow();
                                       st.close();
                                    
                                    }
                                 });
      
         menuOuvrir.setOnAction(
                                 new EventHandler<ActionEvent>(){//
                                    public void handle(ActionEvent e) {
                                       System.out.println("Ouvrir un Fichier...");
                                       FileChooser fc = new FileChooser();
                                       fc.setInitialDirectory(new File("C:\\"));
                                       File fichier = fc.showOpenDialog(scene.getWindow());
                                       //chemin = "file:/"+fichier.getPath();
                                       try{
                                          chemin = fichier.toURL().toExternalForm();
                                       }
                                          catch(MalformedURLException ex){
                                          }
                                       System.out.println(chemin);
                                       chemin = chemin.replaceAll(" ","%20");
                                       mp.stop();
                                       Media media = new Media(chemin);
                                       MediaPlayer mediaPlayer = new MediaPlayer(media);   
                                       MediaControl mediaControl = new MediaControl(mediaPlayer,scene);
                                    
                                       scene.setRoot(mediaControl);
                                    }
                                 });
      }
   }


