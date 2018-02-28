   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.control.*;
   import javafx.scene.layout.*;
   import javafx.scene.image.*;
   import javafx.scene.media.*;
   import java.io.IOException;
   import javafx.stage.Stage; 
   import javafx.scene.Scene;
   //import javafx.geometry.*;
   import java.io.File;
   import java.net.*;


   public class SearchBar extends BorderPane{
      private TextFieldFSR textSearch = new TextFieldFSR("Rechercher ...");
      private VolumeBar volumeBar = new VolumeBar(MonLecteur.mediaView);
      private Label labelSearch = new Label("Search");
      private Button pleinEcran = new Button();
      private Button iconCover = new Button();
      private Button iconCam = new Button();
      private HBox searchBar = new HBox();
      private HBox iconsBar = new HBox();
      private MediaPlayer mPlayer;
   
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
         labelSearch.setTooltip(new Tooltip("Recherche des Medias sur internet ou en interne"));
         iconCover.setTooltip(new Tooltip("Affichage des Couvertures"));
         iconCam.setTooltip(new Tooltip("Capture des Couvertures"));
         pleinEcran.setTooltip(new Tooltip("Plein Ecran"));
      
         //Remplissage iconsBar par les icons: Cover, Camera et FullScreen
         iconsBar.setSpacing(10);
         iconsBar.setAlignment(Pos.CENTER);
         iconsBar.getChildren().addAll(iconCover, iconCam, pleinEcran);
      
         //Remplissage searchBar par les elements: textSearch et labelSearch
         searchBar.setSpacing(8);
         searchBar.setAlignment(Pos.CENTER);
         searchBar.getChildren().addAll(textSearch, labelSearch); 
      
         //Remplissage TopBar par les elements: Bar_des_Icons, Bar_de_Search et Bar_de_Volume
         setLeft(iconsBar);
         setCenter(searchBar);
         setRight(volumeBar);
         setOpacity(0.7);
         setPrefHeight(30);
         resize(scene.getWidth(),30);
         setPadding(new Insets(0, 10, 0, 10));
         setStyle("-fx-background-color: black;");
      
         //Action changement de Vue vers les Covers
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
                                       System.out.println("Capture Cover depuis Media");
                                    }
                                 });
        //Action Plein Ecran 
         pleinEcran.setOnMouseClicked(
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       Stage st= (Stage) scene.getWindow();
                                       st.setFullScreen(!st.isFullScreen());
                                       System.out.println("Changement Mode Ecran");
                                    }
                                 });
        //Action Search
         labelSearch.setOnMouseClicked( 
                                 new EventHandler<MouseEvent>(){
                                    public void handle(MouseEvent e) {
                                       String Media_URL = textSearch.getText();
                                    
                                       File fichier = new File(Media_URL);
                                       if(fichier.isFile()){
                                          try{
                                             Media_URL = fichier.toURL().toExternalForm();
                                          } 
                                             catch(MalformedURLException ex){
                                             }
                                       
                                          mPlayer = MonLecteur.mediaView.getMediaPlayer();
                                          if(mPlayer!=null){
                                             mPlayer.stop();
                                             mPlayer.dispose();
                                          }            
                                          Media_URL=Media_URL.replaceAll(" ","%20"); 
                                          Media media = new Media(Media_URL);
                                          mPlayer=new MediaPlayer(media);
                                          MonLecteur.mediaView.setMediaPlayer(mPlayer);
                                          mPlayer.setVolume((double) VolumeBar.niveauVolume / 10);
                                          new MediaControl(mPlayer , scene);
                                       
                                       //Alert pas Accès à l'internet
                                          if(siAccesInternet())
                                             System.out.println("Connexion Etablie");
                                          else System.out.println("Echec de Connexion...");
                                       } 
                                       else System.out.println("Merci d'entrer Correctement le Nom du Media"); //il faut ajouter AlerteMSG
                                    }
                                 });
      }
   
      public void setLargeurSearchBar (double largeur){
         this.resize(largeur,30);
         searchBar.setPrefWidth(largeur-340);
      }
   
      //Verification d'accès à internet
      public static boolean siAccesInternet(){
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
   }