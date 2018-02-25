   import javafx.application.Application;
   import javafx.scene.Group;
   import javafx.scene.Scene;
   import javafx.stage.Stage;
   import javafx.scene.media.Media;
   import javafx.scene.media.MediaPlayer;
   //import javafx.scene.media.MediaView;  
   import javafx.util.Duration;

   public class MonLecteur extends Application {         
   //Specifier le fichier source Media
      private static final String MEDIA_URL = "file:/c:/Chanson.flv";// "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
   
   //Modifier la methode start
      public void start(Stage st) {
         Group racine = new Group();      
         Scene scene = new Scene(racine, 800, 500);//544, 224
         st.setTitle("FsrLecteur");      
         st.setScene(scene);
      
      
      //Creation du media
         Media media = new Media(MEDIA_URL);
         MediaPlayer mediaPlayer = new MediaPlayer(media);              
         MediaControl mediaControl = new MediaControl(mediaPlayer,scene);
         scene.setRoot(mediaControl);
      
         st.show();     
      }        
   
      public static void main(String[] args) {       
         launch(args);
      }  
   }
