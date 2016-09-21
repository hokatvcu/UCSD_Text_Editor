package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    
    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0;
	}
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		word = word.toLowerCase();
		TrieNode home = root;
		boolean wordAdded = false;
		
		// loop through word for chars
		for(int i = 0; i < word.length(); i++){
			char c = word.charAt(i);
			// test to see if the char exists in hash
			TrieNode testNode = home.getChild(c);
			// if char doesn't exist, add it to the hash 
			if(testNode == null){
				home.insert(c);
				// if last char doesn't exist in hash, then it is a new word 
				if(i == word.length()-1){
					wordAdded = true;
				}
			}
			// point through the next hash by char key
			home = home.getChild(c);		
		}
		if(wordAdded){
			this.size ++;
			home.setEndsWord(true);
			return true;
		}
		return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    
		TrieNode home = root;
		boolean foundWord = false;
		s = s.toLowerCase();
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			home = home.getChild(c);
			if(home == null){
				return foundWord;
			}
		}
		
		if(home.getText().equals(s)){
			foundWord = true;
		}
		return foundWord;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> completionList = new ArrayList<String>();
    	 
    	 //1.
    	 prefix = prefix.toLowerCase();
    	 TrieNode home = root;
    	 
    	 for(int i = 0; i < prefix.length(); i ++){
    		 char c = prefix.charAt(i);
    		 home = home.getChild(c);
    		 if(home == null){
    			 // return an empty list if prefix doesn't iterate completely
    			 return completionList;
    		 }
    	 }
    	 
    	 //2. queue for nodes First in and first out 
    	 Queue<TrieNode> q = new LinkedList <TrieNode>();
    	 TrieNode stem = home;
    	 int completedWords = 0;
    	 // start of the prefix node
    	 q.add(stem);
    	 
    	 // keep going if queue is not empty
    	 while(!q.isEmpty()){
    		 // remove the first and save it to curr
    		 TrieNode curr = q.remove();
    		 if(curr !=null){
    			 
    			 if(curr.endsWord() == true){
    				 completionList.add(curr.getText());
    				 completedWords ++;
    			 }
    			 
    			 if(completedWords == numCompletions){
    				 break;
    			 }
    			 
    			 for(Character c: curr.getValidNextCharacters()){
    				 q.add(curr.getChild(c));
    			 }
    			 
    		 }
    	 }
    	 return completionList;	 
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	public static void main(String[] args){
 		
// 		AutoCompleteDictionaryTrie t = new AutoCompleteDictionaryTrie();
// 		t.addWord("hello");
// 		t.addWord("help");
// 		t.addWord("he");
// 		t.addWord("Helicopter");
// 		t.addWord("hero");
// 		System.out.println(t.isWord("Hello"));
// 		System.out.println(t.predictCompletions("he",5));
 	}
 	
}