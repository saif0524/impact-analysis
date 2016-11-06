package ia.commithistory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class RepoDiff {

    public void compare(byte[] fileContentOld, byte[] fileContentNew) {
        try {
        	Repository repository = new FileRepository("D:/8th Semester/801 Project/repo/java-blog-aggregator/.git");
            Git git = new Git(repository);

            // create the file
            commitFileContent(fileContentOld, repository, git, true);
            commitFileContent(fileContentNew, repository, git, false);

            // The {tree} will return the underlying tree-id instead of the commit-id itself!
            ObjectId oldHead = repository.resolve("HEAD^^^^{tree}");   //here is my nullpointer
            ObjectId head = repository.resolve("HEAD^{tree}");

            System.out.println("Printing diff between tree: " + oldHead + " and " + head);

            // prepare the two iterators to compute the diff between
            ObjectReader reader = repository.newObjectReader();
            CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
            oldTreeIter.reset(reader, oldHead);
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            newTreeIter.reset(reader, head);

            // finally get the list of changed files
            List<DiffEntry> diffs= new Git(repository).diff()
                            .setNewTree(newTreeIter)
                            .setOldTree(oldTreeIter)
                            .call();
            for (DiffEntry entry : diffs) {
                System.out.println("Entry: " + entry);
            }
            System.out.println("Done");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

/**
 * Adds and optionally commits fileContent to a repository
 * @param commit True if file should be committed, False if not
 * @throws Exception catch all for testing
 */
private void commitFileContent(byte[] fileContent, Repository repository, Git git, boolean commit) throws Exception {

    File myfile = new File(repository.getDirectory().getParent(), "testfile");
    myfile.createNewFile();

    FileOutputStream fos = new FileOutputStream (myfile); 
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    baos.write(fileContent);
    baos.writeTo(fos);

    // run the add
    git.add().addFilepattern("testfile").call();

    if(commit) {
        // and then commit the changes
        git.commit().setMessage("Added fileContent").call();
    }

    fos.close();
}

/**
 * Helperfunction from
 * https://github.com/centic9/jgit-cookbook
 */
//public static Repository createNewRepository() throws IOException {
//    // prepare a new folder
//    File localPath = File.createTempFile("TestGitRepository", "");
//    localPath.delete();
//
//    // create the directory
//    Repository repository = FileRepositoryBuilder.create(new File(
//            localPath, ".git"));
//    repository.create();
//
//    return repository;
//    }
}