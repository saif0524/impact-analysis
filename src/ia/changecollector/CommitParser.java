package ia.changecollector;

import ia.dependencyresolver.ClassDependencyImpl;
import ia.sourcecodeparser.ClassFile;
import ia.sourcecodeparser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Repository;

public class CommitParser {

	
	
	public static void main(String[] args) throws IOException, GitAPIException {

		RepositoryReader rr = new RepositoryReader();

		rr.readRepo();

		Repository repo = rr.getRepository();

		ChangeCollector changeCollector = new ChangeCollector();

		List<DiffEntry> diffEntries = changeCollector.compareTrees(repo);
		
		
		
		//list diff entries
		System.out.println("Entries");
		for (DiffEntry entry : diffEntries) {
			System.out.println("new path: " + entry.getNewId()+" || old path: " + entry.getOldId());
			
			//jgit examples https://doc.nuxeo.com/blog/jgit-example/
			DiffFormatter formatter = new DiffFormatter(System.out);
			formatter.setRepository(repo);
			formatter.format(entry);			
		}	
		//end list diff entries	
		
/*		kkkk

		ClassDependencyImpl fileDependency = new ClassDependencyImpl();

		List<File> fileList = new ArrayList<File>();

		fileList = changeCollector.createListOfEntries(repo, diffEntries);

		List<ClassFile> classList = new ArrayList<ClassFile>();

		Parser classParser = new Parser(fileList);

		classList = classParser.createClassCard(fileList);
		System.out
				.println("*******************************************************************");
		for (ClassFile cf : classList) {
			System.out.println("Classname: " + cf.getClassNAme());
			System.out
					.println("________________________________________________________________");
			System.out.println("Methods: ");
			for (int i = 0; i < cf.getMethodList().size(); i++) {
				System.out.println(cf.getMethodList().get(i));
			}
			System.out
					.println("________________________________________________________________");
			System.out.println("Functioncalls: ");
			for (int i = 0; i < cf.getFunctionCallList().size(); i++) {
				System.out.println(cf.getFunctionCallList().get(i));
			}

			System.out
					.println("*******************************************************************");
		}

		HashMap<String, HashSet<String>> classMap = fileDependency
				.getFileDependency(fileList);

		System.out.println("88888888888888888888888888888888888888888888888");
		fileDependency.printDependency(classMap);

		
		
		Scanner sc = new Scanner(System.in);
		
		String str = sc.next();
		
		fileDependency.dependencyGraphGenerator(classMap,str);
		kkkkkk*/
	}

}
// JFileChooser chooser = new JFileChooser();
// chooser.setCurrentDirectory(new java.io.File("/home"));
// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
// int returnVal = chooser.showOpenDialog(null);
//
// if ((returnVal == JFileChooser.APPROVE_OPTION)) {
// System.out.println("You chose to open this file: "
// + chooser.getSelectedFile().getName());
//
// }
//
// Repository repo = new FileRepository(chooser.getSelectedFile());
// Git git = rr.getGit();// new Git(repo);

// ObjectId currentHead = repo.resolve("HEAD^{tree}");
//
// System.out.println("Current head: " + currentHead);
//
// Scanner sc = new Scanner(System.in);
//
// System.out.println("Enter Old head:  ");
// int headNum = sc.nextInt();
//
// ObjectId oldHead = repo.resolve("HEAD~" + headNum + "^{tree}");
// System.out.println("Old head:  " + oldHead);
//
// ObjectReader headReader = repo.newObjectReader();
//
// CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
// oldTreeIter.reset(headReader, oldHead);
//
// CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
//
// newTreeIter.reset(headReader, currentHead);
//

/****************
 * Parse commit of all the branches List<Ref> branches =
 * git.branchList().call(); for (Ref branch : branches) { String branchName =
 * branch.getName();
 * 
 * System.out.println("Commits of branch: " + branch.getName());
 * System.out.println("-------------------------------------");
 * 
 * /************ Iterable<RevCommit> commits = git.log().all().call();
 * 
 * 
 * Parse commit for (RevCommit commit : commits) { boolean foundInThisBranch =
 * false;
 * 
 * RevCommit targetCommit = walk.parseCommit(repo.resolve(commit .getName()));
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
 * walk.parseCommit(e.getValue().getObjectId()))) { String foundInBranch =
 * e.getValue().getName(); if (branchName.equals(foundInBranch)) {
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
