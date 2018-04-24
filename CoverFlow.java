   import composantsFSR.*;
   import javafx.collections.ObservableList;
   import javafx.event.EventHandler;
   import javafx.beans.property.*;
   import javafx.scene.effect.PerspectiveTransform;
   import javafx.scene.effect.ReflectionBuilder;
   import javafx.scene.input.MouseButton;
   import javafx.scene.image.ImageView;
   import javafx.scene.input.MouseEvent;
   import javafx.scene.shape.Rectangle;
   import javafx.scene.layout.*;
   import javafx.scene.media.*;
   import javafx.util.Duration;
   import javafx.scene.Parent;
   import javafx.scene.Group;
   import javafx.scene.Scene;
   import javafx.animation.*;
   import javafx.beans.*;
   import java.util.*;
   import java.io.*;

   
/**
 *   
 *   Author: Abdelkarim KHALLOUK
 *   URI: https://github.com/abdelkarim-khallouk
 *   Email: ab.khallouk@gmail.com
 *
  * */



//Affichage du Rangé
   public class CoverFlow extends Region {
      private static final Duration DURATION = Duration.millis(2000);
   //private static final Duration DURATION = Duration.millis(3000);
   
      private static final Interpolator INTERPOLATOR = Interpolator.EASE_BOTH;
      private static final double SPACING = 90;
      private static final double LEFT_OFFSET = -60;//-110;
      private static final double RIGHT_OFFSET = 60;//110;
      private static final double SCALE_SMALL = 0.7;
      private PerspectiveImage[] items;
      private Group centered = new Group();
      private Group left = new Group();
      private Group center = new Group();
      private Group right = new Group();
      private int centerIndex = 0;
      private Timeline timeline;
      private Rectangle clip = new Rectangle();
   
      public CoverFlow(final File rep, final Scene scene) {
         // set clip
         setClip(clip);
         // set background gradient using css
         setStyle("-fx-background-color: linear-gradient(to bottom, black 60, #141414 60.1%, black 100%);");

         // create items      
         File[] fichier = rep.listFiles();         
         items = new PerspectiveImage[fichier.length-2]; 
         for (int i=0; i<fichier.length-2; i++) {
            final double index = i;
            String pathCover = fichier[i].getPath();
            String [] Donnees = getDonnees(pathCover);
            final String MediaURI = Donnees[0];
            final int note = Integer.parseInt(Donnees[1]);
            final PerspectiveImage item = items[i] = new PerspectiveImage(pathCover,note);
            item.setOnMouseMoved(
                                      new EventHandler<MouseEvent>() {
                                         public void handle(MouseEvent me) {
                                            shiftToCenter(item);
                                         }
                                      });
            item.setOnMouseClicked(
                                      new EventHandler<MouseEvent>() {
                                         public void handle(MouseEvent me) {
                                            if(me.getButton().equals(MouseButton.PRIMARY)){
                                                if(me.getClickCount()==2){
                                                   MediaPlayer mPlayer = MonLecteur.mediaView.getMediaPlayer();
                                                   if(mPlayer!=null){
                                                       mPlayer.stop();
                                                       mPlayer.dispose();
                                                     }    
                                                   Media media = new Media(MediaURI);
                                                   mPlayer = new MediaPlayer(media);
                                                   MonLecteur.mediaView.setMediaPlayer(mPlayer);
                                                   mPlayer.setVolume((double) VolumeFSR.volumeProgress.getValeur() / 10);
                                                   MonLecteur.mediaPane.getChildren().setAll(MonLecteur.mediaView);
                                                   new MediaControl(mPlayer , scene);
                                                   SearchBar.isCOVER = false;
                                                   MonLecteur.captureImage.setVisible(true);
                                                   System.out.println("Ouverture du Media depuis Covers...");
                                                }
                                           }
                                         }
                                      });
                              }
      
            // create content
         centered.getChildren().addAll(left, right, center);
         getChildren().addAll(centered);
            // listen for keyboard events
         setFocusTraversable(true);
      
         if(fichier.length>2)
         update();
      }

      //Reccuperation du MediaURI et son notation à partir le pathCover
      private String [] getDonnees(String pathCover){
         String id = pathCover.substring(pathCover.lastIndexOf("_")+1,pathCover.lastIndexOf("."));
         String MediaURI = "", notation = "";
         try{
          BufferedReader in = new BufferedReader (new FileReader ("./cover/Data.fsr"));
          StringTokenizer st;
          String line;
            while((line=in.readLine())!=null){
               st=new StringTokenizer(line,"|");
               if(id.equals(st.nextToken())){
                  MediaURI = st.nextToken();
                  st.nextToken();st.nextToken();st.nextToken();
                  notation = st.nextToken();
                 break;
               }
            }         
            in.close();
         }  
         catch(IOException e){                    
            }
         System.out.println("Covers: "+MediaURI+"\tNotation: "+notation);
         String [] tab = new String [2];
         tab[0] = MediaURI;
         tab[1] = notation;
         return tab;
      }
   
      protected void layoutChildren() {            // update clip to our size
         clip.setWidth(getWidth());
         clip.setHeight(getHeight());
            // keep centered centered
         centered.setLayoutY((getHeight() - PerspectiveImage.HEIGHT) / 2);
         centered.setLayoutX((getWidth() - PerspectiveImage.WIDTH) / 2);
      }
   
      private void update() {
            // move items to new homes in groups
         left.getChildren().clear();
         center.getChildren().clear();
         right.getChildren().clear();
         for (int i = 0; i < centerIndex; i++) {
            left.getChildren().add(items[i]);
         }
         center.getChildren().add(items[centerIndex]);
         for (int i = items.length - 1; i > centerIndex; i--) {
            right.getChildren().add(items[i]);
         }
            // stop old timeline if there is one running
         if (timeline!=null) timeline.stop();
            // create timeline to animate to new positions
         timeline = new Timeline();
            // add keyframes for left items
         final ObservableList <KeyFrame>keyFrames = timeline.getKeyFrames();
         for (int i = 0; i < left.getChildren().size(); i++) {
            final PerspectiveImage it = items[i];
            double newX = -left.getChildren().size() *
            SPACING + SPACING * i + LEFT_OFFSET;
            keyFrames.add(new KeyFrame(DURATION,
                                       new KeyValue(it.translateXProperty(), newX, INTERPOLATOR),
                                       new KeyValue(it.scaleXProperty(), SCALE_SMALL, INTERPOLATOR),
                                       new KeyValue(it.scaleYProperty(), SCALE_SMALL, INTERPOLATOR),
                                       new KeyValue(it.angle, 40.0, INTERPOLATOR)));
         }
            // add keyframe for center item
         final PerspectiveImage centerItem = items[centerIndex];
         keyFrames.add(new KeyFrame(DURATION,
                                    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                                    new KeyValue(centerItem.scaleXProperty(), 1.0, INTERPOLATOR),
                                    new KeyValue(centerItem.scaleYProperty(), 1.0, INTERPOLATOR),
                                    new KeyValue(centerItem.angle, 90.0, INTERPOLATOR)));
      
            // add keyframes for right items
         for (int i = 0; i < right.getChildren().size(); i++) {
            final PerspectiveImage it = items[items.length - i - 1];
            final double newX = right.getChildren().size() * SPACING - SPACING * i + RIGHT_OFFSET;
            keyFrames.add(new KeyFrame(DURATION,
                                       new KeyValue(it.translateXProperty(), newX, INTERPOLATOR),
                                       new KeyValue(it.scaleXProperty(), SCALE_SMALL, INTERPOLATOR),
                                       new KeyValue(it.scaleYProperty(), SCALE_SMALL, INTERPOLATOR),
                                       new KeyValue(it.angle, 140.0, INTERPOLATOR)));
         }
            // play animation
         timeline.play();
      }
   
      private void shiftToCenter(PerspectiveImage item) {
         for (int i = 0; i < left.getChildren().size(); i++) {
            if (left.getChildren().get(i) == item) {
               int shiftAmount = left.getChildren().size() - i;
               shift(shiftAmount);
               return;
            }
         }
         if (center.getChildren().get(0) == item) {
            return;
         }
         for (int i = 0; i < right.getChildren().size(); i++) {
            if (right.getChildren().get(i) == item) {
               int shiftAmount = -(right.getChildren().size() - i);
               shift(shiftAmount);
               return;
            }
         }
      }
      public void shift(int shiftAmount) {
         if (centerIndex <= 0 && shiftAmount > 0) 
            return;
         if (centerIndex >= items.length - 1 && shiftAmount < 0) 
            return;
         centerIndex -= shiftAmount;
         update();
      }
   }

    /**
     * A Node that displays a image with some 2.5D perspective rotation around the Y axis.
     */
   class PerspectiveImage extends Parent {
      private static final double REFLECTION_SIZE = 0.35;
      public static final double WIDTH = 200;
      public static final double HEIGHT = 50 + WIDTH + (WIDTH*REFLECTION_SIZE);
      private static final double RADIUS_H = WIDTH / 2;
      private static final double BACK = WIDTH / 10;
      private PerspectiveTransform transform = new PerspectiveTransform();
      public String chemin;
      public int note;

        /** Angle Property */
      final DoubleProperty angle =  new SimpleDoubleProperty(80){
            protected void invalidated() {
                // when angle changes calculate new transform
               double lx = (RADIUS_H - Math.sin(Math.toRadians(angle.get())) * RADIUS_H - 1);
               double rx = (RADIUS_H + Math.sin(Math.toRadians(angle.get())) * RADIUS_H + 1);
               double uly = (-Math.cos(Math.toRadians(angle.get())) * BACK);
               double ury = -uly;
               transform.setUlx(lx);
               transform.setUly(uly);
               transform.setUrx(rx);
               transform.setUry(ury);
               transform.setLrx(rx);
               transform.setLry(HEIGHT + uly);
               transform.setLlx(lx);
               transform.setLly(HEIGHT + ury);
            }
         };
      public PerspectiveImage(String chemin, int note) {
         this.chemin = chemin;
         this.note = note;
         ImageView imageView = new ImageView(chemin);
         imageView.setEffect(ReflectionBuilder.create().fraction(REFLECTION_SIZE).build());
         setEffect(transform);
         ProgressBarFSR star = new ProgressBarFSR("star" , 5);
         star.setValeur(note);
         star.setDisable(true);
         VBox vbox = new VBox();
         vbox.setSpacing(15);
         vbox.getChildren().addAll(imageView,star);
         getChildren().addAll(vbox);
      }
   }
