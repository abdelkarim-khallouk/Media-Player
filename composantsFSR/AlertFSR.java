   package composantsFSR;
   import javafx.application.Application;
   import javafx.scene.Scene;
   import javafx.stage.*;
   import javafx.scene.layout.*;
   import javafx.scene.control.*;
   import javafx.geometry.*;
   import javafx.scene.paint.Color;
   import javafx.scene.image.*;
   import javafx.event.ActionEvent;
   import javafx.event.EventHandler;
   import javafx.scene.text.Text;

   public class AlertFSR extends Stage {
   
      private Scene sceneAlert;
      private String cheminICON;
      private String msg;
   
      public AlertFSR(Scene scene, String msg, String cheminICON) {
         this.msg = msg;
         this.cheminICON=cheminICON;
         initModality(Modality.APPLICATION_MODAL);
         initStyle(StageStyle.TRANSPARENT);
         getIcons().add(new Image("./img/logo.png"));
         setTitle("FSRPlayer"); 
         setResizable(false);
      
         ImageView imgInfo = new ImageView("./img/"+cheminICON);
         //imgInfo.setFitWidth(40); imgInfo.setPreserveRatio(true); Pour dimentionner l'icon
         Label label = new Label(msg);
         label.setGraphicTextGap(20);
         label.setGraphic(imgInfo);
      
         Button button = new Button("OK");
         button.setOnAction(
                              new EventHandler<ActionEvent>(){
                                 public void handle(ActionEvent e) {                                 
                                    close();
                                 }
                              });
         VBox vbox = new VBox();
         vbox.setAlignment(Pos.CENTER);
         vbox.getChildren().addAll(label, button);
      
         sceneAlert = new Scene(vbox);
         sceneAlert.setFill(Color.TRANSPARENT);
         sceneAlert.getRoot().setStyle("-fx-background-color: #c0c0c0;-fx-background-radius: 10;-fx-padding: 10;"); 
         setScene(sceneAlert);
         sceneAlert.getWindow().setHeight(80);
         
         show();
         setCenterMSG(scene.getWindow());
         System.out.println("Alerte: "+msg);
      }
   
      public void setCenterMSG(Window window){
         //dimention Window Lecteur
         double x = window.getX();
         double y = window.getY();
         double largeurWindow = window.getWidth();
         double hauteurWindow = window.getHeight();
      
         //dimention Window Alert
         double largeurAlert = sceneAlert.getWindow().getWidth();    //Text text = new Text(msg); double largeurAlert = text.getLayoutBounds().getWidth() + 40;
         double hauteurAlert = sceneAlert.getWindow().getHeight()-40;
      
         //Positionner l'Alerte: (Position Lecteur) + (demi Dimention Lecteur) - (demi Dimention Alerte)
         sceneAlert.getWindow().setX(x + (largeurWindow-largeurAlert)/2);
         sceneAlert.getWindow().setY(y + (hauteurWindow-hauteurAlert)/2);
      }
   
   }