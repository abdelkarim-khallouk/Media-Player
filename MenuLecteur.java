   import composantsFSR.*;
   import java.net.MalformedURLException;
   import javafx.event.EventHandler;
   import javafx.event.ActionEvent;
   import javafx.stage.FileChooser;
   import javafx.scene.control.*;
   import javafx.scene.media.*;
   import javafx.stage.Stage;
   import javafx.scene.Scene;
   import javafx.util.*; 
   import java.io.File;
   import java.util.*; 

   public class MenuLecteur extends MenuBar {
   
      private Menu menuFichier=new Menu("Fichier");
      private Menu menuLecture = new Menu("Lecture");   
      private MenuItem menuOuvrir=new MenuItem("Ouvrir");
      private MenuItem menuOptions = new MenuItem("Options");
      private MenuItem menuQuitter = new MenuItem("Quitter");
      private MenuItem menuMuetSon = new MenuItem("Muet/Son");
      private MenuItem menuLirePause = new MenuItem("Lecture/Pause");
      private MenuItem menuArreter = new MenuItem("Lecture/Arrêter");
      private MediaPlayer mPlayer;
      private List playlist;
      static File fichier=new File("C:\\");
   
      public MenuLecteur (final Scene scene){
         menuFichier.getItems().addAll(menuOuvrir, menuOptions, menuQuitter);
         getMenus().addAll(menuFichier, menuLecture);

         menuQuitter.setOnAction(
                                 new EventHandler<ActionEvent>(){
                                     public void handle(ActionEvent e) {
                                       mPlayer = MonLecteur.mediaView.getMediaPlayer();
                                       if(mPlayer!=null){
                                          mPlayer.stop();
                                          mPlayer.dispose();
                                       }     
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
                                       mPlayer = MonLecteur.mediaView.getMediaPlayer();                                  
                                       if(mPlayer!=null){
                                          mPlayer.stop();
                                          mPlayer.dispose();
                                       }                                    
                                       Media media = new Media(nomMedia);
                                       mPlayer = new MediaPlayer(media);
                                       MonLecteur.mediaView.setMediaPlayer(mPlayer);
                                       mPlayer.setVolume((double) VolumeFSR.volumeProgress.getValeur() / 10);
                                       new MediaControl(mPlayer , scene);
                                    }
                                 });

//insertion MenuItem "Mute", "Pause", "Arreter" sur Menu "Lecture"
         menuLecture.getItems().addAll(menuMuetSon, menuLirePause, menuArreter);       
                  menuArreter.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       mPlayer = MonLecteur.mediaView.getMediaPlayer();
                                       if(mPlayer.getStatus()==MediaPlayer.Status.PLAYING){ mPlayer.seek(Duration.seconds(0)); mPlayer.stop();} 
                                          else if(mPlayer.getStatus()==MediaPlayer.Status.STOPPED) mPlayer.play();      
                                    }
                                 });
                  menuMuetSon.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       mPlayer = MonLecteur.mediaView.getMediaPlayer();
                                       mPlayer.setMute(!mPlayer.isMute());                                    
                                    }
                                 });

                  menuLirePause.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       mPlayer = MonLecteur.mediaView.getMediaPlayer();
                                       if(mPlayer.getStatus()==MediaPlayer.Status.PLAYING)mPlayer.pause(); 
                                          else if(mPlayer.getStatus()==MediaPlayer.Status.PAUSED) mPlayer.play();                                  
                                    }
                                 });
      }
   }
//System.out.println("Ouvrir un Fichier...");