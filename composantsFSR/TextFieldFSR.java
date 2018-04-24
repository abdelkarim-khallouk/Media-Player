   package composantsFSR;
   import javafx.scene.input.MouseEvent;
   import javafx.scene.image.ImageView;
   import javafx.scene.layout.Region;
   import javafx.event.EventHandler;
   import javafx.event.ActionEvent;
   import javafx.scene.control.*;
   import javafx.beans.value.*;

   /**
    *   
    *   Author: Abdelkarim KHALLOUK
    *   URI: https://github.com/abdelkarim-khallouk
    *   Email: ab.khallouk@gmail.com
    *
     * */


   public class TextFieldFSR extends Region{
      private ImageView imgSearch;
      private TextField textField;
      private Button clearButton;  
      private boolean etatEcriture;  
 
      public TextFieldFSR(final String text) {
         getStyleClass().add("text-search");
         setMinHeight(24);
         setMinWidth(100);
         setPrefSize(200, 24);
         setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
         
         //Contruire imgSearch
         imgSearch = new ImageView("./img/search.png");

         //Contruire textField
         textField = new TextField();
         textField.setPromptText("    "+text);

         //Contruire clearButton
         clearButton = new Button();
         clearButton.setVisible(false);

         getChildren().addAll(textField, imgSearch, clearButton);
         
         clearButton.setOnAction( new EventHandler<ActionEvent>() {                
                                       public void handle(ActionEvent actionEvent) {
                                          textField.setText("");
                                          imgSearch.setVisible(true);
                                          System.out.println("Utilisateur Efface URL du Media");
                                          etatEcriture=false;
                                       }
                                    });

         textField.textProperty().addListener( new ChangeListener<String>() {
                                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                          clearButton.setVisible(textField.getText().length() != 0);
                                          imgSearch.setVisible(false);
                                          if(!etatEcriture){ System.out.println("Utilisateur Ecrit URL du Media");
                                                             etatEcriture=true;
                                                           }
                }
            });

         textField.setOnMouseClicked( new EventHandler<MouseEvent>() {
                                         public void handle(MouseEvent me) {
                                          imgSearch.setVisible(false);
                }
            });
      }

        protected void layoutChildren() {
            imgSearch.relocate(imgSearch.getX()+2,imgSearch.getY()+5);
            textField.resize(getWidth(), getHeight());
            clearButton.resizeRelocate(getWidth() - 18, 6, 12, 13);
        }

      public String getText(){
         return this.textField.getText();
      }
   }