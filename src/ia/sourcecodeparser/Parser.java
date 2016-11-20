package ia.sourcecodeparser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Parser {

	private File file;

	private List<File> fileList;

	private List<String> methodList = new ArrayList<String>();

	public Parser(List<File> fileList) {
		this.fileList = fileList;
	}

	public Parser(File file) {
		this.file = file;
	}

	public List<File> getfileList() {
		return fileList;
	}

	public void setfileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public List<ClassFile> createClassCard(List<File> fileList) {

		List<ClassFile> classCardList = new ArrayList<ClassFile>();

		for (File file : fileList) {
			ClassFile cf = new ClassFile(file.getAbsolutePath());
			List<String> methodList = new ArrayList<String>();

			methodList = getListOfMethods(file);

			List<String> functionCalls = new ArrayList<String>();

			try {
				functionCalls = getFunctionCalls(file);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			cf.setMethodList(methodList);
			cf.setFunctionCallList(functionCalls);

			classCardList.add(cf);
		}

		return classCardList;
	}

	public List<String> getFunctionCalls(File file) throws ParseException,
			IOException {
		FileInputStream inputFile = new FileInputStream(file.getAbsoluteFile());

		CompilationUnit compilationUnit;
		try {
			compilationUnit = JavaParser.parse(inputFile);
		} finally {
			inputFile.close();
		}

		MethodCallVisitor methodCall = new MethodCallVisitor();

		methodCall.visit(compilationUnit, null);

		return methodCall.getCalledFunctionList();
	}

	/*
	 * private void getDpendency(File file) { BufferedReader bufferedReader =
	 * null; try {
	 * 
	 * bufferedReader = new BufferedReader(new FileReader(file)); String line =
	 * null; while ((line = bufferedReader.readLine()) != null) {
	 * 
	 * StringTokenizer st = new StringTokenizer(line); while
	 * (st.hasMoreTokens()) { System.out.println("token:  " + st.nextToken()); }
	 * // System.out.println(line); } System.out.println("Reading Complete");
	 * System.out.println("****************************************");
	 * bufferedReader.close(); } catch (IOException e) { e.printStackTrace(); }
	 * }
	 */

	public List<String> getListOfMethods(File javaFile) {
		CompilationUnit compileUnit = getMethods(javaFile);
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		visitor.visit(compileUnit, null);

		return visitor.getDeclaredMethodList();
	}

	public BlockStmt getBodyOfMethods(File javaFile) {
		CompilationUnit compileUnit = getMethods(javaFile);
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		visitor.visit(compileUnit, null);

		return visitor.getMethodList();
	}

	public CompilationUnit getMethods(File javaFile) {
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	private class MethodDeclarationVisitor extends VoidVisitorAdapter {

		private List<String> declaredMethodList = new ArrayList<String>();
		private BlockStmt methodBodyList = new BlockStmt();

		public void visit(MethodDeclaration method, Object arg) {
			declaredMethodList.add(method.getName());
			this.methodBodyList = method.getBody();
		}

		public List<String> getDeclaredMethodList() {
			return declaredMethodList;
		}

		public void setDeclaredMethodList(List<String> declaredMethodList) {
			this.declaredMethodList = declaredMethodList;
		}

		public BlockStmt getMethodList() {
			return methodBodyList;
		}

		public void setMethodList(BlockStmt methodList) {
			this.methodBodyList = methodList;
		}
	}

	private class MethodCallVisitor extends VoidVisitorAdapter {

		private List<String> calledFunctionList = new ArrayList<String>();

		@Override
		public void visit(MethodCallExpr methodCall, Object arg) {
			// System.out.print("Method call: " + methodCall.getName() + "\n");

			calledFunctionList.add(methodCall.getName());

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

		public List<String> getCalledFunctionList() {
			return calledFunctionList;
		}

		public void setCalledFunctionList(List<String> calledFunctionList) {
			this.calledFunctionList = calledFunctionList;
		}
	}
}
