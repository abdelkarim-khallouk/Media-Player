   import javafx.application.Application;
   import javafx.scene.Group;
   import javafx.scene.Scene;
   import javafx.animation.*;
   import javafx.stage.*;
   import javafx.scene.media.*;
   import javafx.util.Duration;
   import javafx.scene.text.*;
   import javafx.scene.paint.Color;
   import javafx.scene.control.*;
   import javafx.scene.image.Image;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.geometry.*;
   import java.io.File;
   import javafx.scene.layout.*;
  import javafx.beans.value.*;

   public class MonLecteur extends Application {      
      static MediaView mediaView;
      static MediaBar mediaBar;

   //Modifier la methode start
      public void start(Stage st) {
         Group racine = new Group();      
         final Scene scene = new Scene(racine, 800, 500);
         st.getIcons().add(new Image("./img/logo.png"));
         st.setTitle("FSRPlayer");      
         st.setScene(scene);        
         scene.getStylesheets().add("MonStyle.css");                                            // * * * * * * Insertion Style
      //Creation BorderPane
         BorderPane border = new BorderPane();
         scene.setRoot(border); 
         border.setStyle("-fx-background-color: black;");
      
      //Placer la Vue au centre du BorderPane
         mediaView = new MediaView(null);
         border.setCenter(mediaView);
      
      //Placer le Menubar en Haut du BorderPane
         MenuLecteur menubar = new MenuLecteur(scene);
         border.setTop(menubar);
      
      //Placer le mediaBar en bas du BorderPane //je dois changer le width sur Mediacontrol fonction setOnReady
        mediaBar = new MediaBar(scene);                                    // * * * * * * Insertion MediaBar (Slider, Nom_Media & Temps_Ecoulé_Media)
        border.setBottom(mediaBar);

      //Placer MaList sur la droite du BorderPane
            // * * * * * * 
            
      //Ajouter Listner sur Bordre (Effet Transition)
         final TranslateTransition tt = new TranslateTransition(Duration.millis(400), mediaBar);   // * * * * * * Effet Transition pour MediaBar
         tt.setToY(80);
         tt.play();

         border.setOnMouseEntered(                                                                 // * * * * * * Effet Transition pour MediaBar
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {tt.stop();
                                    tt.setDelay(Duration.millis(0));
                                    tt.setDuration(Duration.millis(200));
                                       tt.setToY(0);
                                       tt.play();
                                    }
                                 });
         border.setOnMouseExited(                                                                  // * * * * * * Effet Transition pour MediaBar
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {tt.stop();
                                      tt.setDelay(Duration.millis(1000));
                                      tt.setDuration(Duration.millis(500));
                                       tt.setToY(80);
                                       tt.play();
                                    }
                                 });

      //Ajouter Listner sur window (Effet Zoom)
         scene.widthProperty().addListener(new ChangeListener<Number>() {  
             public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
               mediaView.setFitWidth(scene.getWindow().getWidth());  
               MonLecteur.mediaBar.setLargeurSlider(scene.getWidth()-50);                    //* * * * * * * Adaptation Taille MediaBar au Media    
             }
         });
         scene.heightProperty().addListener(new ChangeListener<Number>() { 
             public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            mediaView.setFitHeight(scene.getWindow().getHeight());
            }
      });

      //Mettre le BorderPane au racine de la scene
              
         st.show();
      }        
   
      public static void main(String[] args) {       
         launch(args);
      }  
   }