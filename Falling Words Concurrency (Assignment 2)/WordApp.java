/*Graphics Classes*/
import javax.swing.*;
import java.awt.*;
/*Event Handling Classes*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*Dictionary Input Classes*/
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
/*Parallel Concurrency Class*/
import java.util.concurrent.*;

public class WordApp {
	static int noWords;
	static int totalWords;

   /*Window size settings*/
   static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

   /*Initialise empty dictionary*/
	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

   /*Current word properties stored in WordRecord object*/
	static WordRecord[] words;

   /*Current score stored in Score object*/
	static Score myScore = new Score();

   /*Panels*/
	static WordPanel w;
	static JPanel txt;
   static JPanel g;
   /*Frame*/
	static JFrame frame;
   /*Labels*/
	static JLabel caught;
	static JLabel missed;
	static JLabel score;
   /*Text Field*/
   static JTextField textEntry;
   /*Buttons*/
   static JButton startB;
   static JButton endB;
   static JButton quitB;


  /* Threading is implemented in two Classes:
   * Thread Class:
   * FallingWords class
   */
	//threads used to manage falling words
	static Thread[] threads;
	//Create pool of falling words
	static FallingWords[] fallingWords;

	/* falling words status*/
	static boolean falling = false;
	/* restart status*/
	static boolean reset = false;
	/* exit game info*/
	static boolean exitAlert = false;


	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	frame = new JFrame("WordGame");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);

      g = new JPanel();
      g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
      g.setSize(frameX,frameY);


		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	   g.add(w);

	   txt = new JPanel();
	   txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
	   caught = new JLabel("Caught: " + myScore.getCaught() + "    ");
	   missed = new JLabel("Missed:" + myScore.getMissed()+ "    ");
      score = new JLabel("Score:" + myScore.getScore()+ "    ");
	   txt.add(caught);
	   txt.add(missed);
	   txt.add(score);

	   textEntry = new JTextField("",20);
	   textEntry.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
   		   String text = textEntry.getText();
            /*
            * Reference made to: https://www.tutorialspoint.com/how-to-create-a-thread-by-using-anonymous-class-in-java
            * anonymous thread object started to compare typed data to data on screen
            * using anonymous objects to run one-use objects saves space

            Compare myCompare = new Compare(w, text, myScore);
            Thread myThread = new Thread(myCompare);
            myThread.start();
            */
   	      (new Thread(new Compare(w, text, myScore))).start();

            textEntry.setText("");
            textEntry.requestFocus();
		   }
		});

      txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);

	   JPanel b = new JPanel();
      b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
	   startB = new JButton("Start");;

      /*
      *  This listener waits for the user to press the start button
      *  to start the game
      */
		startB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
            /*If currently off*/
				if(!falling){ // prevents user from starting game while it is running myScore.resetScore();
					//If end is
					if(reset){
                  /*Reset game*/
						myScore.resetScore();
                  exitAlert = false; // game has not been exited
						w.setFalling(true);
                  /*start WordPanel object thread (View) to
                   * display falling words
                   */
						(new Thread(w)).start();

                  FallingWords animation = null;
                  for (int i = 0; i < noWords; i++){
                     /* Get the animation at index in the FallingWords's
                      * array object
                      */
                     animation = fallingWords[i];
                     animation.setFalling(true);
                  }

						for (int i = 0; i < noWords; i++) {
                     /*
                     * Each word displayed is operated by a different thread
                     * It is worthwhile to use the ForkJoin framework to implement these
                     * threads for cases of many falling words (high level game play)
                     */
							threads[i] = new Thread(fallingWords[i]);
						}
					}
					if (!reset) {
                  /*at game-start reset == true*/
						reset = true;
					}
					falling = true;

               /* Start the threads running*/
               for (int i = 0; i < noWords; i++) {
                  threads[i].start();
               }
				}
		    	  textEntry.requestFocus();  //return focus to the text entry field
		   }
		});

      endB = new JButton("End");;

		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
            /*Safely terminate threads*/
   			terminateThreads();
            /*Reset game*/
   			myScore.resetScore();
            /*End session*/
   			exitAlert = false;
		   }
		});

		quitB = new JButton("Quit");;
		quitB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
            terminateThreads();
            /*exit*/
   			System.exit(0);
		   }
		});

      b.add(startB);
		b.add(endB);
		b.add(quitB);

		g.add(b);

      frame.setLocationRelativeTo(null);  // Center window on screen.
      frame.add(g); //add contents to window
      frame.setContentPane(g);
      frame.setVisible(true);
	}


public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
      Scanner dictReader = null;
		try {
			dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();

			dictStr = new String[dictLength];
			for (int i = 0; i < dictLength; i++) {
				dictStr[i] = new String(dictReader.next());
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;

	}

public static int getPersonalBest() {
      int pb = 0;
		try {
			Scanner pbReader = new Scanner(new FileInputStream("pb.txt"));
			pb = pbReader.nextInt();
			pbReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file pb.txt.");
	   }
		return pb;

	}

	public static void main(String[] args) {

		//deal with command line arguments
		totalWords = Integer.parseInt(args[0]);  //total words to fall
		noWords = Integer.parseInt(args[1]); // total words falling at any point
 		String[] tmpDict = getDictFromFile(args[2]); //file of words

		assert(totalWords >= noWords); // this could be done more neatly

		if (tmpDict != null)
			dict = new WordDictionary(tmpDict);

		WordRecord.dict=dict; //set the class dictionary for the words.

		words = new WordRecord[noWords];  //shared array of current words


		setupGUI(frameX, frameY, yLimit);

      /*x-axis spacing between words*/
		int x_inc = (int)frameX/noWords;
	  	//initialize shared array of current words

		for (int i = 0; i < noWords; i++) {
			words[i] = new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}

    	/* start WordPanel object thread (View)
       * thread is started BUT faling word animation is
       * dormant
       */
		(new Thread(w)).start();

      /*Create fallingWords array*/
		fallingWords = new FallingWords[noWords];

		/* Set up falling words using dict*/
		for (int i = 0; i < noWords; i++) {
			fallingWords[i] = new FallingWords(w, myScore, i);
		}

      /*Create thread array*/
		threads = new Thread[noWords];

      /* Place each falling word in a new thread*/
		for (int i = 0; i < noWords; i++) {
			threads[i] = new Thread(fallingWords[i]);
		}
      /*
       * reference made to: https://alvinalexander.com/java/joptionpane-showmessagedialog-examples-1/
       * Continuous update of data labels while game is active
       */
		while(true){
			displayUpdate();

         /* Exit the game if totalWords words are displayed*/
			if(myScore.getTotal() >= totalWords){
				terminateThreads();

				if(!exitAlert){ // display exit message prompt once
               String exitMessage = "Score : "+myScore.getScore()+"\nPersonal Best : "+getPersonalBest();
               JOptionPane.showMessageDialog(frame, exitMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
					exitAlert = true; // game has been exited
               myScore.resetScore();
				}
			}
		}
	}

   /*
    * Controller displays View data
    */
	public static void displayUpdate(){
		caught.setText("Caught: " + myScore.getCaught() + "    ");
	    missed.setText("Missed:" + myScore.getMissed()+ "    ");
	    score.setText("Score:" + myScore.getScore()+ "    ");
	}

	public static void stopThreads(){
      /*Stop animation*/
      FallingWords animation = null;
      for (int i = 0; i < noWords; i++){
      /* Get the animation at index in the FallingWords's
       * array object
       */
      animation = fallingWords[i];
      animation.setFalling(false);
      }

		w.setFalling(false);
	}

	public static void terminateThreads(){
		stopThreads();
		falling = false;

      /*Ensure personal best is correct*/
      if (myScore.getScore() > getPersonalBest()){
         PrintWriter pbWriter = null;
         try {
   			pbWriter = new PrintWriter(new FileOutputStream("pb.txt"));
   			pbWriter.println(myScore.getScore());
   			pbWriter.close();
   		} catch (IOException e) {
   	      System.err.println("Problem writing to file pb.txt.");
   	   }
      }
	}
}
