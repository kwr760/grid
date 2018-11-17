package krt.utility;

public class Strings {
	public static int charCount(StringBuilder str, Character c) {
		int count = 0;
		for(int i =0; i < str.length(); i++) {
		    if(str.charAt(i) == c) {
		        count++;
		    }
		}
		
		return count;
	}
	
	public static int countOccurrences(StringBuilder str, Character[] content){
	    int count = 0;
	    
	    for (Character c : content) {
	    	count += charCount(str, c);
	    }
	    
	    return count;
	}	
}
