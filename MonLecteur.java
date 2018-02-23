   import javafx.application.Application;
   import javafx.scene.Group;
   import javafx.scene.Scene;
   import javafx.stage.Stage;
   import javafx.scene.media.Media;
   import javafx.scene.media.MediaPlayer;
   import javafx.scene.media.MediaView;  

   public class MonLecteur extends Application {         
   //Specifier le fichier source Media
      private static final String nomFichier = "file:/c:/Chanson.flv";// "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
   
   //Modifier la methode start
      public void start(Stage st) {
         Group racine = new Group();      
         Scene scene = new Scene(racine, 544, 224);//800, 500
         st.setTitle("FsrLecteur");      
         st.setScene(scene);
      
      
      //Creation du media
         Media media = new Media(nomFichier);
      
      //Creation du media player
         MediaPlayer mediaPlayer = new MediaPlayer(media);        
         mediaPlayer.setAutoPlay(true); 
      
      //Creation mediaView         
         MediaView mediaView = new MediaView(mediaPlayer); 
         ((Group) scene.getRoot()).getChildren().add(mediaView);
      
         st.show();     
      }        
   
      public static void main(String[] args) {       
         launch(args);
      }  
   }