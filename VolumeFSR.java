   import composantsFSR.*;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.image.ImageView;
   import javafx.scene.image.Image;
   import javafx.scene.layout.*;
   import javafx.scene.media.*;
   import javafx.geometry.*;

   /**
    *   
    *   Author: Abdelkarim KHALLOUK
    *   URI: https://github.com/abdelkarim-khallouk
    *   Email: ab.khallouk@gmail.com
    *
     * */


   public class VolumeFSR extends HBox{
      static ProgressBarFSR volumeProgress = new ProgressBarFSR("volume" , 10);
      private ImageView iconVolume = new ImageView("./img/mute-1.png");
      int saveVolume = 0;
      public VolumeFSR(final MediaView mediaView) {
         iconVolume.setId("mute");
         volumeProgress.setAlignment(Pos.CENTER);  
         volumeProgress.setValeur(5);
         setAlignment(Pos.CENTER);
         setSpacing(4);
         getChildren().addAll(iconVolume, volumeProgress);
      
         volumeProgress.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                       if(volumeProgress.getValeur()!=0){ iconVolume.setImage(new Image("./img/mute-1.png"));
                                          iconVolume.setOpacity(1);
                                       
                                       
                                          if (mediaPlayer!=null){
                                             mediaPlayer.setVolume((double) volumeProgress.getValeur() / 10);
                                             mediaPlayer.setMute(false);
                                          }
                                       }
                                    }
                                 });
      
         iconVolume.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                       if(mediaPlayer!=null){
                                          if(mediaPlayer.isMute()){
                                             iconVolume.setImage(new Image("./img/mute-1.png"));
                                             iconVolume.setOpacity(1);
                                             volumeProgress.setValeur(saveVolume);
                                             System.out.println("Lecteur en Etat: Son");
                                          }
                                          else {
                                             iconVolume.setImage(new Image("./img/mute-2.png"));
                                             iconVolume.setOpacity(0.7);
                                             saveVolume = volumeProgress.getValeur();
                                             volumeProgress.setValeur(0); 
                                             System.out.println("Lecteur en Etat: Muet");
                                          }
                                          mediaPlayer.setMute(!mediaPlayer.isMute());
                                       }
                                    }
                                 });
      }
   
      public double getVolume(){
         return (double) volumeProgress.getValeur();
      }
   
      public void setVolume(double valeur){
         volumeProgress.setValeur((int) valeur);
      }
   }

