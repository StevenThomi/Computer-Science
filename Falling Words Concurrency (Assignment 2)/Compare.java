import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Compare implements Runnable{
    private WordPanel w;
    private String txt;
    private Score myScore;

    
    public Compare(WordPanel w, String txt, Score myScore){
        this.w = w;
        this.txt = txt;
        this.myScore = myScore;
    }
    
    public void play(String fileName) throws Exception{
      File sound = new File(fileName);
      
      /*Play the sound file using Clip*/
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(sound));
      clip.start();
      
      /*Pause for the sound to play*/
      Thread.sleep(1000); // adds slight latence to the thread
      /*
      * An alternative implementation would be to create a separate 
      * Audio class to play the sound if a condition (boolean) is
      * toggled by the Compare threads; risks high overhead and lag
      * using heavyweight Java threads
      */
    }
   
    public void run(){
        WordRecord word = null;
        for (int i = 0; i < w.getNoWords(); i++){
        /* Get the word at index in the WordPanel's
         * WordRecord array object
         */
          word = w.getWordAtIndex(i);
          
         /*if correct match*/
         if(word.matchWord(txt)){
             String match = word.getWord();
             myScore.caughtWord(match.length());
             /*Play sound*/
             try{ play("game.wav"); } catch (Exception e){ } 
         }
        }
    }
}