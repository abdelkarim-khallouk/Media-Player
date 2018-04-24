   import composantsFSR.*;
   import java.net.MalformedURLException;
   import javafx.event.EventHandler;
   import javafx.event.ActionEvent;
   import javafx.stage.FileChooser;
   import javafx.scene.control.*;
   import javafx.scene.media.*;
   import javafx.collections.*;
   import javafx.stage.Stage;
   import javafx.scene.Scene;
   import javafx.util.*;
   import java.io.File;
   import java.util.*; 

   /**
    *   
    *   Author: Abdelkarim KHALLOUK
    *   URI: https://github.com/abdelkarim-khallouk
    *   Email: ab.khallouk@gmail.com
    *
     * */


   public class MenuLecteur extends MenuBar {
      static File fichier=new File("C:\\");  
      private Menu menuFichier=new Menu("Fichier");
      private Menu menuLecture = new Menu("Lecture");   
      private MenuItem menuOuvrir=new MenuItem("Ouvrir");
      private MenuItem menuOptions = new MenuItem("Options");
      private MenuItem menuQuitter = new MenuItem("Quitter");
      private MenuItem menuMuetSon = new MenuItem("Muet/Son");
      private MenuItem menuLirePause = new MenuItem("Lecture/Pause");
      private MenuItem menuArreter = new MenuItem("Lecture/Arrêter");
      private MediaPlayer mPlayer;
      static  List playlistFile;
      static double HEIGHT = 24;    
   
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
                                       CaptureCoverFSR.saveID();
                                       st.close();
                                    }
                                 });
      
         menuOuvrir.setOnAction(
                                 new EventHandler<ActionEvent>(){
                                    public void handle(ActionEvent e) {
                                       FileChooser fc = new FileChooser();
                                       fc.setInitialDirectory(fichier.getParentFile());
                                       playlistFile = fc.showOpenMultipleDialog(scene.getWindow());
                                       MonLecteur.playlist.setItems(FXCollections.observableArrayList(playlistFile));
                                       fichier = (File)playlistFile.get(0);
                                       MonLecteur.playlist.getSelectionModel().select(0);

                                       String nomMedia="";
                                       try{
                                            nomMedia = fichier.toURL().toExternalForm();
                                       } catch(MalformedURLException ex){
                                          }
                                       nomMedia=nomMedia.replaceAll(" ","%20");
                                       mPlayer = MonLecteur.mediaView.getMediaPlayer();


                                       boolean ConnexionInterompue=false;
                                       if(mPlayer!=null && mPlayer.getMedia().getSource().indexOf("http://")==0 && !SearchBar.siConnected())
                                           ConnexionInterompue=true;
                                       if(mPlayer!=null && !ConnexionInterompue){//Si la connexion est interompue lors de la lecture il ne faut pas diposer le mPlayer
                                          mPlayer.stop();
                                          mPlayer.dispose();
                                       }                                    
                                       Media media = new Media(nomMedia);
                                       mPlayer = new MediaPlayer(media);
                                       MonLecteur.mediaView.setMediaPlayer(mPlayer);
                                       mPlayer.setVolume((double) VolumeFSR.volumeProgress.getValeur() / 10);
                                       MonLecteur.mediaPane.getChildren().setAll(MonLecteur.mediaView);
                                       new MediaControl(mPlayer , scene);
                                       SearchBar.isCOVER = false;
                                       MonLecteur.captureImage.setVisible(true);
                                       System.out.println("Ouverture du Media depuis Menu...");
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