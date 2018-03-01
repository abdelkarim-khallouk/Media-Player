   package composantsFSR;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.control.*;
   import javafx.scene.layout.*;
   import javafx.util.Duration;
   import javafx.scene.media.*;
   import javafx.beans.value.*;
   import javafx.geometry.*;

   public class SliderFSR extends StackPane{   
      public Slider slider = new Slider();
      private ProgressBar progress = new ProgressBar(0);
   
      public SliderFSR(double largeurSlider) {
         slider.setMinWidth(largeurSlider+15);
         slider.setMaxWidth(largeurSlider);
         progress.setMinWidth(largeurSlider);
         progress.setMaxWidth(largeurSlider);
      
         getChildren().addAll(progress, slider);
      }
   
      public void setLargeur (double largeur){
         largeur-=50;
         slider.setMinWidth(largeur+15);
         slider.setMaxWidth(largeur);
         progress.setMinWidth(largeur);
         progress.setMaxWidth(largeur);
      }
   
      public void setAction(final MediaView mediaView){
      
         slider.valueProperty().addListener(
                                 new ChangeListener<Number>() {
                                    public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                                       double poucentage=new_val.doubleValue()/slider.getMax();
                                       progress.setProgress(poucentage);
                                       MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                                                if(slider.isValueChanging() && mediaPlayer!=null){
                                          Duration dureeTotal = mediaPlayer.getMedia().getDuration();
                                          mediaPlayer.seek(dureeTotal.multiply(poucentage)); 
                                          }                                                  
                                    } 
                                 });
      
         slider.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       System.out.println("Valeur Slider change a: "+(int)slider.getValue());
                                    }
                                 });
      
      }
   
   }