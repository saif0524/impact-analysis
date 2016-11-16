package ia.commithistory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

public class DifferentEntry {
	public  List<File> createListOfEntries(Repository repo, List<DiffEntry> diffEntries){
		
		List<File> fileList = new ArrayList<>();
		for (DiffEntry entry : diffEntries) {
			if (entry.getNewPath().contains(".java")
					&& !(entry.getOldPath().contains("null"))) {
				
				File tempFile = new File(entry.getNewPath());

				String filePath = repo.getDirectory().getParent()+"\\"+tempFile.getPath();
				
				File file = new File(filePath);
				fileList.add(file);

			}
		}
		
		return fileList;
	}
}