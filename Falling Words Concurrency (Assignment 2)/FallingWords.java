public class FallingWords implements Runnable{

    private WordPanel w;
    private Score score;
    private boolean falling;
    private int id;

    FallingWords(WordPanel w, Score score, int id){
        this.w = w;
        this.score = score;
        this.id = id; // used to identify the word
        this.falling = true;
    }

    public void setFalling (boolean falling) {
        this.falling = falling;
    }

    public void run(){
        while(falling){
            WordRecord words[] = w.getWords();
            /*speed delay setting to control the pace of the game*/
            int speed = (int)(0.015 * words[id].getSpeed());
            
            /*implement a delay for the words to fall smoothly*/
            for (int i = 0; i < speed; i++){
                try{ Thread.sleep(1); } catch (Exception e){/*unlikely*/}
            }

            /* 1 increment down y-direction */
            words[id].drop(1);
            
            if(words[id].dropped()){
                score.missedWord();
                words[id].resetWord();
            }
        }
    }
}