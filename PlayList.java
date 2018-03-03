   import javafx.application.Application;
   import java.net.MalformedURLException;
   import javafx.scene.input.MouseEvent;
   import javafx.event.EventHandler;
   import javafx.scene.control.*;
   import javafx.collections.*;
   import javafx.scene.media.*;
   import javafx.scene.Scene;
   import javafx.util.*;
   import java.io.File;

   public class PlayList extends ListView {   

      public PlayList(final Scene scene){
         getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      

         setOnMousePressed(new EventHandler<MouseEvent>(){
                                     public void handle(MouseEvent e) {
                                     //L'action à exécuter si la souris est clicé
                                     if(e.getClickCount()==2){
                                       System.out.println(getSelectionModel().getSelectedItem());
                                       File fichier =  new File (getSelectionModel().getSelectedItem()+"");
                                       String nomMedia="";
//Le même traitement que pour Menu Lecteur
                                       try{
                                            nomMedia = fichier.toURL().toExternalForm();
                                       } catch(MalformedURLException ex){
                                          }
                                       nomMedia=nomMedia.replaceAll(" ","%20");
                                       MediaPlayer mPlayer = MonLecteur.mediaView.getMediaPlayer();

                                       if(mPlayer!=null){
                                          mPlayer.stop();
                                          mPlayer.dispose();
                                       }                                    
                                       Media media = new Media(nomMedia);
                                       mPlayer = new MediaPlayer(media);
                                       MonLecteur.mediaView.setMediaPlayer(mPlayer);
                                       mPlayer.setVolume((double) VolumeFSR.volumeProgress.getValeur() / 10);
                                       MonLecteur.mediaPane.getChildren().setAll(MonLecteur.mediaView);
                                       new MediaControl(mPlayer , scene);
                                       SearchBar.isCOVER = false;
                                       MonLecteur.captureImage.setVisible(true);
                                       System.out.println("Ouverture du Media depuis PlayList...");
                                    }
                                    }
                                 });

         setCellFactory(new Callback<ListView<String>, ListCell<File>>() {
                public ListCell<File> call(ListView<String> list) {
                    return new GetNom();}
                  }
                );     
      }
   }

//C'est notre classe
   class GetNom extends ListCell<File> {
        public void updateItem(File item, boolean empty) {
//L'attribus "super" est utilisé pour que la méthode pouvoir appeller elle même
            super.updateItem(item, empty);
            if (item != null) {
                  String name = item.getName();
//substring() pour pouvoir prendre le string de 0 jusqu'au dernier "."
//Enlever la partie extension dans le string
                  name = name.substring(0,name.lastIndexOf("."));
                  setText(name);
               }
        }
    }
