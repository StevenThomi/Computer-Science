import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
	private WordRecord[] words;
	private int noWords;
	private int maxY;
	boolean falling;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		g.setColor(Color.red);
		g.fillRect(0,maxY-10,width,height);
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		//draw the words
		for (int i=0;i<noWords;i++){	    	
			g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
		}
		
	}
	
	WordPanel(WordRecord[] words, int maxY) {
		this.words=words; //will this work?
		noWords = words.length;
		this.maxY=maxY;
		falling = true;
	}

	public void setFalling(boolean falling){
		this.falling = falling;
	}

	public WordRecord[] getWords () {
		return this.words;
	}

	public WordRecord getWordAtIndex (int index) {
		return this.words[index];
	}
   
	public int getNoWords () {
		return this.noWords;
	}
   
	public void run() {
   /*while the game is started, continuously redraw string*/
		while(falling){
			repaint();
		}
	}

}


