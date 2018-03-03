   import composantsFSR.*;
   import javafx.scene.paint.Color;
   import javafx.scene.layout.*;
   import javafx.scene.text.*;
   import javafx.geometry.*;

   public class MediaBar extends VBox{
      public Text textTemps = new Text("00:00/00:00");
      public Text textNom = new Text("");
      private BoutonFSR boutonFSR = new BoutonFSR();
      public SliderFSR sliderFSR;
      static double HEIGHT = 80;
   
      public MediaBar(double largeurSlider) {
       //Creation BorderPane, il contiendra 'textNom' & 'panPlay'
         BorderPane bordre_Name =new BorderPane();
      
         //Placer Nom_Media à gauche du BorderPane
         textNom.setFill(Color.WHITE);
         textNom.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
         BorderPane.setAlignment(textNom, Pos.CENTER);
         bordre_Name.setLeft(textNom);
      
         //Placer BoutonPlay à droite du BorderPane
         boutonFSR.setAction(MonLecteur.mediaView);        
         StackPane panPlay = new StackPane();
         panPlay.setPrefSize(boutonFSR.getWidth()+15,boutonFSR.getHeight()); //"15" c'est pour Aligner BoutonPlay avec Slider
         panPlay.setAlignment(Pos.CENTER_LEFT);
         panPlay.getChildren().add(boutonFSR);
         bordre_Name.setRight(panPlay);
      
         //Construire mon SliderBar
         sliderFSR = new SliderFSR(largeurSlider);
         sliderFSR.setAction(MonLecteur.mediaView);
      
         //Creation textTemps
         textTemps.setFill(Color.WHITE);
      
         //Placer tous les elements sur MediaBar
         getChildren().addAll(bordre_Name, sliderFSR, textTemps);  
      
         //Organiser le MediaBar
         setStyle("-fx-background-color: black;");
         setPadding(new Insets(5, 10, 5, 10));
         setPrefHeight(HEIGHT);
         setOpacity(0.8);  
      }
   
      public void setLargeurSlider (double largeur){
         sliderFSR.setLargeur(largeur);
      }
   }