package ia.commithistory;

import ia.filedependency.FileDependencyImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class CommitParser {

	public static void main(String[] args) throws IOException, GitAPIException {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("/home"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = chooser.showOpenDialog(null);

		if ((returnVal == JFileChooser.APPROVE_OPTION)) {
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());

		}

		Repository repo = new FileRepository(chooser.getSelectedFile());
		Git git = new Git(repo);
		// RevWalk walk = new RevWalk(repo);

		FileDependencyImpl fileDependency = new FileDependencyImpl();

		List<File> fileList = new ArrayList<File>();
		ObjectId currentHead = repo.resolve("HEAD^{tree}");

		System.out.println("Current head: " + currentHead);

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Old head:  ");
		int headNum = sc.nextInt();

		ObjectId oldHead = repo.resolve("HEAD~" + headNum + "^{tree}");
		System.out.println("Old head:  " + oldHead);

		ObjectReader headReader = repo.newObjectReader();

		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
		oldTreeIter.reset(headReader, oldHead);

		CanonicalTreeParser newTreeIter = new CanonicalTreeParser();

		newTreeIter.reset(headReader, currentHead);

		List<DiffEntry> diffEntries = git.diff().setNewTree(newTreeIter)
				.setOldTree(oldTreeIter).call();

		DifferentEntry listOfChangedFiles = new DifferentEntry();

		fileList = listOfChangedFiles.createListOfEntries(repo, diffEntries);

		fileDependency.getFileDependency(fileList);

		/****************
		 * Parse commit of all the branches List<Ref> branches =
		 * git.branchList().call(); for (Ref branch : branches) { String
		 * branchName = branch.getName();
		 * 
		 * System.out.println("Commits of branch: " + branch.getName());
		 * System.out.println("-------------------------------------");
		 * 
		 * /************ Iterable<RevCommit> commits = git.log().all().call();
		 * 
		 * 
		 * Parse commit for (RevCommit commit : commits) { boolean
		 * foundInThisBranch = false;
		 * 
		 * RevCommit targetCommit = walk.parseCommit(repo.resolve(commit
		 * .getName()));
		 * 
		 * 
		 * // http://stackoverflow.com/questions/19941597/jgit-use- treewalk
		 * -to-list-files-and-folders RevTree tree = commit.getTree();
		 * 
		 * TreeWalk treeWalk = new TreeWalk(repo); treeWalk.addTree(tree);
		 * treeWalk.setRecursive(false); while (treeWalk.next()) { if
		 * (treeWalk.isSubtree()) { // System.out.println("dir: " +
		 * treeWalk.getPathString()); treeWalk.enterSubtree(); } else {
		 * System.out.println("file: " + treeWalk.getPathString());
		 * if(treeWalk.getPathString().contains(".java")) { File file = new
		 * File(treeWalk.getPathString()); fileList.add(file); }
		 * 
		 * 
		 * } }
		 ************/

		/***
		 * for (Map.Entry<String, Ref> e : repo.getAllRefs().entrySet()) { if
		 * (e.getKey().startsWith(Constants.R_HEADS)) { if
		 * (walk.isMergedInto(targetCommit,
		 * walk.parseCommit(e.getValue().getObjectId()))) { String foundInBranch
		 * = e.getValue().getName(); if (branchName.equals(foundInBranch)) {
		 * foundInThisBranch = true; break; } } } }
		 * 
		 * Branch Info if (foundInThisBranch) {
		 * 
		 * PersonIdent authorIdent = commit.getAuthorIdent(); Date authorDate =
		 * authorIdent.getWhen(); PersonIdent committerIdent =
		 * commit.getCommitterIdent();
		 * 
		 * System.out.println("Info"); System.out.println("Commit name/ID: " +
		 * commit.getName()); System.out.println("Date : " + authorDate);
		 * System.out.println("Commiter: " + committerIdent); } } }
		 *****************************/
	}

}