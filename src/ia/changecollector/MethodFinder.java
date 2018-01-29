package ia.changecollector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MethodFinder {

	private File inputFile;

	
	public static void readFile(String fileName) {
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(fileName));
			String line = bReader.readLine();
			while (bReader.readLine() != null) {
				line = bReader.readLine();
				System.out.println(line);
			}
		} catch (FileNotFoundException e ){
		
			e.printStackTrace();
		} catch (IOException e)  {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		
		//readFile("./tt/changLog.txt");		

		//found a regex from https://stackoverflow.com/questions/68633/regex-that-will-match-a-java-method-declaration;
		String pattern = ".[\\-\\+][ \\t]*(?:(?:public|protected|private)\\s+)?(?:(static|final|native|synchronized|abstract|threadsafe|transient|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>)|(?:<[^<]*<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))\\s+){0,}(?!return)\\b([\\w.]+)\\b(?:|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>)|(?:<[^<]*<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))((?:\\[\\]){0,})\\s+\\b\\w+\\b\\s*\\(\\s*(?:\\b([\\w.]+)\\b(?:|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>)|(?:<[^<]*<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))((?:\\[\\]){0,})(\\.\\.\\.)?\\s+(\\w+)\\b(?![>\\[])\\s*(?:,\\s+\\b([\\w.]+)\\b(?:|(?:<[?\\w\\[\\] ,&]+>)|(?:<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>)|(?:<[^<]*<[^<]*<[?\\w\\[\\] ,&]+>[^>]*>[^>]*>))((?:\\[\\]){0,})(\\.\\.\\.)?\\s+(\\w+)\\b(?![>\\[])\\s*){0,})?\\s*\\)(?:\\s*throws [\\w.]+(\\s*,\\s*[\\w.]+))?\\s*(?:\\{|;)[ \\t]*$";
		
		
		String str = "+    public String getLookAndFeelFromName(String name) {";
		
		
		List<String> methodSignatureList = new ArrayList<String>();
			
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader("./esxternaltestfiles/changeLog.txt"));
			String line;// = bReader.readLine();
			
			while ((line = bReader.readLine()) != null) {
				if(line.matches(pattern)) {
					System.out.println(line);
					methodSignatureList.add(line);
				}
			}
		} catch (FileNotFoundException e ){
		
			e.printStackTrace();
		} catch (IOException e)  {
			e.printStackTrace();
		}
		
		for(int i=0; i<methodSignatureList.size(); i++) {
			String method1 = methodSignatureList.get(i);
			String method2 = methodSignatureList.get(++i);
			
			System.out.println(method1.substring(3, method1.length()-1));
			System.out.println(method2.substring(3, method2.length()-1));
			
//			System.out.println(method1 + "\t\t" + method2);
		}
	}
	
	
	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	
}
