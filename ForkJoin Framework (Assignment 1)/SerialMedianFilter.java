import java.lang.Math;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class SerialMedianFilter {

	static long startTime = 0;
   
	private static void tick(){
		startTime = System.nanoTime();
	}
	private static float tock(){
		return (System.nanoTime() - startTime) / 1000000.0f;
	}

    // Random Number Generator method
    public static double[] randomNumberGenerator(int number) {
    
       double[] arr = new double[number];    // Array to store random numbers
       
       for (int i = 0; i < number; i++){
         arr[i] = Math.random();
       }
       
       return arr;
    }

    // Array sort method (Bubble Sort Algorithm)
    public static double[] dataSort(double[] arr){
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] > arr[j+1]) {
                    // swap arr[j+1] and arr[j]
                    double temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
        return arr;
    }

    // Median identifier method
    public static double median(int startIndex, int filterSize, double[] arr){
    
       double[] small_arr = new double[filterSize];   //Small array filter
       for (int i = 0; i < filterSize; i++) {
         small_arr[i] = arr[startIndex + i];
       }
    
       double[] sorted_arr = dataSort(small_arr);
       
       int middle = (filterSize - 1)/2;
       
       return sorted_arr[middle];
       
    }

    public static void main(String[] args) {
    
       PrintWriter inputStream = null;
       PrintWriter outputStream = null;
       
       String inputFile = args[0];
       String outputFile = args[1];
       int FILTER_SIZE = Integer.parseInt(args[2]);
       int DATA_SIZE = Integer.parseInt(args[3]);
       
       int n = (FILTER_SIZE - 1)/2;
       System.gc();
       
       try{
      
         inputStream = new PrintWriter(new FileOutputStream(inputFile));
         outputStream = new PrintWriter(new FileOutputStream(outputFile));
      
      } catch (FileNotFoundException e){
      
         System.out.println("Error opening the text files.");
         
      }
      
       double[] arr = randomNumberGenerator(DATA_SIZE);
       double[] filtered_arr = new double[arr.length];
       float time = 0;
       
      for(int j = 0; j < 20; j++){
		   tick();
		   for (int i = 0; i < arr.length; i++){
         
            if( (i - n) >= 0 && (i + n) < arr.length ) {
               filtered_arr[i] = median(i-n, FILTER_SIZE, arr);
         
            } else {
         
               filtered_arr[i] = arr[i];
            
            }
         }
		   time = time + tock();
      }
        
		System.out.println("Average (of 20) run took "+ time/20 +" milliseconds");

       for (int i = 0; i < arr.length; i++){
         inputStream.println(i+1 + "\t: " + arr[i]);
         outputStream.println(i+1 + "\t: " + filtered_arr[i]);

       }
       
        inputStream.close();
        outputStream.close();
        
       System.out.println();
    
    }
  
}