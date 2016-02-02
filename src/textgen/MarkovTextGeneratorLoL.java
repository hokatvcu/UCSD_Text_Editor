package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import document.EfficientDocument;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private  List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		starter = "";
		ListNode starterWordNode;
		ListNode newStarterWordNode;

		EfficientDocument sourceDocument = new EfficientDocument(sourceText);
		ArrayList<String> sourceWordList = sourceDocument.getWordList();
		
		for(String word: sourceWordList){
			
			starterWordNode = getStarterNode(wordList, starter);
			// node exists in list, just add word to next
			if(starterWordNode != null){
				starterWordNode.addNextWord(word);	
			}
			// node doesn't exist, make it and add it to list, add word to next word
			else{
				newStarterWordNode = new ListNode(starter);
				wordList.add(newStarterWordNode);
				newStarterWordNode.addNextWord(word);
			}
			// starter is one index back
			starter = word;		
		}
		
	}
	
	private void printWordList(){
		for(ListNode node: wordList){
			System.out.println(node.getWord());
		}
	}
	
	private ListNode getStarterNode(List<ListNode> list, String starter){
		
		for(ListNode node: list){
			if (node.getWord().equals(starter)){
				return node;
			}
		}
		return null;	
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		if(wordList.size() < 1){
			return "Error: empty source file was trained. Please train a valid text";
		}
		starter = "";
		ArrayList<String> output = new ArrayList<String>();
		ListNode starterWordNode;
		String randomWord;
		Random randomGenerator = new Random();
		String outputString = "";
		
		while(output.size() < numWords){
			starterWordNode = getStarterNode(wordList, starter);
			if(starterWordNode == null){
				starterWordNode = getStarterNode(wordList, "");
			}
			randomWord = starterWordNode.getRandomNextWord(randomGenerator);
			output.add(randomWord);
			starter = randomWord;	
		}
		
		for(String word: output){
			outputString += word + " ";		
		}
		return outputString;
	}
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList.clear();
		this.train(sourceText);
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
//		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
//		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
//		System.out.println(textString);
//		gen.train(textString);
//		System.out.println(gen);
//		System.out.println(gen.generateText(20));
//		String textString2 = "You say yes, I say no, "+
//				"You say stop, and I say go, go, go, "+
//				"Oh no. You say goodbye and I say hello, hello, hello, "+
//				"I don't know why you say goodbye, I say hello, hello, hello, "+
//				"I don't know why you say goodbye, I say hello. "+
//				"I say high, you say low, "+
//				"You say why, and I say I don't know. "+
//				"Oh no. "+
//				"You say goodbye and I say hello, hello, hello. "+
//				"I don't know why you say goodbye, I say hello, hello, hello, "+
//				"I don't know why you say goodbye, I say hello. "+
//				"Why, why, why, why, why, why, "+
//				"Do you say goodbye. "+
//				"Oh no. "+
//				"You say goodbye and I say hello, hello, hello. "+
//				"I don't know why you say goodbye, I say hello, hello, hello, "+
//				"I don't know why you say goodbye, I say hello. "+
//				"You say yes, I say no, "+
//				"You say stop and I say go, go, go. "+
//				"Oh, oh no. "+
//				"You say goodbye and I say hello, hello, hello. "+
//				"I don't know why you say goodbye, I say hello, hello, hello, "+
//				"I don't know why you say goodbye, I say hello, hello, hello, "+
//				"I don't know why you say goodbye, I say hello, hello, hello,";
//		System.out.println(textString2);
//		gen.retrain(textString2);
//		System.out.println(gen);
//		System.out.println(gen.generateText(20));
		
//		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
//		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
//		textString = "hi there hi Leo";
//		gen.train(textString);
//		
//		System.out.println(gen.generateText(4));
	}
}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		int randomInt = generator.nextInt(nextWords.size());
		String randomString = nextWords.get(randomInt);
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    return randomString;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


