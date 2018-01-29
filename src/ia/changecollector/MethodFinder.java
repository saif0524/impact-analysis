package ia.changecollector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodFinder {
	
	private File inputFile;
	
	/** provides a list of methods (in pairs) that has changes in signatures**/
	public List<String> getMethodSignatureChangeLog(String fileName) {
		//Used a regex from https://stackoverflow.com/questions/68633/regex-that-will-match-a-java-method-declaration;		
		String pattern = ".[\\-\\+][ \\t]*(?:(?:public|protected|private)\\s+)?"+
				"(?:(static|final|native|synchronized|abstract|threadsafe|transient|"+
				"(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>)|(?:<[^<]*<["+
				"^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))\\s+){0,}(?!return)\\b([\\w.]+)\\b"+
				"(?:|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>)|(?:<[^<]*"+
				"<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))((?:\\[\\]){0,})\\s+\\b\\w+"+
				"\\b\\s*\\(\\s*(?:\\b([\\w.]+)\\b(?:|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+"+
				">[^>]*>)|(?:<[^<]*<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))((?:\\[\\]){0,})(\\.\\.\\.)?\\s+"+
				"(\\w+)\\b(?![>\\[])\\s*(?:,\\s+\\b([\\w.]+)\\b(?:|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+"+
				">[^>]*>)|(?:<[^<]*<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))((?:\\[\\]){0,})(\\.\\.\\.)?\\s+"+
				"(\\w+)\\b(?![>\\[])\\s*){0,})?\\s*\\)(?:\\s*throws [\\w.]+(\\s*,\\s*[\\w.]+))?\\s*(?:\\{|;)[ \\t]*$";
		
		List<String> methodSignatureList = new ArrayList<String>();
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader("./esxternaltestfiles/changeLog.txt"));
			String line = "";
			
			while ((line = bReader.readLine()) != null) {
				if(line.matches(pattern)) {
					methodSignatureList.add(line);
				}
			}
		} catch (FileNotFoundException e ){
		
			e.printStackTrace();
		} catch (IOException e)  {
			e.printStackTrace();
		}
		
		return methodSignatureList;
	}//end of getMethodSignatureChangeLog
	
	
	/**helper method for subtraction of plus or minus symbol 
	 * and tab from the starting of a line
	 * */
	private String extractMethodSignature(String methodSignature) {
		return methodSignature.substring(3, methodSignature.length()-1);
	}
	
	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	
	/** Constructor of Test***/
	public MethodFinder() {
		
		List<String> methodSignatureList = new ArrayList<String>();
		
		methodSignatureList = getMethodSignatureChangeLog("./esxternaltestfiles/changeLog.txt");
		
		for(int i=0; i<methodSignatureList.size(); i++) {
			
			String method1 = extractMethodSignature(methodSignatureList.get(i));
			String method2 = extractMethodSignature(methodSignatureList.get(++i));
			
			System.out.println(method1);
			System.out.println(method2);
			
		}
	}
	
	/***Main method for testing***/
	public static void main(String args[]) {
		new MethodFinder();
	}
}
