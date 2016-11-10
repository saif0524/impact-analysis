package ia.filedependency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDependencyMain {

	public static void main(String[] args){
		
		FileDependencyImpl fileDependency = new FileDependencyImpl();
		
		List<File> fileList = new ArrayList<>();
		
		File file1 = new File("D:/Academics/8th_Semester/801 Project/Workspace/java-blog-aggregator/src/main/java/cz/jiripinkas/jba/controller/AdminController.java");
		File file2 = new File("D:/Academics/8th_Semester/801 Project/Workspace/java-blog-aggregator/src/main/java/cz/jiripinkas/jba/controller/RegisterController.java");
		fileList.add(file1);
		fileList.add(file2);
		
		
//		fileDependency.setfileList(fileList);
		
		fileDependency.getFileDependency(fileList);
	}
	
	
}
