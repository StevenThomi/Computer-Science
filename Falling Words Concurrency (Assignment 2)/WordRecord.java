/*A wordrecord object is kept per word in in the dictionary (WordDictionary dict)*/
/*Keeps track of where the word is on the screen*/
public class WordRecord {
	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;

	private int fallingSpeed;
	private int maxWait=1500;
	private int minWait=100;

	public static WordDictionary dict;



	WordRecord() {
		text="";
		x=0;
		y=0;
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
	}

	WordRecord(String text) {
		this();
		this.text=text;
	}

	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}

// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}

	public synchronized  void setX(int x) {
		this.x=x;
	}

	public synchronized  void setWord(String text) {
		this.text=text;
	}

	public synchronized void setMaxWait(int maxWait){
		this.maxWait = maxWait;
	}

	public synchronized void setMinWait(int minWait){
		this.minWait = minWait;
	}

	public synchronized  String getWord() {
		return text;
	}

	public synchronized  int getX() {
		return x;
	}

	public synchronized  int getY() {
		return y;
	}

	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized  void setSpeed(int fallingSpeed) {
		this.fallingSpeed = fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		setY(0);
	}

	public synchronized void resetWord() {
		resetPos();
      /*Get next word*/
		text=dict.getNewWord();
      /*Set status to not dropped*/
		dropped=false;
      /*Calculate new random falling speed*/
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
	}

	public synchronized boolean matchWord(String typedText) {
		if (typedText.equals(this.text)) { //word matched correctly
			resetWord();
			return true;
		}
		else
			return false;
	}

   //shift word downwards by inc
	public synchronized  void drop(int inc) {
		setY(y+inc);
	}
	//change state
	public synchronized  boolean dropped() {
		return dropped;
	}

}
