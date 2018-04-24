   import javafx.scene.control.*;
   import javafx.scene.layout.Region;
   import javafx.scene.image.ImageView;
   import javafx.scene.input.MouseEvent;
   import javafx.event.ActionEvent;
   import javafx.event.EventHandler;
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
 
      public TextFieldFSR(final String text) {
          getStyleClass().add("text-search");
         setMinHeight(24);
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
                                       }
                                    });

         textField.textProperty().addListener( new ChangeListener<String>() {
                                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                          clearButton.setVisible(textField.getText().length() != 0);
                                          imgSearch.setVisible(false);
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