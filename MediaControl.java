   import javafx.scene.media.*;
   import javafx.util.Duration;
   import javafx.beans.InvalidationListener;
   import javafx.scene.Scene;
   import javafx.stage.Window;
   import javafx.beans.Observable;
   import javafx.scene.text.Text; 


//Add MediaControl Class Code
   public class MediaControl {  
      private MediaPlayer mp;
      private Duration dureeCourant, dureeTotal;																	//* * * * * * * Format Durée courante/Globale
   
      public MediaControl(final MediaPlayer mp, final Scene scene) {
         this.mp = mp;
      
         mp.setAutoPlay(true); 
         mp.setCycleCount(2);
      
         mp.currentTimeProperty().addListener(
                                 new InvalidationListener() {
                                    public void invalidated(Observable ov) {
                                       dureeCourant = mp.getCurrentTime();
                                       String temps = Format.formatTime(dureeCourant,dureeTotal);	//* * * * * * * Format Durée courante/Globale
                                       MonLecteur.textTemps.setText(temps);								//* * * * * * * Format Durée courante/Globale
                                    }
                                 });
      
      
         mp.setOnReady(        
                         new Runnable() {       
                            public void run() {      
                               dureeTotal = mp.getMedia().getDuration();									//* * * * * * * Format Durée courante/Globale
                               double largeur = mp.getMedia().getWidth();
                               double hauteur = mp.getMedia().getHeight();
                               System.out.println("Ready, Duree: "+(int)dureeTotal.toSeconds()+"s");
                               System.out.println(largeur+" , "+hauteur);
                            
                               Window window=scene.getWindow();
                            
                               if(hauteur==0){
                                  largeur = 500; hauteur = 100;												//* * * * * * * Fixation Dimention Window pour "Audio"
                               }
                               window.setWidth(largeur+40);														//* * * * * * * Adaptation Taille lecteur au video
                               window.setHeight(hauteur+80);													//* * * * * * * Adaptation Taille lecteur au video
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
