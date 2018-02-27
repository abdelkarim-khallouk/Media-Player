   import javafx.scene.control.*;
   import javafx.scene.layout.*;
   import javafx.beans.value.*;
   import javafx.scene.text.*;
   import javafx.geometry.*;
   import javafx.scene.Scene;
   import javafx.scene.paint.Color;
   import javafx.scene.canvas.*;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;

   public class MediaBar extends VBox{
   
      static Text textTemps;
      static Text textNom;
      static Slider slider = new Slider();
      ProgressBar progress = new ProgressBar(0);
      boolean play=false;
   
      public MediaBar(final Scene scene) {
         double largeurSlider = scene.getWidth()-50;
         slider.setMinWidth(largeurSlider+15);
         slider.setMaxWidth(largeurSlider);
         progress.setMinWidth(largeurSlider);
         progress.setMaxWidth(largeurSlider);
         
         slider.valueProperty().addListener(new ChangeListener<Number>() { 
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
               double poucentage=new_val.doubleValue()/slider.getMax();
               progress.setProgress(poucentage);
               /*System.out.println(slider.getValue());*/
               if (slider.isValueChanging())MonLecteur.mediaView.getMediaPlayer().seek(MediaControl.dureeTotal.multiply(poucentage));
               }
            });
         StackPane paneSlider = new StackPane();
         paneSlider.getChildren().addAll(progress, slider);
      
       //Creation BorderPane où on mettra textNom & panPlay
         BorderPane bordre_Name =new BorderPane();

      //Placer le Text (Nom du Media) à gauche du BorderPane
         textNom=new Text("Xperia HD Landscapes");
         textNom.setFill(Color.WHITE);
         textNom.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
         BorderPane.setAlignment(textNom, Pos.CENTER);
         bordre_Name.setLeft(textNom);

      //Placer le panPlay (bouton Play) à droite du BorderPane
         final double canvasWidth = 37 , canvasHeight = 37;       
         final Canvas canvasPlay = new Canvas(canvasWidth,canvasHeight);
         final GraphicsContext gCercle = canvasPlay.getGraphicsContext2D();
         final GraphicsContext gPlay = canvasPlay.getGraphicsContext2D();
         canvasPlay.setOpacity(0.8);
         gCercle.setStroke(Color.WHITE);
         gCercle.strokeOval(1,1,canvasWidth-2,canvasHeight-2);
         gCercle.strokeOval(1,1,canvasWidth-3,canvasHeight-3);
         gPlay.setFill(Color.WHITE);
         //gPlay.fillPolygon(new double[]{10,10,30}, new double[]{10,26,18}, 3);   
         gPlay.fillRect(12, 10, 5, 15);
         gPlay.fillRect(20, 10, 5, 15);


         StackPane panPlay = new StackPane();
         panPlay.setPrefSize(canvasWidth+15,canvasHeight); //"15" c'est pour que le canvas soit large de tel sorte le cercle ne depasse pas le slider
         panPlay.getChildren().add(canvasPlay);
         bordre_Name.setRight(panPlay);
         
         //Creation textTemps
         textTemps=new Text("Heure");
         textTemps.setFill(Color.WHITE);

         //Placer tous les elements sur VBox
         getChildren().addAll(bordre_Name, paneSlider, textTemps);
         setStyle("-fx-background-color: black;");
         setPrefHeight(80);
         setPadding(new Insets(5, 10, 5, 10));
         BorderPane.setAlignment(this, Pos.CENTER);
         setOpacity(0.8);      

         canvasPlay.setOnMousePressed(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                          canvasPlay.setOpacity(1);
                                    }
                                 });
         canvasPlay.setOnMouseClicked(                                                                
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                          canvasPlay.setOpacity(0.8);
            gPlay.clearRect(8, 8, canvasWidth-15,canvasHeight-15);         //pour effacer Graphis existant pour le remplacer par le nouveau 
            if(play){            
               gPlay.fillRect(12, 10, 5, 15);
               gPlay.fillRect(20, 10, 5, 15);
               MonLecteur.mediaView.getMediaPlayer().play();
            }
            else{
               gPlay.fillPolygon(new double[]{10,10,30}, new double[]{10,26,18}, 3);
               MonLecteur.mediaView.getMediaPlayer().pause();
            }  
            play=!play;                                              
          }
         });
      }
   
      public void setLargeurSlider (double ls){
         slider.setMinWidth(ls+15);
         slider.setMaxWidth(ls);
         progress.setMinWidth(ls);
         progress.setMaxWidth(ls);
      }
   }