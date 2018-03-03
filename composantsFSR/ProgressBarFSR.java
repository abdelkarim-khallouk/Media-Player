 package composantsFSR;

   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.control.*;
   import javafx.scene.layout.*;
   import javafx.geometry.Pos;


   public class ProgressBarFSR extends HBox{
      private Label [] label;
      public String idCss = "";
      public int niveau = 0;
      public int max = 0;
   
      public ProgressBarFSR(final String idCss, int max) {
         this.idCss = idCss;
         this.max = max;
         setSpacing(4);
         setAlignment(Pos.CENTER);
         label =  new Label[max];
         for (int i=0; i<max; i++){          
            final Label item = label[i] = new Label("  ");
            item.setStyle("-fx-background-radius: 0.24em;");    
            item.setMinHeight(24);
            if(idCss.equals("star"))item.setMinWidth(24);
            getChildren().add(item);         
            final int index = i;
            item.setOnMouseClicked( 
                                    new EventHandler<MouseEvent>(){
                                       public void handle(MouseEvent e) {
                                          niveau = index + 1;
                                          setValeur(niveau);
                                          System.out.println(idCss+": "+niveau);
                                       }
                                    }); 
         }      
      
      }
      public void setValeur(int valeur){
         niveau = valeur;
         for (int i=0; i<max; i++){ 
            if(i < niveau) label[i].setId(idCss+"-1");
            else  label[i].setId(idCss+"-2");
         }
      }
   
      public int getValeur(){
         return niveau;
      }
   
   
   }