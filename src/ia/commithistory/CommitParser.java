package ia.commithistory;

import ia.filedependency.FileDependencyImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JFileChooser;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

public class CommitParser {

	public static void main(String[] args) throws IOException, GitAPIException {
		
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " +
					chooser.getSelectedFile().getName());
			
		}
		
		
		Repository repo = new FileRepository(chooser.getSelectedFile());
		Git git = new Git(repo);
		RevWalk walk = new RevWalk(repo);

		
		
		List<Ref> branches = git.branchList().call();

		for (Ref branch : branches) {
			String branchName = branch.getName();

			System.out.println("Commits of branch: " + branch.getName());
			System.out.println("-------------------------------------");

			Iterable<RevCommit> commits = git.log().all().call();

			for (RevCommit commit : commits) {
				boolean foundInThisBranch = false;

				RevCommit targetCommit = walk.parseCommit(repo.resolve(commit.getName()));
				/*for (Map.Entry<String, Ref> e : repo.getAllRefs().entrySet()) {
					if (e.getKey().startsWith(Constants.R_HEADS)) {
						if (walk.isMergedInto(targetCommit, walk.parseCommit(e.getValue().getObjectId()))) {
							String foundInBranch = e.getValue().getName();
							if (branchName.equals(foundInBranch)) {
								foundInThisBranch = true;
								break;
							}
						}
					}
				}*/

				
				
			
//				 byte[] commitInfoBytes = targetCommit.getRawBuffer();

				FileDependencyImpl fileDependency = new FileDependencyImpl();
				
				List<File> fileList = new ArrayList<File>();
				ObjectId currentHead = repo.resolve("HEAD^{tree}");
				ObjectId oldHead = repo.resolve("HEAD~7^{tree}");
				
				ObjectReader reader = repo.newObjectReader();
				CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
				oldTreeIter.reset(reader, oldHead);
				CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
				newTreeIter.reset(reader, currentHead);
				List<DiffEntry> diffs= git.diff()
						.setNewTree(newTreeIter)
						.setOldTree(oldTreeIter)
						.call();
				
				for(DiffEntry dif: diffs){
					System.out.println(dif.getNewPath());
		    		if(dif.getNewPath().contains(".java")){
						File file = new File(dif.getNewPath());
			    		fileList.add(file);
		    		}
				}

				fileDependency.getFileDependency(fileList);
				
/*Parse commit				
				
//				http://stackoverflow.com/questions/19941597/jgit-use-treewalk-to-list-files-and-folders
				RevTree tree = commit.getTree();
				
				TreeWalk treeWalk = new TreeWalk(repo);
				treeWalk.addTree(tree);
				treeWalk.setRecursive(false);
				while (treeWalk.next()) {
				    if (treeWalk.isSubtree()) {
//				        System.out.println("dir: " + treeWalk.getPathString());
				        treeWalk.enterSubtree();
				    } else {
				        System.out.println("file: " + treeWalk.getPathString());				    	if(treeWalk.getPathString().contains(".java"))
				    	{   
				    		File file = new File(treeWalk.getPathString());
				    		fileList.add(file);
				    	}				    	
				
			
				    }
				}
 */				        
//				fileDependency.setfileList(fileList);

/*				for(File file: fileList){
					System.out.println(file.getName());
				}
*/				
				
				
				
				
				
				
				
//				 System.out.println("*******************************");
//				 String commitInfo = new String(commitInfoBytes);
//				 
//				 System.out.println("Commit's Text: \n" + commitInfo );
//
//				 
//				 String treeInfo= new String(treeInfoBytes);
//				 
//				 
//				 System.out.println("Commit's Tree: \n" + treeInfo );
//				
				 
				System.out.println("*******************************");
				System.out.println("*******************************");

				
/*				
				if (foundInThisBranch) {
					PersonIdent authorIdent = commit.getAuthorIdent();
					Date authorDate = authorIdent.getWhen();
					TimeZone authorTimeZone = authorIdent.getTimeZone();

					PersonIdent committerIdent = commit.getCommitterIdent();
					
					System.out.println("Info");
					System.out.println("Commit name/ID: " + commit.getName());
//					System.out.println("Commited by (uname): "+commit.getAuthorIdent().getName());
//					System.out.println("Commited Date: " + new Date(commit.getCommitTime()));
//					System.out.println("Commited Message: "+ commit.getFullMessage());
//					System.out.println("Time zone: " + authorTimeZone);
					System.out.println("Date : " + authorDate);
//					System.out.println("Commiter: " + committerIdent);
					
					
				}
 */
			}
		}

		
		
		
		
		/*
		ObjectReader reader = git.getRepository().newObjectReader();
		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
		ObjectId oldTree = git.getRepository().resolve( "HEAD^{tree}" );
		oldTreeIter.reset( reader, oldTree );
		CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
		ObjectId newTree = git.getRepository().resolve( "HEAD~1^{tree}" );
		newTreeIter.reset( reader, newTree );

		DiffFormatter diffFormatter = new DiffFormatter( DisabledOutputStream.INSTANCE );
		diffFormatter.setRepository( git.getRepository() );
		List<DiffEntry> entries = diffFormatter.scan( oldTreeIter, newTreeIter );

		for( DiffEntry entry : entries ) {
		  System.out.println( entry.getChangeType() );
		}
		
		
		*/
		
	
		
 	
	}
}