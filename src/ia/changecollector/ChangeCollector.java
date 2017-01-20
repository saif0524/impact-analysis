package ia.changecollector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class ChangeCollector {

	private ObjectId currentHead, oldHead;
	private ObjectReader headReader;
	private CanonicalTreeParser oldTreeIter, newTreeIter;
	private List<DiffEntry> entryList;

	public List<DiffEntry> compareTrees(Repository repo)
			throws AmbiguousObjectException, IOException, GitAPIException {
		List<DiffEntry> diffEntries = null;
		setCurrentHead(repo.resolve("HEAD^{tree}"));

		// Takes old head number

		int oldHeadNumber = Integer.parseInt(JOptionPane
				.showInputDialog("Enter old head"));

		setOldHead(repo.resolve("HEAD~" + oldHeadNumber + "^{tree}"));
		setHeadReader(repo.newObjectReader());

		diffEntries = this.setTrees(repo);

		return diffEntries;

	}

	public List<File> createListOfEntries(Repository repo,
			List<DiffEntry> diffEntries) {

		List<File> fileList = new ArrayList<File>();
		for (DiffEntry entry : diffEntries) {
			if (entry.getNewPath().contains(".java")) {

				File tempFile = new File(entry.getNewPath());

				String filePath = repo.getDirectory().getParent() + "/"
						+ tempFile.getPath();

				File file = new File(filePath);
				fileList.add(file);

			}
		}

		return fileList;
	}

	private List<DiffEntry> setTrees(Repository repo)
			throws IncorrectObjectTypeException, IOException, GitAPIException {

		Git git = new Git(repo);

		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();

		oldTreeIter.reset(getHeadReader(), getOldHead());

		setOldTreeIter(oldTreeIter);

		CanonicalTreeParser newTreeIter = new CanonicalTreeParser();

		newTreeIter.reset(getHeadReader(), getCurrentHead());

		List<DiffEntry> diffEntries = git.diff().setNewTree(newTreeIter)
				.setOldTree(oldTreeIter).call();

		return diffEntries;
	}

	public List<DiffEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<DiffEntry> entryList) {
		this.entryList = entryList;
	}

	public ObjectId getOldHead() {
		return oldHead;
	}

	public void setOldHead(ObjectId oldHead) {
		this.oldHead = oldHead;
	}

	public ObjectId getCurrentHead() {
		return currentHead;
	}

	public void setCurrentHead(ObjectId currentHead) {
		this.currentHead = currentHead;
	}

	public ObjectReader getHeadReader() {
		return headReader;
	}

	public void setHeadReader(ObjectReader headReader) {
		this.headReader = headReader;
	}

	public CanonicalTreeParser getOldTreeIter() {
		return oldTreeIter;
	}

	public void setOldTreeIter(CanonicalTreeParser oldTreeIter) {
		this.oldTreeIter = oldTreeIter;
	}

	public CanonicalTreeParser getNewTreeIter() {
		return newTreeIter;
	}

	public void setNewTreeIter(CanonicalTreeParser newTreeIter) {
		this.newTreeIter = newTreeIter;
	}

}