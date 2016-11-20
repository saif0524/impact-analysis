package ia.sourcecodeparser;

import java.util.List;

public class ClassFile {

	private String classNAme;
	private List<String> methodList;
	private List<String> functionCallList;

	public ClassFile(String classNAme) {
		this.classNAme = classNAme;
	}

	public String getClassNAme() {
		return classNAme;
	}

	public void setClassNAme(String classNAme) {
		this.classNAme = classNAme;
	}

	public List<String> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<String> methodList) {
		this.methodList = methodList;
	}

	public List<String> getFunctionCallList() {
		return functionCallList;
	}

	public void setFunctionCallList(List<String> functionCallList) {
		this.functionCallList = functionCallList;
	}

}
