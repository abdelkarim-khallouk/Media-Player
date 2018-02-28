   package composantsFSR;
   import javafx.scene.media.*;
   import javafx.scene.canvas.*;
   import javafx.scene.paint.Color;
   import javafx.event.EventHandler;
   import javafx.scene.input.MouseEvent;
   
   public class BoutonFSR extends Canvas{
      boolean play=false;
      final double canvasWidth = 37;
      final double canvasHeight = 37; 
      final GraphicsContext gPlay = this.getGraphicsContext2D(); 
      final GraphicsContext gCercle = this.getGraphicsContext2D();  
      public BoutonFSR() {
         //Dimentioner cette Bouton
         setWidth(canvasWidth);
         setHeight(canvasWidth); 
         setOpacity(0.8);
      
         //Dessiner une cercle avec deux petits rectangles verticales làdans (Pause)
         gPlay.setFill(Color.WHITE);      
         gPlay.fillRect(12, 10, 5, 15);
         gPlay.fillRect(20, 10, 5, 15);
         gCercle.setStroke(Color.WHITE);
         gCercle.strokeOval(1,1,canvasWidth-2,canvasHeight-2);
         gCercle.strokeOval(1.5,1.5,canvasWidth-3,canvasHeight-3);
      }
   
      public void setAction(final MediaView mediaView) {
         setOnMousePressed(
                             new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e) {
                                   setOpacity(1);
                                }
                             });
         setOnMouseClicked( 
                             new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e) {
                                   setOpacity(0.8);
                                   gPlay.clearRect(8, 8, canvasWidth-15,canvasHeight-15);      //Effacer Graphis existant il sera remplacer par le nouveau 
                                   if(play){            
                                      gPlay.fillRect(12, 10, 5, 15);
                                      gPlay.fillRect(20, 10, 5, 15);
                                      MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                      if(mediaPlayer != null) mediaPlayer.play();
                                      System.out.println("Boutton_Play en etat: PAUSE");
                                   }
                                   else{
                                      gPlay.fillPolygon(new double[]{10,10,30}, new double[]{10,26,18}, 3);
                                      MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                      if(mediaPlayer != null) mediaPlayer.pause();
                                      System.out.println("Boutton_Play en etat: PLAY");
                                   }  
                                   play=!play;                                              
                                }
                             });
      }
   
   }