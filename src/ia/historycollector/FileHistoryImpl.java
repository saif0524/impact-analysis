package ia.historycollector;

import java.io.File;
import java.util.Calendar;

public class FileHistoryImpl implements IFileHistory {

	private File file;
	private String authorName;
	private String committedComment;
	private Calendar commitDate;

	@Override
	public String getAuthor() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public File getFile() throws Exception {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public Calendar getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Calendar commitDate) {
		this.commitDate = commitDate;
	}


	@Override
	public String getComment() {
		return committedComment;
	}
	public void setCommittedComment(String committedComment) {
		this.committedComment = committedComment;
	}


}
