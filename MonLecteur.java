   //package lecteur;
   import composantsFSR.*;
   import javafx.application.Application;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.stage.WindowEvent;
   import javafx.scene.image.*;
   import javafx.scene.layout.*;
   import javafx.scene.media.*;
   import javafx.beans.value.*;
   import javafx.util.Duration;
   import javafx.scene.Group;
   import javafx.scene.Scene;
   import javafx.stage.Stage;
   import javafx.animation.*;
   import javafx.geometry.*;
   import javafx.scene.control.*;
   import java.io.File;

   /**
    *   
    *   Author: Abdelkarim KHALLOUK
    *   URI: https://github.com/abdelkarim-khallouk
    *   Email: ab.khallouk@gmail.com
    *
     * */


   public class MonLecteur extends Application {
      static MediaView mediaView;
      static SearchBar searchBar;
      static StackPane mediaPane;
      static CoverFlow coverFlow;
      static MediaBar mediaBar;
      static ProgressBarFSR starProgress;
      static ImageView captureImage;
      private Button listDirection;
      boolean isListHide = true;
      static PlayList playlist;
      double HEIGHT_BARs = MediaBar.HEIGHT+SearchBar.HEIGHT+MenuLecteur.HEIGHT;
      double WIDTH_LISTE = 200;

   //Modifier la methode start
      public void start(Stage st) {
         Group racine = new Group();      
         final Scene scene = new Scene(racine, 800, 500);
         st.getIcons().add(new Image("./img/logo.png"));
         st.setTitle("FSRPlayer");      
         st.setScene(scene);
         scene.getStylesheets().add("MonStyle.css");                         // * * * * * * Insertion Style
         
      //Creation BorderPane
         BorderPane border = new BorderPane();
         border.setStyle("-fx-background-color: black;");
         st.setMinHeight(280);
         st.setMinWidth(380);
         scene.setRoot(border);
      
      //Placer la Vue au centre du BorderPane
         mediaView = new MediaView(null);
         CaptureCoverFSR.correctionBD();
         coverFlow = new CoverFlow(new File("./cover"), scene);
         mediaPane = new StackPane();
         mediaPane.getChildren().setAll(coverFlow);
         border.setCenter(mediaPane);     
         //mediaPane.setVisible(false);
      //Placer le SearchBar au-dessous du Menu (hauteur de 24)
         searchBar = new SearchBar(scene);
         border.getChildren().add(searchBar);
         searchBar.relocate(0,24);

      //Placer le Menubar en Haut du BorderPane
         MenuLecteur menubar = new MenuLecteur(scene);
         border.setTop(menubar);

      //Placer le mediaBar en bas du BorderPane //je dois changer le width sur Mediacontrol fonction setOnReady
        mediaBar = new MediaBar(scene.getWidth()-50);                                // * * * * * * Insertion MediaBar (Slider, Nom_Media & Temps_Ecoulé_Media)
        border.setBottom(mediaBar);

      //Placer MaList sur la droite du BorderPane
         playlist= new PlayList(scene);
         listDirection = new Button();
         listDirection.setId("list-left-dir");
         listDirection.setMaxSize(16,16);
         listDirection.setMinSize(16,16);
         final HBox hboxList = new HBox();
         hboxList.resize(WIDTH_LISTE,scene.getHeight()-HEIGHT_BARs);
         hboxList.relocate(scene.getWidth()-WIDTH_LISTE,SearchBar.HEIGHT+MenuLecteur.HEIGHT);
         hboxList.setAlignment(Pos.CENTER);
         hboxList.getChildren().addAll(listDirection,playlist);
         border.getChildren().add(hboxList);

      //Ajouter icon de capture d'image
         captureImage = new ImageView("./img/capture.png");
         captureImage.setFitWidth(44);
         captureImage.setFitHeight(36);
         double x = (scene.getWidth()-captureImage.getFitWidth())/2;
         captureImage.setVisible(false);
         captureImage.relocate(x,60);
         border.getChildren().add(captureImage);
 
      //Ajouter Effet Transition sur Bordre
         final TranslateTransition ttMediaBar = new TranslateTransition(Duration.millis(400), mediaBar);   // * * * * * * Effet Transition pour MediaBar
         ttMediaBar.setToY(80);
         ttMediaBar.play();

         final TranslateTransition ttSearchBar = new TranslateTransition(Duration.millis(400), searchBar);   // * * * * * * Effet Transition pour SearchBar
         ttSearchBar.setToY(-30);
         ttSearchBar.play();

         final TranslateTransition ttListView = new TranslateTransition(Duration.millis(500), hboxList);   // * * * * * * Effet Transition pour playList
         ttListView.setToX(WIDTH_LISTE-16);
         ttListView.play();

         final TranslateTransition ttCaptureImage = new TranslateTransition(Duration.millis(400), captureImage);   // * * * * * * Effet Transition pour Bouton capture
         ttSearchBar.setToY(-50);
         ttSearchBar.play();


         border.setOnMouseEntered(                                                                 // * * * * * * Effet Transition pour MediaBar
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                    ttMediaBar.stop(); 
                                    ttMediaBar.setDelay(Duration.millis(0));
                                    ttMediaBar.setDuration(Duration.millis(200));
                                    ttMediaBar.setToY(0);
                                    ttMediaBar.play();
                                    ttSearchBar.stop();
                                    ttSearchBar.setDelay(Duration.millis(0));
                                    ttSearchBar.setDuration(Duration.millis(200));
                                    ttSearchBar.setToY(0); 
                                    ttSearchBar.play();
                                    ttCaptureImage.stop();
                                    ttCaptureImage.setDelay(Duration.millis(0));
                                    ttCaptureImage.setDuration(Duration.millis(200));
                                    ttCaptureImage.setToY(5); 
                                    ttCaptureImage.play();

                                    }
                                 });
         border.setOnMouseExited(                                                                  // * * * * * * Effet Transition pour MediaBar
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                      ttMediaBar.stop();
                                      ttMediaBar.setDelay(Duration.millis(1000));
                                      ttMediaBar.setDuration(Duration.millis(500));
                                      ttMediaBar.setToY(80);
                                      ttMediaBar.play();
                                      ttSearchBar.stop();
                                      ttSearchBar.setDelay(Duration.millis(1000));
                                      ttSearchBar.setDuration(Duration.millis(500));
                                      ttSearchBar.setToY(-30);                                       
                                      ttSearchBar.play();
                                      ttCaptureImage.stop();
                                      ttCaptureImage.setDelay(Duration.millis(1000));
                                      ttCaptureImage.setDuration(Duration.millis(500));
                                      ttCaptureImage.setToY(-94);                                       
                                      ttCaptureImage.play();
                                      ttListView.stop();
                                      ttListView.setDelay(Duration.millis(1000));
                                      ttListView.setToX(WIDTH_LISTE-16);
                                      listDirection.setId("list-left-dir");
                                      ttListView.play();
                                      isListHide=true;
                                    }
                                 });

     //Ajouter Effet Transition sur button listDirection
         listDirection.setOnMousePressed(                                                                
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {ttListView.stop();
                                    ttListView.setDelay(Duration.millis(0));
                                       if(isListHide){ttListView.setToX(0);
                                       listDirection.setId("list-right-dir");}
                                       else{ ttListView.setToX(WIDTH_LISTE-16);
                                       listDirection.setId("list-left-dir");}
                                       isListHide=!isListHide;
                                       ttListView.play();
                                       }
                                 });
     //Ajouter Action sur button captureImage
      captureImage.setOnMousePressed(
                                    new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       captureImage.setOpacity(0.2);
                                       CaptureCoverFSR.captureImage(mediaView);
                                    }
                                 });
      captureImage.setOnMouseReleased(
                                    new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       captureImage.setOpacity(1);
                                    }
                                 });

      //Ajouter Action sur window (Effet Zoom)
         scene.widthProperty().addListener(new ChangeListener<Number>() {  
             public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                     final double largeurApp = scene.getWidth();
                     mediaView.setFitWidth(largeurApp);

                     MonLecteur.mediaBar.setLargeurSlider(largeurApp);                    //* * * * * * * Adaptation Taille MediaBar au Media
                     MonLecteur.searchBar.setLargeurSearchBar(largeurApp);                //* * * * * * * Adaptation Taille SearchBar au Media
                     double x = (scene.getWidth()-captureImage.getFitWidth())/2;          //* * * * * * * Repostionner CaptureImage
                     captureImage.relocate(x,60);
                     hboxList.relocate(scene.getWidth()-WIDTH_LISTE,SearchBar.HEIGHT+MenuLecteur.HEIGHT);//* * * * * * * Repostionner hboxList
             }
            });
         scene.heightProperty().addListener(new ChangeListener<Number>() { 
             public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                     final double hauteurApp = scene.getHeight();
                     mediaView.setFitHeight(hauteurApp);
                     hboxList.resize(WIDTH_LISTE,scene.getHeight()-HEIGHT_BARs);          //* * * * * * * Adaptation Taille hboxList
             }
            });
         
         scene.getWindow().setOnCloseRequest( new EventHandler<WindowEvent>() {                
               public void handle(WindowEvent we) {                                          
                     CaptureCoverFSR.saveID();
             }
            });              
         st.show();
      }        
   
      public static void main(String[] args) {       
         launch(args);
      }  
   }