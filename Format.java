   import javafx.util.Duration;



//Class Format
   public class Format {   
   
      static String formatTime(Duration timeCourant, Duration dureeTotal) {
         int intElapsed = (int) Math.floor(timeCourant.toSeconds());
         int timeCourantHours = intElapsed / (60 * 60);
         if (timeCourantHours > 0) {
            intElapsed -= timeCourantHours * 60 * 60;
         }
         int timeCourantMinutes = intElapsed / 60;
         int timeCourantSeconds = intElapsed - timeCourantHours * 60 * 60
         - timeCourantMinutes * 60;
      
         if (dureeTotal.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(dureeTotal.toSeconds());
            int dureeTotalHours = intDuration / (60 * 60);
            if (dureeTotalHours > 0) {
               intDuration -= dureeTotalHours * 60 * 60;
            }
            int dureeTotalMinutes = intDuration / 60;
            int dureeTotalSeconds = intDuration - dureeTotalHours * 60 * 60
            - dureeTotalMinutes * 60;
            if (dureeTotalHours > 0) {
               return String.format("%d:%02d:%02d/%d:%02d:%02d",
                                   timeCourantHours, timeCourantMinutes, timeCourantSeconds,
                                   dureeTotalHours, dureeTotalMinutes, dureeTotalSeconds);
            } 
            else {
               return String.format("%02d:%02d/%02d:%02d",
                                   timeCourantMinutes, timeCourantSeconds, dureeTotalMinutes,
                                   dureeTotalSeconds);
            }
         } 
         else {
            if (timeCourantHours > 0) {
               return String.format("%d:%02d:%02d", timeCourantHours,
                                   timeCourantMinutes, timeCourantSeconds);
            } 
            else {
               return String.format("%02d:%02d", timeCourantMinutes,
                                   timeCourantSeconds);
            
            }
         }
      }
   }




