package ia.filedependency;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
			System.out.println("Methodlist: ");
			System.out.println(this.getMethodList());


			try {
				getFunctionCalls(file);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		// for (File file : fileList) {
		// System.out.println("Readnig file: " + file.getName());
		// getDpendency(file);
		// }

	}

	private void getFunctionCalls(File file) throws ParseException, IOException{
		 FileInputStream inputFile = new FileInputStream(file);

	        CompilationUnit compilationUnit;
	        try
	        {
	        	compilationUnit = JavaParser.parse(inputFile);
	        }
	        finally
	        {
	            inputFile.close();
	        }

	        new MethodCallVisitor ().visit(compilationUnit, null);
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
				// System.out.println(line);
			}
			System.out.println("Reading Complete");
			System.out.println("****************************************");
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getListOfAllMethods(File javaFile) {
		CompilationUnit compileUnit = getMethods(javaFile);
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		visitor.visit(compileUnit, null);

	}

	private CompilationUnit getMethods(File javaFile) {
		CompilationUnit compileUnit = null;
		try {
			compileUnit = JavaParser.parse(javaFile);
		} catch (japa.parser.ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compileUnit;
	}

	public List<String> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<String> methodList) {
		this.methodList = methodList;
	}

	
	
	private class MethodDeclarationVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodDeclaration method, Object arg) {
			methodList.add(method.getName());

		}
	}
	
	
	private class MethodCallVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodCallExpr methodCall, Object arg) {
			System.out.print("Method call: " + methodCall.getName() + "\n");
			List<Expression> args = methodCall.getArgs();
			if (args != null)
				handleExpressions(args);
		}

		private void handleExpressions(List<Expression> expressions) {
			for (Expression expr : expressions) {
				if (expr instanceof MethodCallExpr)
					visit((MethodCallExpr) expr, null);
				else if (expr instanceof BinaryExpr) {
					BinaryExpr binExpr = (BinaryExpr) expr;
					handleExpressions(Arrays.asList(binExpr.getLeft(),
							binExpr.getRight()));
				}
			}
		}
	}

	public static void main(String[] args) {

		FileDependencyImpl fileDependency = new FileDependencyImpl();

		List<File> fileList = new ArrayList<File>();

		File file1 = new File(
				"D:/Academics/8th_Semester/801 Project/Workspace/java-blog-aggregator/src/main/java/cz/jiripinkas/jba/controller/AdminController.java");
		File file2 = new File(
				"D:/Academics/8th_Semester/801 Project/Workspace/java-blog-aggregator/src/main/java/cz/jiripinkas/jba/controller/RegisterController.java");
		fileList.add(file1);
		fileList.add(file2);

		fileDependency.getFileDependency(fileList);
	}
}
