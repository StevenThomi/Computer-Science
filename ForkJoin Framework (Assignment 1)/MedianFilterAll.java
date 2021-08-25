import java.util.concurrent.ForkJoinPool;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
 
public class MedianFilterAll {
	static long startTime = 0;
   static MedianFilterArray myArray = null;
   
	private static void tick(){
		startTime = System.nanoTime();
	}
	private static float tock(){
		return (System.nanoTime() - startTime) / 1000000.0f;
	}
	static final ForkJoinPool fjPool = new ForkJoinPool();
   
	static void medianFilter(double[] arr, int filterSize){
	  fjPool.invoke(new MedianFilterArray(arr,0,arr.length,filterSize));
	}
   
   // Random Number Generator method
   public static double[] randomNumberGenerator(int number) {
    
       double[] arr = new double[number];    // Array to store random numbers
       
       for (int i = 0; i < number; i++){
         arr[i] = Math.random();
       }
       
       return arr;
   }
	
	public static void main(String[] args) {
      PrintWriter inputStream = null;
      PrintWriter outputStream = null;
      
      String inputFile = args[0];
      String outputFile = args[1];
      int FILTER_SIZE = Integer.parseInt(args[2]);
      int DATA_SIZE = Integer.parseInt(args[3]);
       
		double[] arr = randomNumberGenerator(DATA_SIZE); 
      
      try{
      
         inputStream = new PrintWriter(new FileOutputStream(inputFile));
         outputStream = new PrintWriter(new FileOutputStream(outputFile));
      
      } catch (FileNotFoundException e){
      
         System.out.println("Error opening the text files.");
         
      }
      
      for(int j = 0; j < arr.length; j++){
         inputStream.println(j+1 + "\t: " + arr[j]);
      }
      inputStream.close();
      
      float time = 0;
      System.gc();
      
      for(int i = 0; i < 20; i++){
		   tick();
		   medianFilter(arr, FILTER_SIZE);
		   time = time + tock();
      }
        
		System.out.println("Average (of 20) run took "+ time/20 +" milliseconds");

      for(int j = 0; j < arr.length; j++){
         outputStream.println(j+1 + "\t: " + arr[j]);
      }
       outputStream.close();
	}

}
