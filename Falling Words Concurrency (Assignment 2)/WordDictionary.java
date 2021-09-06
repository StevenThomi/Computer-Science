public class WordDictionary {
   /*Two parameters: dictionary size and dictionary elements*/
	int size;
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
   /*Constructor 1*/
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
      //populate theDict (deep copy)
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		
	}
	/*Constructor 2: Default*/
	WordDictionary() {
		size=theDict.length;
		
	}
	
   /*Fetch random word in dictionary*/
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}
	
}
