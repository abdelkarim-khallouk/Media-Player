   import javafx.application.Application;
   import javafx.scene.Group;
   import javafx.scene.Scene;   
   import javafx.stage.Stage;
   import javafx.scene.shape.Circle;

   public class MonLecteur extends Application {         
   
   //Modifier la methode start
      public void start(Stage st) {
         //Circle circ = new Circle(40, 40, 30);
         //Group racine = new Group(circ);   
         Group racine = new Group();         
         Scene scene = new Scene(racine, 800, 500);
         st.setTitle("MyPlayer");      
         st.setScene(scene);              
         st.show();     
      }        
   
      public static void main(String[] args) {       
         launch(args);
      }  
   }