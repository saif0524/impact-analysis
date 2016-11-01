package ia.commithistory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class PageCommit {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repo = new FileRepository("D:/8th Semester/801 Project/repo/java-blog-aggregator/.git");
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
				for (Map.Entry<String, Ref> e : repo.getAllRefs().entrySet()) {
					if (e.getKey().startsWith(Constants.R_HEADS)) {
						if (walk.isMergedInto(targetCommit, walk.parseCommit(e.getValue().getObjectId()))) {
							String foundInBranch = e.getValue().getName();
							if (branchName.equals(foundInBranch)) {
								foundInThisBranch = true;
								break;
							}
						}
					}
				}
				

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
//					System.out.println("Date : " + authorDate);
//					System.out.println("Commiter: " + committerIdent);
					
					
				}
			}
		}
		System.out.println("______________________________________________________________");
		
		ObjectId oldHead = repo.resolve("HEAD^^^{tree}");
		
		System.out.println(oldHead);
		
		ObjectId head = repo.resolve("HEAD^{tree}");
		
		System.out.println(head);
		

		
		

		ObjectReader reader = repo.newObjectReader();
		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
		oldTreeIter.reset(reader, oldHead);
		CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
		newTreeIter.reset(reader, head);
		List<DiffEntry> diffs= git.diff()
		                    .setNewTree(newTreeIter)
		                    .setOldTree(oldTreeIter)
		                    .call();
		
	
		System.out.println("______________________________________________________________");
		for(DiffEntry dif: diffs){
			System.out.println(dif.toString());
		}
	}
}