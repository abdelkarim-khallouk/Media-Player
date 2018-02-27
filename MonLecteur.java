   import javafx.scene.layout.BorderPane;
   import javafx.application.Application;
   import javafx.scene.Group;
   import javafx.scene.Scene;
   import javafx.stage.Stage;
   import javafx.scene.media.*;
   import javafx.util.Duration;
   import javafx.scene.text.Text;
   import javafx.scene.paint.Color;
   import javafx.scene.control.*;
   import javafx.collections.*;
   import javafx.scene.image.Image;
   import java.util.*;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.geometry.*;
   import java.io.File;
   import javafx.beans.value.*;/*
   import javafx.scene.Camera;
   import javafx.application.ConditionalFeature;*/


   public class MonLecteur extends Application {      
      static Text textTemps;
      static MediaView mediaView;
   
   //Modifier la methode start
      public void start(Stage st) {
         Group racine = new Group();      
         final Scene scene = new Scene(racine, 800, 500);
         st.getIcons().add(new Image("./img/logo.png")); 				//* * * * * * * Creation Icon de l'Application
         st.setTitle("FSRPlayer");      
         st.setScene(scene);
      
      //Creation BorderPane
         BorderPane border = new BorderPane();
         border.setStyle("-fx-background-color: black;");				// * * * * Changement Couleur Ecran en Noir
      
      //Placer la Vue au centre du BorderPane
         mediaView = new MediaView(null);
         border.setCenter(mediaView);
      
      //Placer le Menubar au Top du BorderPane
         MenuLecteur menubar = new MenuLecteur(scene);
         border.setTop(menubar);
      
      //Placer le Text en bas du BorderPane
         textTemps=new Text("");
         textTemps.setFill(Color.WHITE);												// * * * * Changement Couleur Ecran en Noir
         border.setBottom(textTemps);
      
      //Placer MaList sur la droite du BorderPane
      
      //Mettre le BorderPane au racine de la scene
         scene.setRoot(border);
         st.show();
      
      //Ajouter Listner sur window (Effet Zoom)
         scene.widthProperty().addListener(new ChangeListener<Number>() {	// * * * * Effet Zoom
             public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
      		mediaView.setFitWidth(scene.getWindow().getWidth());     
             }
         });
         scene.heightProperty().addListener(new ChangeListener<Number>() {	// * * * * Effet Zoom
             public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
      		mediaView.setFitHeight(scene.getWindow().getHeight());
      		}
      });
      
      // scene.setCamera(new Camera());
      }        
   
      public static void main(String[] args) {       
         launch(args);
      }  
   }