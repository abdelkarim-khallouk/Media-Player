   package composantsFSR;
   import javafx.beans.InvalidationListener;
   import javafx.beans.Observable;
   import javafx.scene.SnapshotParameters;
   import javafx.embed.swing.SwingFXUtils;
   import java.awt.image.BufferedImage;
   import java.net.URISyntaxException;
   import java.text.SimpleDateFormat;
   import javax.imageio.ImageIO;
   import javafx.scene.image.*;
   import javafx.scene.media.*;
   import javafx.scene.text.*;
   import java.awt.Graphics;
   import java.awt.Color;
   import java.awt.Font;
   import java.net.URI;
   import java.util.*;
   import java.io.*;

//le id sera enregistrer a la fermeture de l'application
//il faut enregistrer aussi le note du Media (Star)
//AlertMSG(cover existe);
   public class CaptureCoverFSR {
      MediaPlayer mediaPlayer;
      static int note = 0;
      static int id = 0;
      static public String request = "";

      public CaptureCoverFSR(MediaView mediaView, int note) {
         this.note = note;
         mediaPlayer = mediaView.getMediaPlayer();
         if(!coverExist(mediaPlayer)){
            sauvegardeImage(mediaPlayer);
            sauvegardeDonnees(mediaPlayer);
            request = "Capture Cover Realise avec Succes";
         }
         else request = "Cover existant";
      }
   
   //Calcule d'emplacement ideal pour l'insertion des commentaires sur l'image
      static int getX(String commentaire, int largeur, String police, int size){
         Text text = new Text(commentaire);
         javafx.scene.text.Font f = javafx.scene.text.Font.font(police, FontWeight.BOLD, size);
         text.setFont(f);
         int largeurText = (int)text.getLayoutBounds().getWidth()+10;
         int x = (largeur - largeurText)/2;  
         if(commentaire.indexOf("TYPE FICHIER:")!=-1) x = largeur - largeurText;
      
         return x;
      }
   
   //Reccuperation les données du Media
      static String [] getDonnees(MediaPlayer mediaPlayer){
         String [] tab = new String [5];
      
         String Media_URL = mediaPlayer.getMedia().getSource();
         File fichier=new File(Media_URL);
         String fileName = fichier.getName();
      
      //titre de fichier
         tab[0] = fileName.substring(0,fileName.lastIndexOf("."));
         tab[0] = tab[0].replaceAll("%20"," ");
      
      //extension de fichier
         String extension = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
         tab[2] = "TYPE FICHIER: "+extension.toUpperCase();
      
      //date de sauvegarde
         Date maDate=new Date();
         Locale locale = Locale.getDefault();
         SimpleDateFormat forme = new SimpleDateFormat("dd MMMM yyyy");
         tab[1] = forme.format(maDate).toString();
      
      //chemin du fichier
         tab[3] = Media_URL;
      
      //note du Media
         tab[4] = note+"";
         return tab;
      }
   
   //Sauvegarde des Donnees
      static void sauvegardeDonnees(MediaPlayer mediaPlayer){
         String [] donnee  = getDonnees(mediaPlayer);
         try{
            PrintStream fichierSortie = new PrintStream(new FileOutputStream("./cover/Data.fsr",true));
            fichierSortie.println(id+"|"+donnee[3]+"|"+donnee[0]+"|"+donnee[1]+"|"+donnee[2]+"|"+donnee[4]);
            fichierSortie.close();
            id++;
         }
            catch(IOException e){
            }
      }
   
   //Sauvegarde Cover
      static void sauvegardeImage(MediaPlayer mediaPlayer) {
         MediaView mView = new MediaView(mediaPlayer);
      
         //Adapter dimention MediaView
         int largeur = mediaPlayer.getMedia().getWidth()/2;
         int hauteur = mediaPlayer.getMedia().getHeight()/2;
         mView.setFitWidth(largeur);
         mView.setFitHeight(hauteur);
      
         //Creation image à partir Video
         largeur = largeur*4/5;
         WritableImage wi = new WritableImage(largeur,hauteur);
         mView.snapshot(new SnapshotParameters(), wi);
         ImageView imageView = new ImageView(wi);
         Image img = imageView.getImage();
      
      //Insertion des Commentaire sur l'image (Titre, Date, Extention)
         BufferedImage buffer = SwingFXUtils.fromFXImage(img, null);
         Graphics g = buffer.getGraphics();
         String [] donnee  = getDonnees(mediaPlayer); //{ "Araignée-Gipsy", "18 Mars 2014", "TYPE FICHIER: MP4"};
         String [] police  = { "Haettenschweiler", "Haettenschweiler", "Garamond" };
         Color  [] couleur = { Color.white, Color.blue, Color.red };
         int    [] size    = { 22, 14 ,12 };
         int    [] y       = { 40, 54 , hauteur-3};
      
         for(int i=0;i<3;i++){
            int x = getX(donnee[i], largeur, police[i], size[i]);
            g.setFont(new Font(police[i],Font.BOLD,size[i]));
         
            if(i==0){
               javafx.scene.paint.Color col = wi.getPixelReader().getColor(x+20, y[i]+5).invert();
               couleur[i] = new Color((float) col.getRed(), (float) 0.3, (float) col.getBlue());
            }
            g.setColor(couleur[i]);
            g.drawString(donnee[i], x, y[i]);
         }
      
         try{
         //insertion d'icon à l'image
            BufferedImage bufferImg = ImageIO.read(new File("./img/logo.png"));
            g.drawImage(bufferImg, 0, hauteur-30, 30, 30, null);
         
         //Sauvegarde d'image apres transformation
            ImageIO.write(buffer, "png",new File("./cover/COVER_"+id+".png"));
         }
            catch(IOException e){
            }
      }
   
     //Verification d'existance des covers sur la Base données si oui il ne sera pas enregistrer
      static boolean coverExist(MediaPlayer mediaPlayer){
         String Media_URL = mediaPlayer.getMedia().getSource();
         try{
            BufferedReader in = new BufferedReader (new FileReader ("./cover/Data.fsr"));
            StringTokenizer st; 
            String line;
         
            while((line=in.readLine())!=null){ 
               st=new StringTokenizer(line, "|"); 
               st.nextToken();
               if(Media_URL.compareTo(st.nextToken())==0)
                  return  true;
            }
            in.close();
         }
            catch(IOException e){                    
            }
         return false;
      }
   
   //Verification initiale de la conformité de tous les données de la Base données si non il faus supprimer les lignes non conforme et les Covers correspond
   //le 'id' doit etre reccuperer depuis la BD 'en haut' & doit etre toujours incrementer meme si on supprime des lignes pour eviter la redandance
      static public void correctionBD(){
         //File file_data = new File("./cover/Data.fsr");
         File file_tmp = new File("./cover/Data-tmp.fsr");
         String line = "";
         try{
            file_tmp.createNewFile();
            BufferedReader in = new BufferedReader (new FileReader ("./cover/Data.fsr"));
            PrintStream out = new PrintStream(new FileOutputStream("./cover/Data-tmp.fsr"));
            StringTokenizer st;
            while((line=in.readLine())!=null){
               st=new StringTokenizer(line, "|");
               id = Integer.parseInt (st.nextToken());
               String Media_URL = st.nextToken();
               File file_Media = new File( new URI(Media_URL).getPath() );
               File file_Cover = new File("./cover/COVER_"+id+".png");
               if(file_Media.exists()) 
                  out.println(line);
               else 
                  file_Cover.delete();
            }
         
            in.close();
            out.close();
         }
            catch(IOException e){                    
            }
            catch(URISyntaxException uriEx){                    
            }
      
         try{
            file_tmp.createNewFile();
            BufferedReader in = new BufferedReader (new FileReader ("./cover/Data-tmp.fsr"));
            PrintStream out = new PrintStream(new FileOutputStream("./cover/Data.fsr"));
         
            while((line=in.readLine())!=null)
               out.println(line);
            in.close();
            out.close();
            in = new BufferedReader (new FileReader ("./cover/id.fsr"));
            id = Integer.parseInt (in.readLine());
            in.close();
         }
            catch(IOException e){                    
            }
         file_tmp.delete();
      }
      static public void saveID(){
         try{
            PrintStream out = new PrintStream(new FileOutputStream("./cover/id.fsr"));
            out.print(id);
            out.close();
         }
            catch(IOException e){
            }
      }

   //Capture d'Image
      static public void captureImage(MediaView mView) {
         MediaPlayer mPlayer = mView.getMediaPlayer();
         int largeur = mPlayer.getMedia().getWidth();
         int hauteur = mPlayer.getMedia().getHeight();
         WritableImage wi = new WritableImage(largeur,hauteur);
         mView.snapshot(new SnapshotParameters(), wi);
         int i = new File("./capture").list().length;
         String [] donnee  = getDonnees(mPlayer);
         
          //Sauvegarde d'image
          try{
            BufferedImage buffer = SwingFXUtils.fromFXImage(wi, null);
            ImageIO.write(buffer, "png",new File("./capture/"+donnee[0]+"_"+i+".png"));
            System.out.println("Capture Image Realise avec Succes");
         }
            catch(IOException e){
            }
      }
   }