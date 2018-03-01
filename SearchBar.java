   import composantsFSR.*;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.control.*;
   import javafx.scene.layout.*;
   import javafx.scene.image.*;
   import javafx.scene.media.*;
   import javafx.util.Duration;
   import java.io.IOException;
   import javafx.stage.Stage;  
   import javafx.scene.Scene;
   import javafx.animation.*;
   import javafx.geometry.*;
   import java.io.File;
   import java.net.*;


   public class SearchBar extends BorderPane{
      private TextFieldFSR textSearch = new TextFieldFSR("Rechercher ...");
      private VolumeFSR volumeFSR = new VolumeFSR(MonLecteur.mediaView);
      private ProgressBarFSR starProgress = new ProgressBarFSR("star" , 5);
      private Label labelSearch = new Label("Search");
      private Button pleinEcran = new Button();
      private Button iconCover = new Button();
      private Button iconCam = new Button();
      private HBox searchBar = new HBox();
      private HBox iconsBar = new HBox();
      static AlertFSR alertMSG;
      static MediaPlayer mediaPlayer;
   
      public SearchBar(final Scene scene) {
         labelSearch.setId("label-search");
         labelSearch.setMinWidth(40);
         iconCover.setId("image-cover");
         iconCover.setPrefSize(24,24);
         iconCam.setId("image-camera");
         iconCam.setPrefSize(32,28);
         pleinEcran.setId("plein-ecran");
         pleinEcran.setPrefSize(24,24);
      
         //Definition des Tooltip pour tous les elts 
         labelSearch.setTooltip(new Tooltip("Recherche des Medias, Commencer par 'http://' pour Media_Web"));
         iconCover.setTooltip(new Tooltip("Affichage des Couvertures"));
         iconCam.setTooltip(new Tooltip("Noter le Medie avant la capture de Couverture"));
         pleinEcran.setTooltip(new Tooltip("Plein Ecran"));
      
         //Configurer le starProgress
         starProgress.setValeur(3);
         starProgress.setOpacity(1);
         animateStarBar(starProgress);
      
      
         //Remplissage iconsBar par les icons: Cover et Camera
         iconsBar.setSpacing(10);
         iconsBar.setAlignment(Pos.CENTER);
         iconsBar.getChildren().addAll(iconCover, iconCam, pleinEcran, starProgress);
      
         //Remplissage searchBar par les elements: textSearch et labelSearch
         searchBar.setSpacing(8);
         searchBar.setAlignment(Pos.CENTER);
         searchBar.getChildren().addAll(textSearch, labelSearch); 
      
         //Remplissage TopBar par les elements: Bar_des_Icons, Bar_de_Search et Bar_de_Volume
         setLeft(iconsBar);
         setCenter(searchBar);
         setRight(volumeFSR);
         setOpacity(0.7);
         setPrefHeight(30);
         resize(scene.getWidth(),30);
         setPadding(new Insets(0, 10, 0, 10));
         setStyle("-fx-background-color: black;");
      
        //Action chagement de Vue vers les Covers
         iconCover.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       System.out.println("Affichage Couvertures des Medias");
                                    }
                                 });   
        //Action Capture d'image
         iconCam.setOnMouseClicked(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       System.out.println("Capture de Couverture");
                                       alertMSG = new AlertFSR(scene, "Capture de Couverture réalisé avec Succès", "Info.png");
                                    }
                                 });
        //Action Plein Ecran 
         pleinEcran.setOnMouseClicked(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       Stage st= (Stage) scene.getWindow();
                                       if(st.isFullScreen()){
                                          st.setFullScreen(false);
                                          System.out.println("Retour en Mode Ecran Normale");
                                       }
                                       else{
                                          st.setFullScreen(true);
                                          System.out.println("Passer en Mode Plein Ecran");
                                       }
                                    }
                                 });
                              
        //Action Search
         labelSearch.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       String Media_URI = textSearch.getText();
                                       
                                       boolean isFILE = false;
                                       String shemeURI="";
                                       String typeFile = "Non_File";
                                    
                                       File fichier = new File(Media_URI);
                                       if(fichier.isFile()){
                                          try{
                                             Media_URI = fichier.toURL().toExternalForm();
                                          }                                             
                                             catch(MalformedURLException ex){                                                     
                                             } 
                                       }
                                    
                                       Media_URI=Media_URI.replaceAll(" ","%20");
                                       try{
                                          URI uri = new URI(Media_URI);
                                          shemeURI = uri.getScheme();
                                          if(new File(uri.getPath()).isFile())isFILE=true;
                                       } 
                                          catch(URISyntaxException ex){    
                                          }
                                    
                                       if(shemeURI!=null && shemeURI.equals("http")) typeFile = "Internet";
                                       else if(shemeURI!=null && shemeURI.equals("file")&&isFILE) typeFile = "Local";
                                       System.out.println("typeFile:  "+typeFile+"  |  "+Media_URI);
                                            //Progress de Notation
                                    
                                       if(typeFile.equals("Non_File"))
                                          alertMSG = new AlertFSR(scene, "Merci d'entrer Correctement le Nom du Media", "Info.png");
                                       else if(typeFile.equals("Internet") && !siConnected())
                                          alertMSG = new AlertFSR(scene, "Echec de Connexion...", "Alert.png");
                                       else if (typeFile.equals("Internet") || typeFile.equals("Local")){
                                          if (typeFile.equals("Internet")) alertMSG = new AlertFSR(scene, "Connexion Reussie...", "Info.png");
                                          mediaPlayer = MonLecteur.mediaView.getMediaPlayer();
                                          if(mediaPlayer!=null){
                                             mediaPlayer.stop();
                                             mediaPlayer.dispose();
                                          }         
                                          Media media = new Media(Media_URI);
                                          mediaPlayer=new MediaPlayer(media);
                                          MonLecteur.mediaView.setMediaPlayer(mediaPlayer);
                                          mediaPlayer.setVolume(volumeFSR.getVolume() / 10);
                                          new MediaControl(mediaPlayer , scene);                                          
                                       }                                    
                                    }
                                 }); 
      }
   
      // * * * * * * Mise à jour de largeur du SearchBar   
      public void setLargeurSearchBar (double largeur){
         this.resize(largeur,30);
      }
   
      // * * * * * * Verification d'accès à Internet
      public static boolean siConnected(){
         try {
            URL url = new URL("http://www.google.com");
         
                //ouvrir une connexion pour cette source "google"
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();         
            Object objData = urlConnect.getContent();
         }
               //Echec pour la non connexion -> Passer a "catch"
            catch (UnknownHostException e) {
               return false;
            }
            catch (IOException e) {
               return false;
            }
         return true;
      }
   
      // * * * * * * Effet Transition pour Noter le Media
      private void animateStarBar (ProgressBarFSR starProgress){
         final TranslateTransition animStar = new TranslateTransition(Duration.millis(400), starProgress);
         animStar.setToY(-24);
         animStar.setToX(-120);
         animStar.play();
      
         iconCam.setOnMouseEntered(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       animStar.stop();
                                       animStar.setToY(30);
                                       animStar.play();
                                    
                                    }
                                 });
         iconCover.setOnMouseEntered(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       animStar.stop();
                                       animStar.setToY(-24);
                                       animStar.play();
                                    }
                                 });
         pleinEcran.setOnMouseEntered(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       animStar.stop();
                                       animStar.setToY(-24);
                                       animStar.play();
                                    }
                                 });
      
         this.setOnMouseExited(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       animStar.stop();
                                       animStar.setToY(-24);
                                       animStar.play();
                                    }
                                 });
                                 
      }

   }