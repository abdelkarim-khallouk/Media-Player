   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.image.ImageView;
   import javafx.scene.image.Image;
   import javafx.scene.control.*;
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


   public class VolumeBar extends HBox{
      private ImageView iconVolume = new ImageView("./img/mute-1.png");
      private Label [] volBar=  new Label[10];
      private Label l = new Label("  ");
      public static int niveauVolume = 10;
   
      public VolumeBar(final MediaView mediaView) {
         iconVolume.setId("mute");
         setSpacing(4);
         setAlignment(Pos.CENTER);
         setStyle("-fx-background-color: black;");    
         getChildren().addAll(iconVolume);
      
         for (int i=0; i<10; i++){          
            final Label item = volBar[i]=new Label("  ");
            item.setStyle("-fx-background-radius: 0.24em;");  
            final int index = i; 
            getChildren().add(volBar[i]);
         
            if(i<4) volBar[i].setId("volume-1");
            else volBar[i].setId("volume-2");
         
            item.setOnMouseClicked( 
                                    new EventHandler<MouseEvent>(){
                                       public void handle(MouseEvent e) {
                                          if(niveauVolume == 0) iconVolume.setImage(new Image("./img/mute-1.png"));
                                          niveauVolume = index+1;
                                          System.out.println("Niveau de Volume est = "+niveauVolume);
                                       
                                          for (int j=0; j<10; j++){ 
                                             if(j<index+1) volBar[j].setId("volume-1");
                                             else volBar[j].setId("volume-2");
                                          }
                                          MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                          if (mediaPlayer!=null) 
                                                mediaPlayer.setVolume((double) niveauVolume / 10);
                                       }
                                    }); 
         }      
      
         iconVolume.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                    
                                       if(niveauVolume<10){
                                          volBar[niveauVolume].setId("volume-1");
                                          niveauVolume++;  
                                       }
                                       else if(niveauVolume==10){
                                          niveauVolume=0;
                                          iconVolume.setImage(new Image("./img/mute-2.png"));
                                          for (int i=0; i<10; i++) 
                                             volBar[i].setId("volume-2");
                                       }
                                       if(niveauVolume == 1) iconVolume.setImage(new Image("./img/mute-1.png"));
                                          MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                          if (mediaPlayer!=null) 
                                                mediaPlayer.setVolume((double) niveauVolume / 10);
                                       System.out.println("Niveau de Volume est = "+niveauVolume);

                                    }
                                 });
      }
   }

