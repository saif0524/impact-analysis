package ia.changecollector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class LogAnalyzer {

	private Git git;
	private Repository repo;

	@SuppressWarnings("unused")
	public void diffCommit(String hashID) throws IOException {
		// Initialize repositories.
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		repo = builder.setGitDir(new File("C:\\Users\\saif-pc\\Documents\\repo\\argouml\\" + "/.git"))
				.setMustExist(true).build();
		git = new Git(repo);

		// Get the commit you are looking for.
		RevCommit newCommit;

		Repository repo = git.getRepository();

		RevWalk walk = null;

		try {
			walk = new RevWalk(git.getRepository());
			newCommit = walk.parseCommit(repo.resolve(hashID));
			System.out.println("LogCommit: " + newCommit);
			String logMessage = newCommit.getFullMessage();
			System.out.println("LogMessage: " + logMessage);
			// Print diff of the commit with the previous one.
			System.out.println(getDiffOfCommit(newCommit));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Helper gets the diff as a string.
	private String getDiffOfCommit(RevCommit newCommit) throws IOException {

		// Get commit that is previous to the current one.
		RevCommit oldCommit = getPrevHash(newCommit);
		if (oldCommit == null) {
			return "Start of repo";
		}
		// Use treeIterator to diff.
		AbstractTreeIterator oldTreeIterator = getCanonicalTreeParser(oldCommit);
		AbstractTreeIterator newTreeIterator = getCanonicalTreeParser(newCommit);
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			DiffFormatter formatter = new DiffFormatter(outputStream);
			formatter.setRepository(git.getRepository());
			formatter.format(oldTreeIterator, newTreeIterator);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		String diff = outputStream.toString();
		return diff;
	}

	// Helper function to get the previous commit.
	public RevCommit getPrevHash(RevCommit commit) throws IOException {

		try {

			RevWalk walk = new RevWalk(repo);
			// Starting point
			walk.markStart(commit);
			int count = 0;
			for (RevCommit rev : walk) {
				// got the previous commit.
				if (count == 1) {
					return rev;
				}
				count++;
			}
			walk.dispose();
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		// Reached end and no previous commits.
		return null;
	}

	// Helper function to get the tree of the changes in a commit. Written by
	// Rüdiger Herrmann
	private AbstractTreeIterator getCanonicalTreeParser(ObjectId commitId) throws IOException {
		try {
			RevWalk walk = new RevWalk(git.getRepository());
			RevCommit commit = walk.parseCommit(commitId);
			ObjectId treeId = commit.getTree().getId();
			try {

				ObjectReader reader = git.getRepository().newObjectReader();
				return new CanonicalTreeParser(null, reader, treeId);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public LogAnalyzer() throws IOException {
		diffCommit("b8604d9");
	}

	public static void main(String args[]) throws IOException {
		LogAnalyzer logAnalyszer = new LogAnalyzer();
	}
	
	// Parse commit of all the branches

	/*
	 * 
	 * public static void main(String args[]) throws GitAPIException, IOException {

		Git git = null;
		List<Ref> branches = git.branchList().call();

		for (Ref branch : branches) {
			String branchName = branch.getName();

			System.out.println("Commits of branch: " + branch.getName());
			System.out.println("-------------------------------------");

			Iterable<RevCommit> commits = git.log().all().call();

			// Parse commit
			for (RevCommit commit : commits) {
				boolean foundInThisBranch = false;

				Repository repo = null;

				RevWalk walk = new RevWalk(git.getRepository());
				RevCommit targetCommit = walk.parseCommit(repo.resolve(commit.getName()));

				// http:stackoverflow.comquestions19941597jgit-use-treewalk-to-list-files-and-folders

				RevTree tree = commit.getTree();

				TreeWalk treeWalk = new TreeWalk(repo);
				treeWalk.addTree(tree);
				treeWalk.setRecursive(false);
				List<File> fileList = null;
				while (treeWalk.next()) {
					if (treeWalk.isSubtree()) {
						System.out.println("dir: " + treeWalk.getPathString());
						treeWalk.enterSubtree();
					} else {
						System.out.println("file: " + treeWalk.getPathString());
						if (treeWalk.getPathString().contains(".java")) {
							File file = new File(treeWalk.getPathString());
							fileList.add(file);
						}

					}

				}
			}
		}
	}
	*/
}
