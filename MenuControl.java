   import javafx.scene.media.*;
   import javafx.scene.control.*;
   import javafx.scene.Scene;
   import javafx.stage.Stage;
   import javafx.stage.FileChooser;
   import javafx.event.ActionEvent;
   import javafx.event.EventHandler;
   import javafx.util.Duration;
   import java.io.File;
   import java.net.*;


   public class MenuControl extends MenuBar { 
      String chemin;
      private MediaPlayer mp;
      private Menu menuFichier=new Menu("Fichier");
      private Menu menuLecture = new Menu("Lecture");
   
      private MenuItem menuOuvrir=new MenuItem("Ouvrir");
      private MenuItem menuOptions = new MenuItem("Options");
      private MenuItem menuQuitter = new MenuItem("Quitter");
      private MenuItem menuMuetSon = new MenuItem("Muet/Son");	
      private MenuItem menuLirePause = new MenuItem("Lecture/Pause");
      private MenuItem menuArreter=new MenuItem("Lecture/Arrêter");

      public MenuControl(final MediaPlayer mp,final Scene scene) {
         this.mp = mp;
         getMenus().addAll(menuFichier, menuLecture);
         menuFichier.getItems().addAll(menuOuvrir, menuOptions, menuQuitter);
      
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
                                       try{
                                          chemin = fichier.toURL().toExternalForm();
                                       }
                                          catch(MalformedURLException ex){
                                          }
                                       System.out.println(chemin);
                                       chemin = chemin.replaceAll(" ","%20");
                                       mp.stop();
                                    	mp.dispose();
                                       Media media = new Media(chemin);
                                       MediaPlayer mediaPlayer = new MediaPlayer(media);   
                                       MediaControl mediaControl = new MediaControl(mediaPlayer,scene);

                                       scene.setRoot(mediaControl);
                                    }
                                 });

      
         menuLecture.getItems().addAll(menuMuetSon, menuLirePause, menuArreter);
      	
      	         menuArreter.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       if(mp.getStatus()==MediaPlayer.Status.PLAYING){ mp.seek(Duration.seconds(0)); mp.stop();}
													   else if(mp.getStatus()==MediaPlayer.Status.STOPPED) mp.play();      
                                    }
                                 });
      	         menuMuetSon.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       mp.setMute(!mp.isMute());                                    
                                    }
                                 });

      	         menuLirePause.setOnAction(     
                                 new EventHandler<ActionEvent>(){    
                                    public void handle(ActionEvent e) {
                                       if(mp.getStatus()==MediaPlayer.Status.PLAYING)mp.pause(); 
                                          else if(mp.getStatus()==MediaPlayer.Status.PAUSED) mp.play(); 
                                  
                                    }
                                 });


      }
   }


