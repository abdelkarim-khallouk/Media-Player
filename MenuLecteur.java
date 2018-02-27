   import javafx.scene.layout.BorderPane;
   import javafx.scene.media.*;
   import javafx.stage.Stage;
   import javafx.scene.Scene;
   import javafx.scene.control.*;
   import javafx.event.ActionEvent;
   import javafx.event.EventHandler;
   import javafx.stage.FileChooser;
   import java.util.*; 
   import javafx.util.*; 
   import java.io.File;
   import java.net.URL;
   import java.net.MalformedURLException;
   import javafx.collections.*;
   import javafx.stage.Window;
   import javafx.geometry.*;

   public class MenuLecteur extends MenuBar { 
   
      private Menu menuFichier=new Menu("Fichier");
      private Menu menuLecture = new Menu("Lecture");   
      private MenuItem menuOuvrir=new MenuItem("Ouvrir");
      private MenuItem menuOptions = new MenuItem("Options");
      private MenuItem menuQuitter = new MenuItem("Quitter");
      private MenuItem menuMuetSon = new MenuItem("Muet/Son");
      private MenuItem menuLirePause = new MenuItem("Lecture/Pause");
      private MenuItem menuArreter=new MenuItem("Lecture/Arrêter");
      private MediaPlayer mplayer;   
      private  List playlist;
      private File fichier=new File("C:\\");
   
      public MenuLecteur (final Scene scene){
         menuFichier.getItems().addAll(menuOuvrir, menuOptions, menuQuitter);
         getMenus().addAll(menuFichier, menuLecture); 

   
         menuQuitter.setOnAction(
                                 new EventHandler<ActionEvent>(){
                                     public void handle(ActionEvent e) {
                                       mplayer.stop();
                                       mplayer.dispose();
                                       Stage st= (Stage) scene.getWindow();
                                       st.close();
                                    
                                    }
                                 });
      
         menuOuvrir.setOnAction(
                                 new EventHandler<ActionEvent>(){
                                    public void handle(ActionEvent e) {
                                       FileChooser fc = new FileChooser();
                                       fc.setInitialDirectory(fichier.getParentFile());
                                       playlist = fc.showOpenMultipleDialog(scene.getWindow());
                                       fichier = (File)playlist.get(0);
                                    
                                       String nomMedia="";
                                       try{
                                            nomMedia = fichier.toURL().toExternalForm();
                                       } catch(MalformedURLException ex){
                                          }
                                       nomMedia=nomMedia.replaceAll(" ","%20");                                    
                                       if(mplayer!=null){
                                          mplayer.stop();
                                          mplayer.dispose();
                                       }                                    
                                       Media media = new Media(nomMedia);
                                       MediaPlayer mediaPlayer=new MediaPlayer(media);
                                       MonLecteur.mediaView.setMediaPlayer(mediaPlayer);
                                       new MediaControl(mediaPlayer , scene);
                                       mplayer=mediaPlayer;
                                    }
                                 });

//insertion MenuItem "Mute", "Pause", "Arreter" sur Menu "Lecture"
         menuLecture.getItems().addAll(menuMuetSon, menuLirePause, menuArreter);      	
      	         menuArreter.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       if(mplayer.getStatus()==MediaPlayer.Status.PLAYING){ mplayer.seek(Duration.seconds(0)); mplayer.stop();} 
													   else if(mplayer.getStatus()==MediaPlayer.Status.STOPPED) mplayer.play();      
                                    }
                                 });
      	         menuMuetSon.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       mplayer.setMute(!mplayer.isMute());                                    
                                    }
                                 });

      	         menuLirePause.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       if(mplayer.getStatus()==MediaPlayer.Status.PLAYING)mplayer.pause(); 
                                          else if(mplayer.getStatus()==MediaPlayer.Status.PAUSED) mplayer.play(); 
                                  
                                    }
                                 });
      }
   }
//System.out.println("Ouvrir un Fichier...");