import java.util.concurrent.RecursiveAction;

public class MedianFilterArray extends RecursiveAction  {
	  int lo; // arguments
	  int hi;
     int filterSize;
	  double[] arr;
     static double[] filteredArr;
	  static final int SEQUENTIAL_CUTOFF=4;

	    
	  MedianFilterArray(double[] a, int l, int h, int filter) { 
	    lo=l; hi=h; arr=a; filteredArr=a; filterSize = filter;
	  }

     public void printFilteredArray(){ 
	    for(int j = 0; j < filteredArr.length; j++){
         System.out.println("Output: "+filteredArr[j]);
      }
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
    
	  protected void compute(){
        int n = (filterSize - 1)/2;
        int length = (hi - lo) + 1;
        int ln; 
        int pc = (filterSize-1)/2;
              
         /*********************************************************/
		  if(length < SEQUENTIAL_CUTOFF) {
        
		      for(int i=0; i <= (length - 1 - (filterSize - 1)); i++){
               filteredArr[pc+lo+i] = median(lo+i, filterSize, arr); 
            }
 
                          
		  } else { 
                  
           if ((length % 2) == 0) { // even
               ln = length/2;
               
			  } else { // odd
           
               ln = (length + 1)/2;
           }
           
           
           MedianFilterArray left = new MedianFilterArray(arr, lo, lo+ln, filterSize);
   		  MedianFilterArray right= new MedianFilterArray(arr, (length-1)-ln, length-1, filterSize);
           
			  left.fork();
			  right.compute();
			  left.join();
		  }
     /*********************************************************/
     	 }
}


