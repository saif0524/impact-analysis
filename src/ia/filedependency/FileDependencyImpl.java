package ia.filedependency;

import ia.sourcecodeparser.ClassFile;
import ia.sourcecodeparser.Parser;
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

public class FileDependencyImpl implements IFileDependency {

	private List<File> fileList;

	private List<String> methodList = new ArrayList<String>();

	private Map<String, String> fileMatrix = new HashMap<String, String>();

	public List<File> getfileList() {
		return fileList;
	}

	public void setfileList(List<File> fileList) {
		this.fileList = fileList;
	}

	@Override
	public HashMap<String, HashSet<String>> getFileDependency(
			List<File> fileList) {

		this.setfileList(fileList);
		// Map<String, List<String>> classMap = new HashMap<String,
		// List<String>>();

		HashMap<String, HashSet<String>> classHashMap = new HashMap<>();

		for (File file : fileList) {

			Parser originalFileParser = new Parser(file);

			List<File> tempFileileList = new ArrayList<File>(fileList);

			String originalFile = file.getAbsoluteFile().toString();

			tempFileileList.remove(file);

			List<String> functionCallsFromThisFile = new ArrayList<String>();

			List<String> methodListOfComparingFile = new ArrayList<String>();

			// List<String> classSet = new ArrayList<String>();

			for (int i = 0; i < tempFileileList.size(); i++) {
				// list function calls
				Parser secondFileParser = new Parser(file);

				try {
					// System.out.println("Original file: " + file.getName());
					functionCallsFromThisFile = originalFileParser
							.getFunctionCalls(file);
					// System.out.println("Method calls: "
					// + functionCallsFromThisFile);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				String comparedToFileName = tempFileileList.get(i)
						.getAbsoluteFile().toString();

				// System.out.println("File compared with: " +
				// comparedToFileName);

				methodListOfComparingFile = secondFileParser
						.getListOfMethods(tempFileileList.get(i)
								.getAbsoluteFile());

				// System.out.println("Methodlist: " +
				// methodListOfComparingFile);
				/*
				 * BlockStmt methodBodies = new BlockStmt();
				 * 
				 * methodBodies = getBodyOfMethods(tempFileileList.get(i)
				 * .getAbsoluteFile());
				 * 
				 * System.out.println("Method bodies: " + methodBodies);
				 */
				for (int j = 0; j < methodListOfComparingFile.size(); j++) {
					if (!(functionCallsFromThisFile.isEmpty())
							&& functionCallsFromThisFile
									.contains(methodListOfComparingFile.get(j))) {
						if (classHashMap.get(comparedToFileName) != null) {
							classHashMap.get(comparedToFileName).add(
									originalFile);
						} else {
							classHashMap.put(comparedToFileName,
									new HashSet<String>());
							classHashMap.get(comparedToFileName).add(
									originalFile);
						}
						// map.add(comparedToFileName);
						break;
					}
				}

				// classMap.put(originalFile, classSet);
			}

		}

		return classHashMap;
	}

	public void printDependency(HashMap<String, HashSet<String>> classHashMap) {
		for (HashMap.Entry<String, HashSet<String>> entry : classHashMap
				.entrySet()) {
			String key = entry.getKey();
			HashSet<String> values = entry.getValue();
			if (!values.isEmpty()) {

				System.out.println(key+"\n");
				System.out.println("---------------------------------------------------------------------------------------------\n");
				for (int i = 0; i < values.size(); i++) {
					System.out.println(values.iterator().next() + "\n");
				}
				
				System.out.println("*********************************************************************************");
			}
		}

	}

	public static void main(String[] args) {

		FileDependencyImpl fileDependency = new FileDependencyImpl();

		List<File> fileList = new ArrayList<File>();

		File file1 = new File(
				"D:/Academics/8th_Semester/801 Project/repo/dp-factory-pattern/src/factory/main/CoconutTree.java");
		File file2 = new File(
				"D:/Academics/8th_Semester/801 Project/repo/dp-factory-pattern/src/factory/main/FactoryMain.java");
		File file3 = new File(
				"D:/Academics/8th_Semester/801 Project/repo/dp-factory-pattern/src/factory/main/ITree.java");
		File file4 = new File(
				"D:/Academics/8th_Semester/801 Project/repo/dp-factory-pattern/src/factory/main/MangoTree.java");
		File file5 = new File(
				"D:/Academics/8th_Semester/801 Project/repo/dp-factory-pattern/src/factory/main/TreeFactory.java");
		fileList.add(file1);
		fileList.add(file2);
		fileList.add(file3);
		fileList.add(file4);
		fileList.add(file5);

		HashMap<String, HashSet<String>> classMap = fileDependency
				.getFileDependency(fileList);

		fileDependency.printDependency(classMap);
	}
}

/*
 * private List<String> getFunctionCalls(File file) throws ParseException,
 * IOException { FileInputStream inputFile = new
 * FileInputStream(file.getAbsoluteFile());
 * 
 * CompilationUnit compilationUnit; try { compilationUnit =
 * JavaParser.parse(inputFile); } finally { inputFile.close(); }
 * 
 * MethodCallVisitor methodCall = new MethodCallVisitor();
 * 
 * methodCall.visit(compilationUnit, null);
 * 
 * return methodCall.getCalledFunctionList(); }
 */
/*
 * private void getDpendency(File file) { BufferedReader bufferedReader = null;
 * try {
 * 
 * bufferedReader = new BufferedReader(new FileReader(file)); String line =
 * null; while ((line = bufferedReader.readLine()) != null) {
 * 
 * StringTokenizer st = new StringTokenizer(line); while (st.hasMoreTokens()) {
 * System.out.println("token:  " + st.nextToken()); } //
 * System.out.println(line); } System.out.println("Reading Complete");
 * System.out.println("****************************************");
 * bufferedReader.close(); } catch (IOException e) { e.printStackTrace(); } }
 */
/*
 * private List<String> getListOfMethods(File javaFile) { CompilationUnit
 * compileUnit = getMethods(javaFile); MethodDeclarationVisitor visitor = new
 * MethodDeclarationVisitor(); visitor.visit(compileUnit, null);
 * 
 * return visitor.getDeclaredMethodList(); }
 */
/*
 * private BlockStmt getBodyOfMethods(File javaFile) { CompilationUnit
 * compileUnit = getMethods(javaFile); MethodDeclarationVisitor visitor = new
 * MethodDeclarationVisitor(); visitor.visit(compileUnit, null);
 * 
 * return visitor.getMethodList(); }
 *//*
	 * private CompilationUnit getMethods(File javaFile) { CompilationUnit
	 * compileUnit = null; try { compileUnit = JavaParser.parse(javaFile); }
	 * catch (japa.parser.ParseException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } return compileUnit; }
	 * 
	 * public List<String> getMethodList() { return methodList; }
	 * 
	 * private class MethodDeclarationVisitor extends VoidVisitorAdapter {
	 * 
	 * private List<String> declaredMethodList = new ArrayList<String>();
	 * private BlockStmt methodBodyList = new BlockStmt();
	 * 
	 * public void visit(MethodDeclaration method, Object arg) {
	 * declaredMethodList.add(method.getName()); this.methodBodyList =
	 * method.getBody(); }
	 * 
	 * public List<String> getDeclaredMethodList() { return declaredMethodList;
	 * }
	 * 
	 * public void setDeclaredMethodList(List<String> declaredMethodList) {
	 * this.declaredMethodList = declaredMethodList; }
	 * 
	 * public BlockStmt getMethodList() { return methodBodyList; }
	 * 
	 * public void setMethodList(BlockStmt methodList) { this.methodBodyList =
	 * methodList; } }
	 * 
	 * private class MethodCallVisitor extends VoidVisitorAdapter {
	 * 
	 * private List<String> calledFunctionList = new ArrayList<String>();
	 * 
	 * @Override public void visit(MethodCallExpr methodCall, Object arg) { //
	 * System.out.print("Method call: " + methodCall.getName() + "\n");
	 * 
	 * calledFunctionList.add(methodCall.getName());
	 * 
	 * List<Expression> args = methodCall.getArgs(); if (args != null)
	 * handleExpressions(args); }
	 * 
	 * private void handleExpressions(List<Expression> expressions) { for
	 * (Expression expr : expressions) { if (expr instanceof MethodCallExpr)
	 * visit((MethodCallExpr) expr, null); else if (expr instanceof BinaryExpr)
	 * { BinaryExpr binExpr = (BinaryExpr) expr;
	 * handleExpressions(Arrays.asList(binExpr.getLeft(), binExpr.getRight()));
	 * } } }
	 * 
	 * public List<String> getCalledFunctionList() { return calledFunctionList;
	 * }
	 * 
	 * public void setCalledFunctionList(List<String> calledFunctionList) {
	 * this.calledFunctionList = calledFunctionList; } }
	 */