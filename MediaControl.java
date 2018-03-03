   import javafx.beans.InvalidationListener;
   import java.net.MalformedURLException;
   import javafx.beans.Observable;
   import javafx.scene.text.Text;
   import javafx.util.Duration;
   import javafx.scene.media.*;
   import javafx.stage.Window;
   import javafx.scene.Scene;
   import java.io.File;


   public class MediaControl {  
      static Duration dureeCourant, dureeTotal;
      private MediaPlayer mp;
      //int k = 0;
   
      public MediaControl(final MediaPlayer mp, final Scene scene) {
         this.mp = mp;
      
         mp.setAutoPlay(true); 
        //mp.setCycleCount(2);
      
         mp.currentTimeProperty().addListener(
                                 new InvalidationListener() {
                                    public void invalidated(Observable ov) {
                                       dureeCourant = mp.getCurrentTime();
                                       String temps = Format.formatTime(dureeCourant,dureeTotal);
                                       MonLecteur.mediaBar.textTemps.setText(temps);                                         //* * * * * * * Insertion Temps Ecoulé du Media
                                       MonLecteur.mediaBar.sliderFSR.slider.setValue(dureeCourant.divide(dureeTotal).toMillis()*100);  //* * * * * * * Activation de la Progression
                                    }
                                 });
      
      
         mp.setOnReady(        
                         new Runnable() {       
                            public void run() {      
                               dureeTotal = mp.getMedia().getDuration();
                               double largeur = mp.getMedia().getWidth()+40;
                               double hauteur = mp.getMedia().getHeight()+80;
                            
                               Window window=scene.getWindow();
                            
                               if(hauteur==0){
                                  largeur = 500; hauteur = 100;
                               }
                               window.setWidth(largeur); 
                               window.setHeight(hauteur);

                              //* * * * * * * Adaptation Taille MediaBar au Media
                               MonLecteur.mediaBar.setLargeurSlider(largeur);

                               //* * * * * * * Adaptation Taille SearchBar au Media
                               MonLecteur.searchBar.setLargeurSearchBar(largeur);

                              //* * * * * * * Affichage Nom du Media
                               String nomMedia = mp.getMedia().getSource();
                               File fichier=new File(nomMedia);
                               nomMedia = fichier.getName();
                               nomMedia = nomMedia.replaceAll("%20"," ");
                               MonLecteur.mediaBar.textNom.setText(nomMedia);

                               if(SearchBar.alertMSG!=null)
                                  SearchBar.alertMSG.setCenterMSG(window);

                               System.out.println("Nom: "+nomMedia+"\nTaille: "+(int)largeur+","+(int)hauteur+"\nDuree: "+(int)dureeTotal.toSeconds()+"s"+"\nVolume: "+mp.getVolume());                              
                            }
                         });
      
         mp.setOnEndOfMedia(        
                           
                              new Runnable() {
                                 public void run() {
                                    MediaPlayer mPlayer = MonLecteur.mediaView.getMediaPlayer();   
              
                                    int k = MonLecteur.playlist.getSelectionModel().getSelectedIndex();    
                                    k++;
                                    if(k<MenuLecteur.playlistFile.size()){
                                       MonLecteur.playlist.getSelectionModel().selectNext();
                                       MonLecteur.playlist.getSelectionModel().clearSelection(k-1);
                                       File fichier = (File)MenuLecteur.playlistFile.get(k);
                                       String nomMedia="";
                                       try{
                                            nomMedia = fichier.toURL().toExternalForm();
                                       } catch(MalformedURLException ex){
                                          }
                                       nomMedia=nomMedia.replaceAll(" ","%20");
                                      if(mPlayer!=null){
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
                                       System.out.println("\n\nOuverture du Media N°"+k+" depuis PlayList...");
                                    }
                                 else{
                                   mPlayer.stop();
                                   MonLecteur.mediaBar.sliderFSR.slider.setValue(0);
                                   MonLecteur.mediaBar.textNom.setText("");
                                   MonLecteur.mediaBar.textTemps.setText("");
                                   }               
                                 }
                              });
      
         mp.setOnPlaying(          
                           new Runnable() {       
                              public void run() {    
                                 System.out.println("Play");
                              }
                           });
      
         mp.setOnRepeat(        
                          new Runnable() {
                             public void run() {      
                                System.out.println("Repeat");
                             }
                          });
         mp.setOnPaused(     
                          new Runnable() {       
                             public void run() {    
                                System.out.println("Pause");}
                          });     
         mp.setOnStopped(       
                           new Runnable() {      
                              public void run() {
                                 System.out.println("Stop");
                              }
                           });
      }
   }
