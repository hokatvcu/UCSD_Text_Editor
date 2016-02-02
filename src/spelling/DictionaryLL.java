package spelling;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{
	private LinkedList<String> dict;
	private int wordsNum;
	
	public DictionaryLL(){
		dict = new LinkedList<String>();
		wordsNum = 0;		
	}

    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	word = word.toLowerCase();
    	if(!isWord(word)){
    		dict.add(word);
    		wordsNum++;
    		return true;
    	}
        return false;
    }

    /** Return the number of words in the dictionary */
    public int size()
    {
        return wordsNum;
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
    	for(int i = 0; i < this.wordsNum; i++){
    		if(dict.get(i).equals(s.toLowerCase())){
    			return true;
    		}
    	}	
        return false;
    }
    public static void main(String[] args){
    	
    }
}
