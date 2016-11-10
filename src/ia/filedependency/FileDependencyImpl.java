package ia.filedependency;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileDependencyImpl implements IFileDependency {

	private List<File> fileList;

	private List<String> methodList = new ArrayList<String>();

	public List<File> getfileList() {
		return fileList;
	}

	public void setfileList(List<File> fileList) {
		this.fileList = fileList;
	}

	@Override
	public void getFileDependency(List<File> fileList) {
		for (File file : fileList) {
			System.out.println("Readnig file: " + file.getName());
			getListOfAllMethods(file);

		}
		
		for (File file : fileList) {
			System.out.println("Readnig file: " + file.getName());
			getDpendency(file);
		}

	}
	private void getDpendency(File file) {
		BufferedReader bufferedReader = null;
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				
				
				StringTokenizer st = new StringTokenizer(line);  
			     while (st.hasMoreTokens()) {  
			         System.out.println("token:  " + st.nextToken());  
			     }				
//				System.out.println(line);
			}
			System.out.println("Reading Complete");
			System.out.println("****************************************");
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getListOfAllMethods(File file) {
		CompilationUnit cu = getMethods(file);
		MethodVisitor visitor = new MethodVisitor();
		visitor.visit(cu, null);

	}


	private CompilationUnit getMethods(File javaFile) {
		CompilationUnit cu = null;
		try {
			cu = JavaParser.parse(javaFile);
		} catch (japa.parser.ParseException | IOException e) {
			e.printStackTrace();
		}
		return cu;
	}

	public List<String> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<String> methodList) {
		this.methodList = methodList;
	}

	private class MethodVisitor extends VoidVisitorAdapter {
		public void visit(MethodDeclaration method, Object arg) {
			methodList.add(method.getName());

		}
	}
}
