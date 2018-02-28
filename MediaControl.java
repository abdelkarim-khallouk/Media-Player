   import javafx.beans.InvalidationListener;
   import javafx.beans.Observable;
   import javafx.scene.text.Text;
   import javafx.util.Duration;
   import javafx.scene.media.*;
   import javafx.stage.Window;
   import javafx.scene.Scene;
   import java.io.File;


   public class MediaControl {  
      private MediaPlayer mp;
      static Duration dureeCourant, dureeTotal; 
   
      public MediaControl(final MediaPlayer mp, final Scene scene) {
         this.mp = mp;
      
         mp.setAutoPlay(true); 
         mp.setCycleCount(2);
      
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

                               System.out.println("Nom: "+nomMedia+"\nSize: "+(int)largeur+","+(int)hauteur+"\nDuree: "+(int)dureeTotal.toSeconds()+"s"+"\nVolume: "+mp.getVolume());                              
                            }
                         });
      
         mp.setOnEndOfMedia(        
                           
                              new Runnable() {
                                 public void run() {      
                                 
                                    System.out.println("Fin");
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
