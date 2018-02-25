   import javafx.scene.layout.BorderPane;
   import javafx.scene.media.MediaPlayer;
   import javafx.scene.media.MediaView;
   import javafx.util.Duration;
   import javafx.beans.InvalidationListener;
   import javafx.beans.Observable;
   import javafx.scene.text.Text; 


//Add MediaControl Class Code

   public class MediaControl extends BorderPane {   
      private MediaPlayer mp;
      private MediaView mediaView;
      private Text text;
   
      public MediaControl(final MediaPlayer mp) {
         this.mp = mp;
         mediaView = new MediaView(mp);
         getChildren().add(mediaView);//setCenter(mediaView)
         text=new Text(50, 300, "");
         getChildren().add(text);
      
      
         mp.currentTimeProperty().addListener(
                                 new InvalidationListener() {
                                    public void invalidated(Observable ov) {
                                       text.setText((int)mp.getCurrentTime().toSeconds()+"s");
                                    }
                                 });
      
         mp.setOnReady(        
                         new Runnable() {       
                            public void run() {      
                               System.out.println("Ready, Duree: "+(int)mp.getMedia().getDuration().toSeconds()+"s");
                               System.out.println(mp.getMedia().getWidth()+" , "+mp.getMedia().getHeight());
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
      }
   }



/*
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
      */