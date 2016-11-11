package ia.commithistory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryImpl implements IHistory{
	
	private File currentFile;
    private List<IFileHistory> listHistoryFiles;
	
    @Override
	public File getCurrentFile() throws Exception {
		return currentFile;
	}
	
	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}
	
	@Override
	public List<IFileHistory> getHistoryFiles() throws Exception {
		return listHistoryFiles;
	}
	
	
	public void addHistoryFile(IFileHistory historyFile){
        if(listHistoryFiles==null){
        	listHistoryFiles = new ArrayList<IFileHistory>();
        }
        listHistoryFiles.add(historyFile);
    }

}
